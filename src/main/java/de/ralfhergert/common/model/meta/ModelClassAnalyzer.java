package de.ralfhergert.common.model.meta;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class analyzes a given model class for all its properties.
 */
public class ModelClassAnalyzer {

	private static final Set<Method> genericSuperClassMethods = new HashSet<>(Arrays.asList(Class.class.getMethods()));

	private ModelClassAnalyzer() {}

	/**
	 * Analyzes the given typeClass.
	 */
	public static ModelClassInfo analyze(Class<?> typeClass) {
		if (typeClass == null) {
			throw new IllegalArgumentException("typeClass must not be null");
		}

		final Map<Method,MethodInfo> foundMethods = new HashMap<>();
		final Map<String,Type> foundProperties = new HashMap<>();

		for (Method method : typeClass.getMethods()) {
			if (genericSuperClassMethods.contains(method)) {
				continue;
			}
			final MethodInfo methodInfo = MethodInfo.analyze(method);
			if (methodInfo == null || methodInfo.getMethodType() == MethodInfo.MethodType.IGNORED) {
				continue;
			}
			foundMethods.put(method, methodInfo);

			if (!foundProperties.containsKey(methodInfo.getPropertyName())
				|| foundProperties.get(methodInfo.getPropertyName()) == null) {
				foundProperties.put(methodInfo.getPropertyName(), methodInfo.getPropertyType());
			} else if (methodInfo.getPropertyType() != null
				&& !methodInfo.getPropertyType().equals(foundProperties.get(methodInfo.getPropertyName()))) {
				throw new IllegalArgumentException("type mismatch on property named '" + methodInfo.getPropertyName() + "' detected on method '" + method.getName() + "'");
			}
		}

		// after all methods have been check verify that all properties are defined.
		for (Map.Entry<String,Type> entry : foundProperties.entrySet()) {
			if (entry.getValue() == null) {
				throw new IllegalArgumentException("undefined type for property named '" + entry.getKey() + "'. Add a proper get-method for this property.");
			}
		}

		return new ModelClassInfo(typeClass, foundMethods, foundProperties);
	}
}
