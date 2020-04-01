package com.parksexpress.as400.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;

import org.junit.Test;

public class TestDateChanger {

	@Test
	public void testConvertDateToAS400() {		
		try {
			assertEquals("20090601", DateChanger.convertDateToAS400("06/01/2009"));
		} catch (ParseException e) {
			fail();
		}
	}

}
