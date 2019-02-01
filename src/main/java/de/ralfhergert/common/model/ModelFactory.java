package de.ralfhergert.common.model;

import de.ralfhergert.common.model.meta.ModelClassAnalyzer;
import de.ralfhergert.common.model.meta.ModelClassInfo;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ModelFactory {

	private static final Map<Class,ModelClassInfo> MODEL_CLASS_INFO_LOOKUP = new HashMap<>();

	private ModelFactory() {}

	public static <Type> Type create(ClassLoader classLoader, Class<Type> typeClass) {
		ModelClassInfo info = MODEL_CLASS_INFO_LOOKUP.computeIfAbsent(typeClass, ModelClassAnalyzer::analyze);
		return typeClass.cast(Proxy.newProxyInstance(classLoader, new Class[]{typeClass}, new ModelProxy(info)));
	}
}
