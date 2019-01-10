package de.ralfhergert.common.model.meta;

import de.ralfhergert.common.model.Ignore;
import de.ralfhergert.common.model.property.PropertyHolder;
import de.ralfhergert.common.model.property.PropertyHolderFactory;

import java.lang.reflect.Method;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class creates a info about a given {@link Method}.
 */
public class MethodInfo {

	public enum MethodType {
		IGNORED,
		SETTER,
		GETTER,
		ADD,
		REMOVE
	}

	// patterns for method names regarding https://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.8
	private static final Pattern SETTER_PATTERN = Pattern.compile("set([A-Z][a-zA-Z_0-9$]*)");
	private static final Pattern GETTER_PATTERN = Pattern.compile("get([A-Z][a-zA-Z_0-9$]*)");
	private static final Pattern BOOLEAN_IS_GETTER_PATTERN = Pattern.compile("is([A-Z][a-zA-Z_0-9$]*)");
	private static final Pattern BOOLEAN_HAS_GETTER_PATTERN = Pattern.compile("has([A-Z][a-zA-Z_0-9$]*)");
	private static final Pattern ADD_PATTERN = Pattern.compile("add([A-Z][a-zA-Z_0-9$]*)");
	private static final Pattern REMOVE_PATTERN = Pattern.compile("remove([A-Z][a-zA-Z_0-9$]*)");

	private final Method method;
	private final MethodType methodType;
	private final String propertyName;
	private final Supplier<PropertyHolder> propertyHolderSupplier;

	private MethodInfo(Method method, MethodType methodType, String propertyName, Supplier<PropertyHolder> propertyHolderSupplier) {
		this.method = method;
		this.methodType = methodType;
		this.propertyName = propertyName;
		this.propertyHolderSupplier = propertyHolderSupplier;
	}

	public static MethodInfo analyze(final Method method) {
		if (method == null) {
			throw new IllegalArgumentException("method must not be null");
		}
		if (method.isAnnotationPresent(Ignore.class)) {
			return new MethodInfo(method, MethodType.IGNORED, null, null);
		}

		final String methodName = method.getName();

		Matcher matcher = GETTER_PATTERN.matcher(methodName);
		if (matcher.matches()) {
			final String propertyName = matcher.group(1).substring(0, 1).toLowerCase() + matcher.group(1).substring(1);
			if (void.class.equals(method.getReturnType())) {
				throw new IllegalArgumentException("method " + method + " looks like a getter " +
					"for property " + propertyName + " but has void as return type. " +
					"Getter methods are expected to have a non-void return type. " +
					"Annotate this method with " + Ignore.class + " to let it ignore by this framework.");
			}
			if (method.getParameterCount() != 0) {
				throw new IllegalArgumentException("method " + method + " looks like a getter " +
					"for property " + propertyName + " but accepts parameters. " +
					"Getter methods are not expected to take parameters. " +
					"Annotate this method with " + Ignore.class + " to let it ignore by this framework.");
			}
			return new MethodInfo(method, MethodType.GETTER, propertyName, () ->
				PropertyHolderFactory.createPropertyFor(propertyName, method.getGenericReturnType()));
		}
		matcher = SETTER_PATTERN.matcher(methodName);
		if (matcher.matches()) {
			final String propertyName = matcher.group(1).substring(0, 1).toLowerCase() + matcher.group(1).substring(1);
			if (method.getParameterCount() == 1) {
				return new MethodInfo(method, MethodType.SETTER, propertyName, () ->
					PropertyHolderFactory.createPropertyFor(propertyName, method.getGenericParameterTypes()[0]));
			} else {
				throw new IllegalArgumentException("method " + method + " looks like a setter " +
					"for property " + propertyName + " but has a parameter count of " + method.getParameterCount() +
					". Setter methods are expected to have exactly one parameter. " +
					"Annotate this method with " + Ignore.class + " to let it ignore by this framework.");
			}
		}
		// the matcher assignments in the conditional are on purpose.
		if ((matcher = BOOLEAN_IS_GETTER_PATTERN.matcher(methodName)).matches() || (matcher = BOOLEAN_HAS_GETTER_PATTERN.matcher(methodName)).matches()) {
			final String propertyName = matcher.group(0);
			if (void.class.equals(method.getReturnType())) {
				throw new IllegalArgumentException("method " + method + " looks like a getter " +
					"for property " + propertyName + " but has void as return type. " +
					"Getter methods are expected to have a non-void return type. " +
					"Annotate this method with " + Ignore.class + " to let it ignore by this framework.");
			}
			if (method.getParameterCount() != 0) {
				throw new IllegalArgumentException("method " + method + " looks like a getter " +
					"for property " + propertyName + " but accepts parameters. " +
					"Getter methods are not expected to take parameters. " +
					"Annotate this method with " + Ignore.class + " to let it ignore by this framework.");
			}
			if (!(Boolean.class.equals(method.getReturnType()) || boolean.class.equals(method.getReturnType()))) {
				throw new IllegalArgumentException("method " + method + " looks like a getter " +
					"for a boolean property " + propertyName + " but has a non-boolean return type. " +
					"Boolean getter methods are expected to return a boolean. " +
					"Annotate this method with " + Ignore.class + " to let it ignore by this framework.");
			}
			return new MethodInfo(method, MethodType.GETTER, propertyName, () ->
				PropertyHolderFactory.createPropertyFor(propertyName, method.getGenericReturnType()));
		}
		matcher = ADD_PATTERN.matcher(methodName);
		if (matcher.matches()) {
			final String propertyName = matcher.group(1).substring(0, 1).toLowerCase() + matcher.group(1).substring(1) + "s";
			if (method.getParameterCount() == 1) {
				return new MethodInfo(method, MethodType.ADD, propertyName, () -> null);
			} else {
				throw new IllegalArgumentException("method " + method + " looks like a add method " +
					"for property " + propertyName + " but has a parameter count of " + method.getParameterCount() +
					". Add methods are expected to have exactly one parameter. " +
					"Annotate this method with " + Ignore.class + " to let it ignore by this framework.");
			}
		}
		matcher = REMOVE_PATTERN.matcher(methodName);
		if (matcher.matches()) {
			final String propertyName = matcher.group(1).substring(0, 1).toLowerCase() + matcher.group(1).substring(1) + "s";
			if (method.getParameterCount() == 1) {
				return new MethodInfo(method, MethodType.REMOVE, propertyName, () -> null);
			} else {
				throw new IllegalArgumentException("method " + method + " looks like a remove method " +
					"for property " + propertyName + " but has a parameter count of " + method.getParameterCount() +
					". Add methods are expected to have exactly one parameter. " +
					"Annotate this method with " + Ignore.class + " to let it ignore by this framework.");
			}
		}
		return null;
	}

	public Method getMethod() {
		return method;
	}

	public MethodType getMethodType() {
		return methodType;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public PropertyHolder createPropertyHolder() {
		return propertyHolderSupplier.get();
	}
}
