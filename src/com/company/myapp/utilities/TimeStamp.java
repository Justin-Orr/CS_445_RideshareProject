package com.company.myapp.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp {
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("dd-LLL-yyyy hh:mm:ss a");
	private static SimpleDateFormat formatter2 = new SimpleDateFormat("dd-LLL-yyyy");
	
	public static String stamp() {
		Date date = new Date(System.currentTimeMillis());
		return formatter.format(date);
	}
	
	public static String stampDate() {
		Date date = new Date(System.currentTimeMillis());
		return formatter2.format(date);
	}
	
}
