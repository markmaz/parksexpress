package com.parksexpress.as400.util;

public final class Spacing {
	public static final int FAMILY = 5;
	public static final int ITEM = 10;
	public static final int CUSTOMER = 10;
	public static final int ORDER_GUIDE = 10;
	public static final int HEADER = 5;
	public static final int CLASS = 3;
	public static final int CHAIN_CODE = 5;
	public static final int VENDOR = 10;
	public static final int INVOICE_NUMBER = 7;
	public static final int ORDER_NUMBER = 7;
	public static final int FAMILY_IN_HISTORY = 7;
	
	public Spacing(){}
	
	public static String setCorrectSpacing(String itemToSpace, int spacing) {
		final int length = itemToSpace.length();
		final int spaces = spacing - length;

		String returnVal = "";

		for (int i = 0; i < spaces; i++) {
			returnVal += " ";
		}

		returnVal += itemToSpace;

		return returnVal;
	}
}
