package de.ralfhergert.common.model;

import java.lang.reflect.Proxy;

public class ModelFactory {

	private ModelFactory() {}

	public static <Type> Type create(ClassLoader classLoader, Class<Type> typeClass) {
		return typeClass.cast(Proxy.newProxyInstance(classLoader, new Class[]{typeClass}, new ModelProxy(typeClass)));
	}
}
