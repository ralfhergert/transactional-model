package de.ralfhergert.common.model.property;

import de.ralfhergert.common.model.TestContext;
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
	public void testPropertyHoldersEqualEachOther() {
		Assert.assertEquals("property holders should equal each other", new ValuePropertyHolder<String>("abc"), new ValuePropertyHolder<String>("abc"));
	}

	@Test
	public void testPropertyHoldersWithDifferentPropertyNameDiffer() {
		Assert.assertNotEquals("property holders for different properties should not equal each other", new ValuePropertyHolder<String>("a"), new ValuePropertyHolder<String>("b"));
	}

	@Test
	public void testPropertyHoldersWithDifferentValuesDiffer() {
		ValuePropertyHolder<String> propertyHolder = new ValuePropertyHolder<>("abc");
		propertyHolder.setValue("one", new TestContext());
		Assert.assertNotEquals("property holders with different values should not equal each other", new ValuePropertyHolder<String>("abc"), propertyHolder);
	}
}
