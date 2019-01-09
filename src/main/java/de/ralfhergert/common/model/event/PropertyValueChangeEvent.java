package de.ralfhergert.common.model.event;

import de.ralfhergert.common.model.context.Context;

public class PropertyValueChangeEvent<Value> extends ModelChangeEvent {

	private final String propertyName;
	private final Value value;

	public PropertyValueChangeEvent(final Context context, final String propertyName, Value value) {
		super(context);
		this.propertyName = propertyName;
		this.value = value;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Value getValue() {
		return value;
	}
}
