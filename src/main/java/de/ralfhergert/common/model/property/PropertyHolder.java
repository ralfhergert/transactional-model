package de.ralfhergert.common.model.property;

import de.ralfhergert.common.model.context.Context;
import de.ralfhergert.common.model.event.PropertyValueChangeEvent;

import java.util.Objects;

public class PropertyHolder<Value> {

	private final String propertyName;

	private Value value;

	public PropertyHolder(String propertyName) {
		this(propertyName, null);
	}

	public PropertyHolder(String propertyName, Value initialValue) {
		if (propertyName == null) {
			throw new IllegalArgumentException("propertyName must not be null");
		}
		this.propertyName = propertyName;
		value = initialValue;
	}

	public String getPropertyName() {
		return propertyName;
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
		PropertyHolder<?> that = (PropertyHolder<?>) o;
		return Objects.equals(propertyName, that.propertyName) &&
			Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(propertyName, value);
	}
}
