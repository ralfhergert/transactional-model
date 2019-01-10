package de.ralfhergert.common.model.property;

import de.ralfhergert.common.model.context.Context;
import de.ralfhergert.common.model.event.PropertyValueChangeEvent;

import java.util.Objects;

public class ValuePropertyHolder<Value> extends PropertyHolder {

	private Value value;

	public ValuePropertyHolder(String propertyName) {
		this(propertyName, null);
	}

	public ValuePropertyHolder(String propertyName, Value initialValue) {
		super(propertyName);
		value = initialValue;
	}

	public void setValue(Value value, Context context) {
		if (!Objects.equals(this.value, value)) {
			this.value = value;
			context.pushChangeEvent(new PropertyValueChangeEvent<>(context, propertyName, value));
		}
	}

	public Value getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ValuePropertyHolder<?> that = (ValuePropertyHolder<?>) o;
		return Objects.equals(propertyName, that.propertyName) &&
			Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(propertyName, value);
	}
}
