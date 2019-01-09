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
		if (int.class.equals(typeClass)) {
			return ((PropertyHolder<Type>)new IntPropertyHolder(propertyName));
		} else {
			return new PropertyHolder<>(propertyName);
		}
	}
}
