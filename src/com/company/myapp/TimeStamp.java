package com.company.myapp;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class TimeStamp {
	
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("DD-MMM-YYYY, HH:MM");
	
	public static String stamp() {
		return dtf.format(LocalDateTime.now());
	}
	
}
