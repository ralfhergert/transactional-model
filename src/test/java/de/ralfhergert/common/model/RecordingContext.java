package de.ralfhergert.common.model;

import de.ralfhergert.common.model.context.Context;
import de.ralfhergert.common.model.event.ModelChangeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecordingContext implements Context {

	private final List<ModelChangeEvent> events = new ArrayList<>();

	@Override
	public void pushChangeEvent(ModelChangeEvent... events) {
		this.events.addAll(Arrays.asList(events));
	}

	public List<ModelChangeEvent> getEvents() {
		return events;
	}
}
