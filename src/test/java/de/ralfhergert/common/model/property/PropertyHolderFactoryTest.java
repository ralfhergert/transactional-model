package de.ralfhergert.common.model.property;

import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensures that {@link PropertyHolderFactory} is working correctly.
 */
public class PropertyHolderFactoryTest {

	@Test(expected = IllegalArgumentException.class)
	public void testNullPropertyNameIsRejected() {
		PropertyHolderFactory.createPropertyFor(null, Integer.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullClassIsRejected() {
		PropertyHolderFactory.createPropertyFor("foo", null);
	}

	@Test
	public void testCreatePropertyHolderForPrimitiveBoolean() {
		PropertyHolder propertyHolder = PropertyHolderFactory.createPropertyFor("foo", boolean.class);
		Assert.assertNotNull("propertyHolder should not be null", propertyHolder);
		Assert.assertEquals("property name should be", "foo", propertyHolder.getPropertyName());
	}

	@Test
	public void testCreatePropertyHolderForPrimitiveByte() {
		PropertyHolder propertyHolder = PropertyHolderFactory.createPropertyFor("foo", byte.class);
		Assert.assertNotNull("propertyHolder should not be null", propertyHolder);
		Assert.assertEquals("property name should be", "foo", propertyHolder.getPropertyName());
	}

	@Test
	public void testCreatePropertyHolderForPrimitiveChar() {
		PropertyHolder propertyHolder = PropertyHolderFactory.createPropertyFor("foo", char.class);
		Assert.assertNotNull("propertyHolder should not be null", propertyHolder);
		Assert.assertEquals("property name should be", "foo", propertyHolder.getPropertyName());
	}

	@Test
	public void testCreatePropertyHolderForPrimitiveDouble() {
		PropertyHolder propertyHolder = PropertyHolderFactory.createPropertyFor("foo", double.class);
		Assert.assertNotNull("propertyHolder should not be null", propertyHolder);
		Assert.assertEquals("property name should be", "foo", propertyHolder.getPropertyName());
	}

	@Test
	public void testCreatePropertyHolderForPrimitiveFloat() {
		PropertyHolder propertyHolder = PropertyHolderFactory.createPropertyFor("foo", float.class);
		Assert.assertNotNull("propertyHolder should not be null", propertyHolder);
		Assert.assertEquals("property name should be", "foo", propertyHolder.getPropertyName());
	}

	@Test
	public void testCreatePropertyHolderForPrimitiveInt() {
		PropertyHolder propertyHolder = PropertyHolderFactory.createPropertyFor("foo", int.class);
		Assert.assertNotNull("propertyHolder should not be null", propertyHolder);
		Assert.assertEquals("property name should be", "foo", propertyHolder.getPropertyName());
	}

	@Test
	public void testCreatePropertyHolderForPrimitiveLong() {
		PropertyHolder propertyHolder = PropertyHolderFactory.createPropertyFor("foo", long.class);
		Assert.assertNotNull("propertyHolder should not be null", propertyHolder);
		Assert.assertEquals("property name should be", "foo", propertyHolder.getPropertyName());
	}

	@Test
	public void testCreatePropertyHolderForPrimitiveShort() {
		PropertyHolder propertyHolder = PropertyHolderFactory.createPropertyFor("foo", short.class);
		Assert.assertNotNull("propertyHolder should not be null", propertyHolder);
		Assert.assertEquals("property name should be", "foo", propertyHolder.getPropertyName());
	}

	@Test
	public void testCreatePropertyHolderForBoolean() {
		PropertyHolder propertyHolder = PropertyHolderFactory.createPropertyFor("foo", Boolean.class);
		Assert.assertNotNull("propertyHolder should not be null", propertyHolder);
		Assert.assertEquals("property name should be", "foo", propertyHolder.getPropertyName());
	}

	@Test
	public void testCreatePropertyHolderForByte() {
		PropertyHolder propertyHolder = PropertyHolderFactory.createPropertyFor("foo", Byte.class);
		Assert.assertNotNull("propertyHolder should not be null", propertyHolder);
		Assert.assertEquals("property name should be", "foo", propertyHolder.getPropertyName());
	}

	@Test
	public void testCreatePropertyHolderForCharacter() {
		PropertyHolder propertyHolder = PropertyHolderFactory.createPropertyFor("foo", Character.class);
		Assert.assertNotNull("propertyHolder should not be null", propertyHolder);
		Assert.assertEquals("property name should be", "foo", propertyHolder.getPropertyName());
	}

	@Test
	public void testCreatePropertyHolderForDouble() {
		PropertyHolder propertyHolder = PropertyHolderFactory.createPropertyFor("foo", Double.class);
		Assert.assertNotNull("propertyHolder should not be null", propertyHolder);
		Assert.assertEquals("property name should be", "foo", propertyHolder.getPropertyName());
	}

	@Test
	public void testCreatePropertyHolderForFloat() {
		PropertyHolder propertyHolder = PropertyHolderFactory.createPropertyFor("foo", Float.class);
		Assert.assertNotNull("propertyHolder should not be null", propertyHolder);
		Assert.assertEquals("property name should be", "foo", propertyHolder.getPropertyName());
	}

	@Test
	public void testCreatePropertyHolderForInteger() {
		PropertyHolder propertyHolder = PropertyHolderFactory.createPropertyFor("foo", Integer.class);
		Assert.assertNotNull("propertyHolder should not be null", propertyHolder);
		Assert.assertEquals("property name should be", "foo", propertyHolder.getPropertyName());
	}

	@Test
	public void testCreatePropertyHolderForLong() {
		PropertyHolder propertyHolder = PropertyHolderFactory.createPropertyFor("foo", Long.class);
		Assert.assertNotNull("propertyHolder should not be null", propertyHolder);
		Assert.assertEquals("property name should be", "foo", propertyHolder.getPropertyName());
	}

	@Test
	public void testCreatePropertyHolderForShort() {
		PropertyHolder propertyHolder = PropertyHolderFactory.createPropertyFor("foo", Short.class);
		Assert.assertNotNull("propertyHolder should not be null", propertyHolder);
		Assert.assertEquals("property name should be", "foo", propertyHolder.getPropertyName());
	}

	@Test
	public void testCreatePropertyHolderForString() {
		PropertyHolder propertyHolder = PropertyHolderFactory.createPropertyFor("foo", String.class);
		Assert.assertNotNull("propertyHolder should not be null", propertyHolder);
		Assert.assertEquals("property name should be", "foo", propertyHolder.getPropertyName());
	}
}
