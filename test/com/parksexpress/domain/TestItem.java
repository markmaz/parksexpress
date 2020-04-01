package com.parksexpress.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import com.parksexpress.domain.item.Item;

public class TestItem {

	@Test
	public void testSixDigitCheckDigitItemNumber() {
		Item item = new Item();
		item.setCheckDigit("1");
		item.setNumber("123456");
		
		assertEquals("01234561", item.getCheckDigitItemNumber());
	}
	
	public void testSevenDigitCheckDigitItemNumber() {
		Item item = new Item();
		item.setCheckDigit("1");
		item.setNumber("1234567");
		
		assertEquals("12345671", item.getCheckDigitItemNumber());
	}	

}
