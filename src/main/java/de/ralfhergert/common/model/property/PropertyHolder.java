package de.ralfhergert.common.model.property;

import java.util.Objects;

public class PropertyHolder {

	protected final String propertyName;

	public PropertyHolder(String propertyName) {
		if (propertyName == null) {
			throw new IllegalArgumentException("propertyName must not be null");
		}
		this.propertyName = propertyName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public boolean equals(Object o) {
		return (this == o)
			|| (o instanceof PropertyHolder)
			&& Objects.equals(propertyName, ((PropertyHolder)o).propertyName);
	}

	@Override
	public int hashCode() {
		return propertyName.hashCode();
	}
}
