package de.ralfhergert.common.model.property;

import de.ralfhergert.common.model.TestContext;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * This test ensures that {@link NonNullPropertyHolder} is working correctly.
 */
public class NonNullPropertyHolderTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testTryingToInitializeWithNullValueIsRejected() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("initValue must not be null");

		new NonNullPropertyHolder<Integer>("foo", null);
	}

	@Test
	public void testTryingToSetNullValueIsRejected() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("value must not be null");

		new NonNullPropertyHolder<>("foo", 0).setValue(null, new TestContext());
	}
}
