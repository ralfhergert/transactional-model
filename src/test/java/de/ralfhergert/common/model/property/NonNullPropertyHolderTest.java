package de.ralfhergert.common.model.property;

import de.ralfhergert.common.model.TestContext;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * This test ensures that {@link NonNullValuePropertyHolder} is working correctly.
 */
public class NonNullPropertyHolderTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testTryingToInitializeWithNullValueIsRejected() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("initValue must not be null");

		new NonNullValuePropertyHolder<Integer>("foo", null);
	}

	@Test
	public void testTryingToSetNullValueIsRejected() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("value must not be null");

		new NonNullValuePropertyHolder<>("foo", 0).setValue(null, new TestContext());
	}
}
