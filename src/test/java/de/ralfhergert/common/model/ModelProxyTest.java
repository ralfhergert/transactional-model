package de.ralfhergert.common.model;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * This test ensures that {@link ModelProxy} is working correctly.
 */
public class ModelProxyTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testNullClassIsRejected() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("typeClass must not be null");

		new ModelProxy(null);
	}

	@Test
	public void testIncompatiblePropertyTypesAreRejected() {
		class A {
			public void setFoo(int value) {}
			public Integer getFoo() { return null; }
		}

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("type mismatch on property named 'foo' detected on method");

		new ModelProxy(A.class);
	}

	@Test
	public void testSuccessfulModelProxyCreation() {
		class A {
			public void setFoo(int value) {}
			public int getFoo() { return 0; }
		}

		ModelProxy proxy = new ModelProxy(A.class);
		Assert.assertNotNull("proxy should not be null", proxy);
		Assert.assertEquals("number of properties supported by proxy", 1, proxy.getProperties().size());
		Assert.assertTrue("property name 'foo' should be one of the supported properties", proxy.getProperties().containsKey("foo"));
	}
}
