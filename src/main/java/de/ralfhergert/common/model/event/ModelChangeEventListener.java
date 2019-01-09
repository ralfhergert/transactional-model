package de.ralfhergert.common.model.event;

public interface ModelChangeEventListener {

	void process(ModelChangeEvent... events);
}
