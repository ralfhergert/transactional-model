package de.ralfhergert.common.model.event;

import de.ralfhergert.common.model.context.Context;

public class ElementAddedChangeEvent<Element> extends ModelChangeEvent {

	private final String propertyName;
	private final Element element;

	public ElementAddedChangeEvent(final Context context, final String propertyName, Element element) {
		super(context);
		this.propertyName = propertyName;
		this.element = element;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Element getElement() {
		return element;
	}
}
