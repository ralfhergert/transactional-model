package de.ralfhergert.common.model.property;

import de.ralfhergert.common.model.context.Context;

public class IntPropertyHolder extends PropertyHolder<Integer> {

	public IntPropertyHolder(String propertyName) {
		super(propertyName, 0);
	}

	@Override
	public void setValue(Integer intValue, Context context) {
		if (intValue == null) {
			throw new IllegalArgumentException("primitive int value must not be null");
		}
		super.setValue(intValue, context);
	}
}
