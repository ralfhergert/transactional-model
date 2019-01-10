package de.ralfhergert.common.model.property;

import de.ralfhergert.common.model.context.Context;
import de.ralfhergert.common.model.event.ElementAddedChangeEvent;
import de.ralfhergert.common.model.event.ElementRemovedChangeEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListPropertyHolder<Element> extends NonNullValuePropertyHolder<List<Element>> {

	public ListPropertyHolder(String propertyName) {
		super(propertyName, new ArrayList<>());
	}

	/**
	 * Adds the given element to the internal list.
	 * @return true if the list was modified.
	 */
	public boolean addElement(Element element, Context context) {
		if (element == null) {
			throw new IllegalArgumentException("element must not be null");
		}
		if (value.add(element)) {
			context.pushChangeEvent(new ElementAddedChangeEvent<>(context, propertyName, element));
			return true;
		}
		return false;
	}

	/**
	 * Removes the given element from the internal list.
	 * @return true if the list was modified.
	 */
	public boolean removeElement(Element element, Context context) {
		if (element == null) {
			throw new IllegalArgumentException("element must not be null");
		}
		if (value.remove(element)) {
			context.pushChangeEvent(new ElementRemovedChangeEvent<>(context, propertyName, element));
			return true;
		}
		return false;
	}

	@Override
	public List<Element> getValue() {
		return Collections.unmodifiableList(value);
	}
}
