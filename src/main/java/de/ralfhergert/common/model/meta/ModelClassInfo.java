package de.ralfhergert.common.model.meta;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * This class holds all analysis information gather about a single model class.
 * With this class the {@link de.ralfhergert.common.model.ModelFactory} must perform
 * its analysis only once per class and gets faster instantiating model proxies.
 */
public class ModelClassInfo {

	private final Class modelClass;

	private final Map<Method,MethodInfo> propertyMethods = new HashMap<>();
	private final Map<String,Type> propertyTypes = new HashMap<>();

	public ModelClassInfo(Class modelClass, Map<Method,MethodInfo> propertyMethods, Map<String,Type> propertyTypes) {
		this.modelClass = modelClass;
		this.propertyMethods.putAll(propertyMethods);
		this.propertyTypes.putAll(propertyTypes);
	}

	public Map<Method,MethodInfo> getPropertyMethods() {
		return propertyMethods;
	}

	public Map<String,Type> getPropertyTypes() {
		return propertyTypes;
	}
}
