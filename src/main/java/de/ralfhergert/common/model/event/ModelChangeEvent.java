package de.ralfhergert.common.model.event;

import de.ralfhergert.common.model.context.Context;

public class ModelChangeEvent {

	private final Context context;

	public ModelChangeEvent(Context context) {
		this.context = context;
	}
}
