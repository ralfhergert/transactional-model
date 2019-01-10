package de.ralfhergert.common.model.property;

public class PropertyHolderFactory {

	private PropertyHolderFactory() { /* no need to instantiate */ }

	public static <Type> PropertyHolder createPropertyFor(final String propertyName, Type typeClass) {
		if (propertyName == null) {
			throw new IllegalArgumentException("property name must not be null");
		}
		if (typeClass == null) {
			throw new IllegalArgumentException("typeClass name must not be null");
		}
		if (boolean.class.equals(typeClass)) {
			return new NonNullValuePropertyHolder<>(propertyName, false);
		} else if (byte.class.equals(typeClass)) {
			return new NonNullValuePropertyHolder<>(propertyName, (byte)0);
		} else if (char.class.equals(typeClass)) {
			return new NonNullValuePropertyHolder<>(propertyName, (char)0);
		} else if (double.class.equals(typeClass)) {
			return new NonNullValuePropertyHolder<>(propertyName, 0d);
		} else if (float.class.equals(typeClass)) {
			return new NonNullValuePropertyHolder<>(propertyName, 0f);
		} else if (long.class.equals(typeClass)) {
			return new NonNullValuePropertyHolder<>(propertyName, 0L);
		} else if (int.class.equals(typeClass)) {
			return new NonNullValuePropertyHolder<>(propertyName, 0);
		} else if (short.class.equals(typeClass)) {
			return new NonNullValuePropertyHolder<>(propertyName, (short)0);
		} else {
			return new ValuePropertyHolder<>(propertyName);
		}
	}
}
