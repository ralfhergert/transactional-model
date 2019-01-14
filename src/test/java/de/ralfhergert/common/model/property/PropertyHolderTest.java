package de.ralfhergert.common.model.property;

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

		new PropertyHolder(null);
	}

	@Test
	public void testEmptyPropertyNameIsRejected() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("propertyName must not be empty");

		new PropertyHolder("");
	}

	@Test
	public void testPropertyHoldersWithEqualNameAreEqual() {
		Assert.assertEquals("propertyHolders with equal name are equal", new PropertyHolder("color"), new PropertyHolder("color"));
	}

	@Test
	public void testPropertyHoldersWithDifferentNamesAreUnequal() {
		Assert.assertNotEquals("propertyHolders with different names are unequal", new PropertyHolder("color"), new PropertyHolder("brightness"));
	}

	@Test
	public void testEqualPropertyHoldersHaveToSameHashCode() {
		Assert.assertEquals("equal propertyHolders have the same hashCode", new PropertyHolder("color").hashCode(), new PropertyHolder("color").hashCode());
	}

	@Test
	public void testUnequalPropertyHoldersHaveDifferentHashCode() {
		Assert.assertNotEquals("unequal propertyHolders have a different hashCode", new PropertyHolder("color").hashCode(), new PropertyHolder("brightness").hashCode());
	}
}
