package de.ralfhergert.common.model;

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
	public void testNullModelClassInfoIsRejected() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("modelClassInfo must not be null");

		new ModelProxy(null);
	}
}
