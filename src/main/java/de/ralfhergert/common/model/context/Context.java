package de.ralfhergert.common.model.context;

import de.ralfhergert.common.model.event.ModelChangeEvent;

public interface Context {

	void pushChangeEvent(ModelChangeEvent... event);
}
