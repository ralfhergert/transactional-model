package de.ralfhergert.common.model;

import de.ralfhergert.common.model.archtype.Model;
import de.ralfhergert.common.model.event.ModelChangeEvent;
import de.ralfhergert.common.model.event.PropertyValueChangeEvent;
import org.junit.Assert;
import org.junit.Test;

public class ChangeEventOnSetProperty {

	public interface A extends Model {

		void setValue(int value);

		int getValue();
	}

	@Test
	public void testChangeEventIsFiredOnSettingProperty() {
		// prepare
		final A instance = ModelFactory.create(getClass().getClassLoader(), A.class);
		Assert.assertNotNull("instance should not be null", instance);

		final RecordingModelChangeEventListener listener = new RecordingModelChangeEventListener();
		instance.attachListener(listener);

		Assert.assertEquals("current value should be", 0, instance.getValue());

		// test
		instance.setValue(5);

		// verify
		Assert.assertEquals("number of received events", 1, listener.getEvents().size());
		ModelChangeEvent modelChangeEvent = listener.getEvents().get(0);
		Assert.assertEquals("change event should be of type", PropertyValueChangeEvent.class, modelChangeEvent.getClass());
		PropertyValueChangeEvent propertyValueChangeEvent = (PropertyValueChangeEvent)modelChangeEvent;
		Assert.assertEquals("property name should be", "value", propertyValueChangeEvent.getPropertyName());
		Assert.assertEquals("new value should be", 5, propertyValueChangeEvent.getValue());

		Assert.assertEquals("current value should be", 5, instance.getValue());
	}
}
