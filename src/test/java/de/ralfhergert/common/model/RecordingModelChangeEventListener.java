package de.ralfhergert.common.model;

import de.ralfhergert.common.model.event.ModelChangeEvent;
import de.ralfhergert.common.model.event.ModelChangeEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecordingModelChangeEventListener implements ModelChangeEventListener {

	private final List<ModelChangeEvent> events = new ArrayList<>();

	@Override
	public void process(ModelChangeEvent... events) {
		if (events != null) {
			this.events.addAll(Arrays.asList(events));
		}
	}

	public List<ModelChangeEvent> getEvents() {
		return events;
	}
}
