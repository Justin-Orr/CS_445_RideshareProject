package com.company.myapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataPatternFormatter {
	
	private static final String PHONE_NUMBER_REGEX = "^(1\\-)?[0-9]{3}\\-?[0-9]{3}\\-?[0-9]{4}$";
	private static final String DATE_REGEX = "^(([0-9])|([0-2][0-9])|([3][0-1]))\\-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\-\\d{4}$";
	private static final String TIME_REGEX = "^([0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
	private static final String PLATE_STATE_REGEX = "^(?i:A[LKSZRAEP]|C[AOT]|D[EC]|F[LM]|G[AU]|HI|I[ADLN]|K[SY]|LA|M[ADEHINOPST]|N[CDEHJMVY]|O[HKR]|P[ARW]|RI|S[CD]|T[NX]|UT|V[AIT]|W[AIVY])$";
	private static final String FIRST_NAME_REGEX = "[A-Z][a-z]*";
	private static final String LAST_NAME_REGEX = "[A-Z][a-z]*";
	
	private static int validateData(String data, final String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(data);
		boolean matched = m.matches();
		if(matched)
			return 0;
		else
			return -1;
	}
	
	public static int validatePhoneNumber(String phone) {
		return validateData(phone, PHONE_NUMBER_REGEX);
	}
	
	public static int validateDate(String date) {
		return validateData(date, DATE_REGEX);
	}
	
	public static int validateTime(String time) {
		return validateData(time, TIME_REGEX);
	}
	
	public static int validatePlateState(String plate_state) {
		return validateData(plate_state, PLATE_STATE_REGEX);
	}
	
	public static int validateFirstName(String name) {
		return validateData(name, FIRST_NAME_REGEX); 
	}
	
	public static int validateLastName(String name) {
		return validateData(name, LAST_NAME_REGEX); 
	}

}
