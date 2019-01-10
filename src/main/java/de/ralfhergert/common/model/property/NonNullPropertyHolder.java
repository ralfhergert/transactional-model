package de.ralfhergert.common.model.property;

import de.ralfhergert.common.model.context.Context;

public class NonNullPropertyHolder<Type> extends PropertyHolder<Type> {

	public NonNullPropertyHolder(String propertyName, Type initValue) {
		super(propertyName, initValue);
		if (initValue == null) {
			throw new IllegalArgumentException("initValue must not be null");
		}
	}

	@Override
	public void setValue(Type value, Context context) {
		if (value == null) {
			throw new IllegalArgumentException("value must not be null");
		}
		super.setValue(value, context);
	}
}
