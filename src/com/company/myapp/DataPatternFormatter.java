package com.company.myapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataPatternFormatter {
	
	private static final String PHONE_NUMBER_REGEX = "^(1\\-)?[0-9]{3}\\-?[0-9]{3}\\-?[0-9]{4}$";
	private static final String DATE_REGEX = "^(([0-9])|([0-2][0-9])|([3][0-1]))\\-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\-\\d{4}$";
	private static final String TIME_REGEX = "^([0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
	private static final String PLATE_STATE_REGEX = "^(?i:A[LKSZRAEP]|C[AOT]|D[EC]|F[LM]|G[AU]|HI|I[ADLN]|K[SY]|LA|M[ADEHINOPST]|N[CDEHJMVY]|O[HKR]|P[ARW]|RI|S[CD]|T[NX]|UT|V[AIT]|W[AIVY])$";
	
	public static int validatePhoneNumber(String phone) {
		Pattern p = Pattern.compile(PHONE_NUMBER_REGEX);
		Matcher m = p.matcher(phone);
		boolean matched = m.matches();
		if(matched)
			return 0;
		else
			return -1;
	}
	
	public static int validateDate(String date) {
		Pattern p = Pattern.compile(DATE_REGEX);
		Matcher m = p.matcher(date);
		boolean matched = m.matches();
		if(matched)
			return 0;
		else
			return -1;
	}
	
	public static int validateTime(String time) {
		Pattern p = Pattern.compile(TIME_REGEX);
		Matcher m = p.matcher(time);
		boolean matched = m.matches();
		if(matched)
			return 0;
		else
			return -1;
	}
	
	public static int validatePlateState(String plate_state) {
		Pattern p = Pattern.compile(PLATE_STATE_REGEX);
		Matcher m = p.matcher(plate_state);
		boolean matched = m.matches();
		if(matched)
			return 0;
		else
			return -1;
	}

}
