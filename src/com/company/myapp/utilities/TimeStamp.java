package com.company.myapp.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp {
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("dd-LLL-yyyy hh:mm:ss a");
	
	public static String stamp() {
		Date date = new Date(System.currentTimeMillis());
		return formatter.format(date);
	}
	
}
