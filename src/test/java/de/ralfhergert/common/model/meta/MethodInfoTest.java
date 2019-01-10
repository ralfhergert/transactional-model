package de.ralfhergert.common.model.meta;

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

		new MethodInfo(null);
	}
}
