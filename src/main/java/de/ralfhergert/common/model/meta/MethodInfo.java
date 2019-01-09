package de.ralfhergert.common.model.meta;

import de.ralfhergert.common.model.property.PropertyHolder;
import de.ralfhergert.common.model.property.PropertyHolderFactory;

import java.lang.reflect.Method;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class MethodInfo {

	public enum MethodType {
		SETTER,
		GETTER
	}

	// patterns for method names regarding https://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.8
	private static final Pattern SETTER_PATTERN = Pattern.compile("set[A-Z][a-zA-Z_0-9$]*");
	private static final Pattern GETTER_PATTERN = Pattern.compile("get[A-Z][a-zA-Z_0-9$]*");

	private final Method method;
	private final MethodType methodType;
	private final String propertyName;
	private final Supplier<PropertyHolder> propertyHolderSupplier;

	public MethodInfo(Method method) {
		if (method == null) {
			throw new IllegalArgumentException("method must not be null");
		}
		this.method = method;
		final String methodName = method.getName();
		if (method.getParameterCount() == 1 && SETTER_PATTERN.matcher(methodName).matches()) {
			methodType = MethodType.SETTER;
			propertyName = methodName.substring(3,4).toLowerCase() + methodName.substring(4);
			propertyHolderSupplier = () -> PropertyHolderFactory.createPropertyFor(propertyName, method.getGenericParameterTypes()[0]);
		} else if (method.getParameterCount() == 0 && GETTER_PATTERN.matcher(methodName).matches()) {
			methodType = MethodType.GETTER;
			propertyName = methodName.substring(3,4).toLowerCase() + methodName.substring(4);
			propertyHolderSupplier = () -> PropertyHolderFactory.createPropertyFor(propertyName, method.getGenericReturnType());
		} else {
			throw new IllegalArgumentException("could not get method-type for " + method);
		}
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
