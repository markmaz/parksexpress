package com.parksexpress.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
	public static String getDefaultStartDate(int months){
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		final Calendar cal = Calendar.getInstance();
		
		if(months == 0){
			return sdf.format(cal.getTime());
		}else{
			cal.add(Calendar.MONTH, (months * -1));
			return sdf.format(cal.getTime());
		}
	}
	
	public static String convertMySQLDate(String date) throws ParseException{
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		final SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
		
		return sdf2.format(sdf.parse(date));
	}
	
	public static String convertToMySQLDate(String mmddyyyy) throws ParseException{
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		
		return sdf2.format(sdf.parse(mmddyyyy));
	}
	
	public static String getDefaultEndDate(int months){
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		final Calendar cal = Calendar.getInstance();
		
		if(months == 0){
			return sdf.format(cal.getTime());
		}else{
			cal.add(Calendar.MONTH, months);
			return sdf.format(cal.getTime());
		}		
	}
	
	public static String today(){
		final Calendar calendar = Calendar.getInstance();
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		
		return sdf.format(calendar.getTime());
	}
	
	public static String tomorrow(){
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		
		return sdf.format(calendar.getTime());
	}
	
	public static String getMonthStart(){
		final Calendar calendar = Calendar.getInstance();
		final SimpleDateFormat month = new SimpleDateFormat("MM");
		final SimpleDateFormat year = new SimpleDateFormat("yyyy");
		
		return month.format(calendar.getTime()) + "/01/" + year.format(calendar.getTime());
	}
	
	public static String getYearStart(){
		final Calendar calendar = Calendar.getInstance();
		final SimpleDateFormat year = new SimpleDateFormat("yyyy");
		
		return "01/01/" + year.format(calendar.getTime());		
	}
}
