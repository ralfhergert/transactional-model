package de.ralfhergert.common.model.meta;

import de.ralfhergert.common.model.Ignore;
import de.ralfhergert.common.model.property.PropertyHolder;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * This test ensure that {@link MethodInfo} is working correctly.
 */
public class MethodInfoTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testNullMethodIsRejected() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("method must not be null");

		MethodInfo.analyze(null);
	}

	@Test
	public void testIgnoredMethodIsIgnored() throws NoSuchMethodException {
		class A {
			@Ignore(reason = "method is ignored for testing purposes")
			public void setFoo() {}
		}

		// test
		final MethodInfo info = MethodInfo.analyze(A.class.getMethod("setFoo"));

		// verify
		Assert.assertNotNull("method info should not be null", info);
		Assert.assertEquals("method type should be", MethodInfo.MethodType.IGNORED, info.getMethodType());
	}

	@Test
	public void testUnknownMethodGivesNoInfo() throws NoSuchMethodException {
		class A {
			public void createFoo() {}
		}

		// test
		final MethodInfo info = MethodInfo.analyze(A.class.getMethod("createFoo"));

		// verify
		Assert.assertNull("method info should be null", info);
	}

	@Test
	public void testPublicVoidSetterMethodIsDetected() throws NoSuchMethodException {
		class A {
			public void setFooBar(int value) {}
		}

		// test
		final MethodInfo info = MethodInfo.analyze(A.class.getMethod("setFooBar", int.class));

		// verify
		Assert.assertNotNull("method info should not be null", info);
		Assert.assertNotNull("method should not be null", info.getMethod());
		Assert.assertEquals("method type should be", MethodInfo.MethodType.SETTER, info.getMethodType());
		Assert.assertEquals("propertyName should be", "fooBar", info.getPropertyName());
		PropertyHolder propertyHolder = info.createPropertyHolder();
		Assert.assertNotNull("propertyHolder should not be null", propertyHolder);
		Assert.assertEquals("propertyHolder's propertyName should be", "fooBar", propertyHolder.getPropertyName());
	}



	@Test
	public void testSetterMethodWithoutParameterIsRejected() throws NoSuchMethodException {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("setFoo() looks like a setter for property foo but has a parameter count of 0.");

		class A {
			public void setFoo() {}
		}

		// test
		MethodInfo.analyze(A.class.getMethod("setFoo"));
	}

	@Test
	public void testPublicIntGetterMethodIsDetected() throws NoSuchMethodException {
		class A {
			public int getFooBar() { return 0; }
		}

		// test
		final MethodInfo info = MethodInfo.analyze(A.class.getMethod("getFooBar"));

		// verify
		Assert.assertNotNull("method info should not be null", info);
		Assert.assertNotNull("method should not be null", info.getMethod());
		Assert.assertEquals("method type should be", MethodInfo.MethodType.GETTER, info.getMethodType());
		Assert.assertEquals("propertyName should be", "fooBar", info.getPropertyName());
		PropertyHolder propertyHolder = info.createPropertyHolder();
		Assert.assertNotNull("propertyHolder should not be null", propertyHolder);
		Assert.assertEquals("propertyHolder's propertyName should be", "fooBar", propertyHolder.getPropertyName());
	}

	@Test
	public void testGetterMethodWithParametersIsRejected() throws NoSuchMethodException {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("getFoo(boolean) looks like a getter for property foo but accepts parameters.");

		class A {
			public int getFoo(boolean a) { return 0; }
		}

		// test
		MethodInfo.analyze(A.class.getMethod("getFoo", boolean.class));
	}

	@Test
	public void testGetterMethodReturningVoidIsRejected() throws NoSuchMethodException {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("getFoo() looks like a getter for property foo but has void as return type.");

		class A {
			public void getFoo() {}
		}

		// test
		MethodInfo.analyze(A.class.getMethod("getFoo"));
	}
}
