package de.ralfhergert.common.model.archtype;

import de.ralfhergert.common.model.event.ModelChangeEventListener;

public interface Model {

	void attachListener(ModelChangeEventListener listener);

	void detachListener(ModelChangeEventListener listener);
}
