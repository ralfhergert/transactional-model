package de.ralfhergert.common.model;

import de.ralfhergert.common.model.archtype.Model;
import de.ralfhergert.common.model.event.ElementAddedChangeEvent;
import de.ralfhergert.common.model.event.ElementRemovedChangeEvent;
import de.ralfhergert.common.model.event.ModelChangeEvent;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ChangeEventOnListModificationsTest {

	public interface A extends Model {

		void addName(String name);

		void removeName(String name);

		List<String> getNames();
	}

	@Test
	public void testChangeEventIsFiredOnAddingElement() {
		// prepare
		final A instance = ModelFactory.create(getClass().getClassLoader(), A.class);
		Assert.assertNotNull("instance should not be null", instance);

		final RecordingModelChangeEventListener listener = new RecordingModelChangeEventListener();
		instance.attachListener(listener);

		Assert.assertEquals("current list size should be", 0, instance.getNames().size());

		// test
		instance.addName("ACME");

		// verify
		Assert.assertEquals("number of received events", 1, listener.getEvents().size());
		ModelChangeEvent modelChangeEvent = listener.getEvents().get(0);
		Assert.assertEquals("change event should be of type", ElementAddedChangeEvent.class, modelChangeEvent.getClass());
		ElementAddedChangeEvent elementAddedChangeEvent = (ElementAddedChangeEvent)modelChangeEvent;
		Assert.assertEquals("property name should be", "names", elementAddedChangeEvent.getPropertyName());
		Assert.assertEquals("new value should be", "ACME", elementAddedChangeEvent.getElement());

		Assert.assertEquals("current list size should be", 1, instance.getNames().size());
		Assert.assertEquals("name in list should be", "ACME", instance.getNames().get(0));
	}

	@Test
	public void testChangeEventIsFiredOnRemovingElement() {
		// prepare
		final A instance = ModelFactory.create(getClass().getClassLoader(), A.class);
		Assert.assertNotNull("instance should not be null", instance);
		instance.addName("ACME");

		final RecordingModelChangeEventListener listener = new RecordingModelChangeEventListener();
		instance.attachListener(listener);

		Assert.assertEquals("current list size should be", 1, instance.getNames().size());
		Assert.assertEquals("name in list should be", "ACME", instance.getNames().get(0));

		// test
		instance.removeName("ACME");

		// verify
		Assert.assertEquals("number of received events", 1, listener.getEvents().size());
		ModelChangeEvent modelChangeEvent = listener.getEvents().get(0);
		Assert.assertEquals("change event should be of type", ElementRemovedChangeEvent.class, modelChangeEvent.getClass());
		ElementRemovedChangeEvent elementAddedChangeEvent = (ElementRemovedChangeEvent)modelChangeEvent;
		Assert.assertEquals("property name should be", "names", elementAddedChangeEvent.getPropertyName());
		Assert.assertEquals("new value should be", "ACME", elementAddedChangeEvent.getElement());

		Assert.assertEquals("current list size should be", 0, instance.getNames().size());
	}
}
