package de.ralfhergert.common.model.property;

import de.ralfhergert.common.model.RecordingContext;
import de.ralfhergert.common.model.event.ModelChangeEvent;
import de.ralfhergert.common.model.event.PropertyValueChangeEvent;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * This test ensures that {@link ValuePropertyHolder} is working correctly.
 */
public class ValuePropertyHolderTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testNullPropertyNameIsRejected() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("propertyName must not be null");

		new ValuePropertyHolder<String>(null);
	}

	@Test
	public void testEventIsThrownOnSettingNewValue() {
		// prepare
		final ValuePropertyHolder<String> propertyHolder = new ValuePropertyHolder<>("abc", "one");
		Assert.assertEquals("value should be", "one", propertyHolder.getValue());

		final RecordingContext context = new RecordingContext();
		// test
		propertyHolder.setValue("two", context);

		// verify
		Assert.assertEquals("value should be", "two", propertyHolder.getValue());
		Assert.assertEquals("number of events", 1, context.getEvents().size());
		ModelChangeEvent modelChangeEvent = context.getEvents().get(0);
		Assert.assertEquals("event type should be", PropertyValueChangeEvent.class, modelChangeEvent.getClass());
		PropertyValueChangeEvent propertyValueChangeEvent = (PropertyValueChangeEvent)modelChangeEvent;
		Assert.assertEquals("new value should be", "two", propertyValueChangeEvent.getValue());
	}

	@Test
	public void testNoEventIsThrownOnSettingSameValue() {
		// prepare
		final ValuePropertyHolder<String> propertyHolder = new ValuePropertyHolder<>("abc", "one");
		Assert.assertEquals("value should be", "one", propertyHolder.getValue());

		final RecordingContext context = new RecordingContext();
		// test
		propertyHolder.setValue("one", context);

		// verify
		Assert.assertEquals("value should be", "one", propertyHolder.getValue());
		Assert.assertEquals("number of events", 0, context.getEvents().size());
	}

	@Test
	public void testPropertyHoldersEqualEachOther() {
		Assert.assertEquals("property holders should equal each other", new ValuePropertyHolder<String>("abc"), new ValuePropertyHolder<String>("abc"));
	}

	@Test
	public void testPropertyHoldersWithDifferentPropertyNameDiffer() {
		Assert.assertNotEquals("property holders for different properties should not equal each other", new ValuePropertyHolder<String>("a"), new ValuePropertyHolder<String>("b"));
	}

	@Test
	public void testPropertyHoldersWithDifferentValuesDiffer() {
		Assert.assertNotEquals("property holders with different values should differ", new ValuePropertyHolder<>("abc", "one"), new ValuePropertyHolder<String>("abc"));
	}

	@Test
	public void testEqualValuePropertyHoldersHaveEqualHashCode() {
		Assert.assertEquals("property holders should have equal hashCode", new ValuePropertyHolder<String>("abc").hashCode(), new ValuePropertyHolder<String>("abc").hashCode());
	}

	@Test
	public void testUnequalValuePropertyHoldersHaveDifferentHashCode() {
		Assert.assertNotEquals("unequal property holders should have different hashCodes", new ValuePropertyHolder<>("a", "foo").hashCode(), new ValuePropertyHolder<>("a", "bar").hashCode());
	}
}
