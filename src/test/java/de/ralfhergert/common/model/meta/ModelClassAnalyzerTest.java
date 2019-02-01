package de.ralfhergert.common.model.meta;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * This test ensures that {@link ModelClassAnalyzer} is working correctly.
 */
public class ModelClassAnalyzerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testNullClassIsRejected() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("typeClass must not be null");

		ModelClassAnalyzer.analyze(null);
	}

	@Test
	public void testIncompatiblePropertyTypesAreRejected() {
		class A {
			public void setFoo(int value) {}
			public Integer getFoo() { return null; }
		}

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("type mismatch on property named 'foo' detected on method");

		ModelClassAnalyzer.analyze(A.class);
	}

	@Test
	public void testSuccessfulModelClassAnalysis() {
		class A {
			public void setFoo(int value) {}
			public int getFoo() { return 0; }
		}

		ModelClassInfo info = ModelClassAnalyzer.analyze(A.class);
		Assert.assertNotNull("proxy should not be null", info);
		Assert.assertEquals("number of properties supported found", 1, info.getPropertyTypes().size());
		Assert.assertTrue("property name 'foo' should be one of the supported properties", info.getPropertyTypes().containsKey("foo"));
	}


	/**
	 * Add methods alone are not sufficient to define a property type. It is not clear,
	 * whether a {@link java.util.List}, {@link java.util.Set} or some other collection
	 * shall be used.
	 */
	@Test
	public void testPublicAddMethodWithoutGetterIsRejected() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("undefined type for property named 'values'. Add a proper get-method for this property.");

		class A {
			public void addValue(String value) {}
		}

		// test
		ModelClassAnalyzer.analyze(A.class);
	}
}
