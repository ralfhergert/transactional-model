package de.ralfhergert.common.model.property;

public class PropertyHolderFactory {

	private PropertyHolderFactory() { /* no need to instantiate */ }

	public static <Type> PropertyHolder<Type> createPropertyFor(final String propertyName, Type typeClass) {
		if (propertyName == null) {
			throw new IllegalArgumentException("property name must not be null");
		}
		if (typeClass == null) {
			throw new IllegalArgumentException("typeClass name must not be null");
		}
		if (boolean.class.equals(typeClass)) {
			return ((PropertyHolder<Type>) new NonNullPropertyHolder<>(propertyName, false));
		} else if (byte.class.equals(typeClass)) {
			return ((PropertyHolder<Type>)new NonNullPropertyHolder<>(propertyName, (byte)0));
		} else if (char.class.equals(typeClass)) {
			return ((PropertyHolder<Type>)new NonNullPropertyHolder<>(propertyName, (char)0));
		} else if (double.class.equals(typeClass)) {
			return ((PropertyHolder<Type>)new NonNullPropertyHolder<>(propertyName, 0d));
		} else if (float.class.equals(typeClass)) {
			return ((PropertyHolder<Type>)new NonNullPropertyHolder<>(propertyName, 0f));
		} else if (long.class.equals(typeClass)) {
			return ((PropertyHolder<Type>)new NonNullPropertyHolder<>(propertyName, 0L));
		} else if (int.class.equals(typeClass)) {
			return ((PropertyHolder<Type>)new NonNullPropertyHolder<>(propertyName, 0));
		} else if (short.class.equals(typeClass)) {
			return ((PropertyHolder<Type>)new NonNullPropertyHolder<>(propertyName, (short)0));
		} else {
			return new PropertyHolder<>(propertyName);
		}
	}
}
