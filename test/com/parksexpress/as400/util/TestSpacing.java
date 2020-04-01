package com.parksexpress.as400.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestSpacing {

	@Test
	public void testSetCorrectSpacing() {
		String itemToSpace = "9000";
		String spacedItem = Spacing.setCorrectSpacing(itemToSpace, 3);
		
		assertEquals("   9000", spacedItem);
	}

}
