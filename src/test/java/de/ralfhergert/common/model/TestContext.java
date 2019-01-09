package de.ralfhergert.common.model;

import de.ralfhergert.common.model.context.Context;
import de.ralfhergert.common.model.event.ModelChangeEvent;

public class TestContext implements Context {

	@Override
	public void pushChangeEvent(ModelChangeEvent... event) {}
}
