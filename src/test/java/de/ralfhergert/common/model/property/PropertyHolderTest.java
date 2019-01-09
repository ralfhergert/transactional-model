package de.ralfhergert.common.model.property;

import de.ralfhergert.common.model.TestContext;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * This test ensures that {@link PropertyHolder} is working correctly.
 */
public class PropertyHolderTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testNullPropertyNameIsRejected() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("propertyName must not be null");

		new PropertyHolder<String>(null);
	}

	@Test
	public void testPropertyHoldersEqualEachOther() {
		Assert.assertEquals("property holders should equal each other", new PropertyHolder<String>("abc"), new PropertyHolder<String>("abc"));
	}

	@Test
	public void testPropertyHoldersWithDifferentPropertyNameDiffer() {
		Assert.assertNotEquals("property holders for different properties should not equal each other", new PropertyHolder<String>("a"), new PropertyHolder<String>("b"));
	}

	@Test
	public void testPropertyHoldersWithDifferentValuesDiffer() {
		PropertyHolder<String> propertyHolder = new PropertyHolder<>("abc");
		propertyHolder.setValue("one", new TestContext());
		Assert.assertNotEquals("property holders with different values should not equal each other", new PropertyHolder<String>("abc"), propertyHolder);
	}
}
