package com.parksexpress.as400.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateChanger {
	public static final String MONTH_DAY_YEAR = "MM/dd/yyyy";
	public static final String YEAR_MONTH_DAY = "yyyyMMdd";

	public DateChanger(){}
	public static String convertDateToAS400(String dateMMDDYYYY) throws ParseException{
		final SimpleDateFormat sdf1 = new SimpleDateFormat(DateChanger.MONTH_DAY_YEAR);
		final SimpleDateFormat sdf2 = new SimpleDateFormat(DateChanger.YEAR_MONTH_DAY);
		
		return sdf2.format(sdf1.parse(dateMMDDYYYY));
	}
	
	public static String convertDateFromAS400(String dateyyyyMMdd) throws ParseException{
		final SimpleDateFormat sdf1 = new SimpleDateFormat(DateChanger.MONTH_DAY_YEAR);
		final SimpleDateFormat sdf2 = new SimpleDateFormat(DateChanger.YEAR_MONTH_DAY);
		
		return sdf1.format(sdf2.parse(dateyyyyMMdd));		
	}
}
