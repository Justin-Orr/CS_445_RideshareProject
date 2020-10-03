package com.company.myapp.utilities;

import org.json.JSONException;
import org.json.JSONObject;

import com.company.myapp.enums.JSONValidatorCode;

public class JSONValidator {
	
	//Error response generator for incorrect client input data.
	public static JSONObject validationErrorResponse(String detail, String instance, int status) {
		JSONObject obj = new JSONObject();
		obj.put("type", "http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation");
		obj.put("title", "Your request data didn't pass validation");
		obj.put("detail", detail);
		obj.put("status", status);
		obj.put("instance", instance);
		return obj;
	}
	
	//Generate corresponding error messages based on the error code.
	public static String generateErrorMessage(JSONValidatorCode code) {
		switch(code) {
			case INVALID_JSON: return "Invalid JSON Object";
			case INVALID_NUMBER: return "Invalid Id";
			case INVALID_FIRST_NAME: return "Invalid first name";
			case INVALID_LAST_NAME: return "Invalid last name";
			case INVALID_PHONE_NUMBER: return "Invalid phone number";
			case INVALID_PICTURE: return "Invalid picture url/file";
			case INVALID_ACTIVE_VALUE: return "Invalid active value";
			case INVALID_FROM_CITY: return "Invalid from city value";
			case INVALID_FROM_ZIP: return "Invalid from zip value";
			case INVALID_TO_CITY: return "Invalid to city value";
			case INVALID_TO_ZIP: return "Invalid to xip value";
			case INVALID_DATE: return "Invalid date";
			case INVALID_TIME: return "Invalid time";
			case INVALID_MAKE: return "Invalid make";
			case INVALID_MODEL: return "Invalid model";
			case INVALID_COLOR: return "Invalid color";
			case INVALID_PLATE_STATE: return "Invalid plate state value";
			case INVALID_PLATE_SERIAL: return "Invalid plater serial number";
			case INVALID_MAX_PASSENGERS: return "Invalid number of passengers";
			case INVALID_AMMOUNT_PER_PERSON: return "Invalid amount per person";
			case INVALID_CONDITIONS: return "Invalid condition statement";
			case VALID: return "No error";
			default: return "Unknown error code for JSONValidator message generator";
		}
	}
	
	//Check if the incoming string is json at all
	public static JSONValidatorCode validJson(String json) {
		try {
			@SuppressWarnings("unused")
			JSONObject obj = new JSONObject(json);
			return JSONValidatorCode.VALID;
		}
		catch (JSONException e) {
			e.printStackTrace();
			return JSONValidatorCode.INVALID_JSON;
		}
	}
	
	//Checks the incoming json to see if it is valid for an account.
	@SuppressWarnings("unused")
	//Checks the incoming json to see if is valid for an account.
	public static JSONValidatorCode validAccountJson(JSONObject json) {
		
		JSONValidatorCode error_code = JSONValidatorCode.VALID;
		
		try {
			String first_name = json.getString("first_name");
			if(first_name.compareTo("") == 0 || DataPatternFormatter.validateFirstName(first_name) == -1)
				return error_code = JSONValidatorCode.INVALID_FIRST_NAME;
		}
		catch(NullPointerException | JSONException e ) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_FIRST_NAME;
		}
		
		try {
			String last_name = json.getString("last_name"); 
			if(last_name.compareTo("") == 0 || DataPatternFormatter.validateLastName(last_name) == -1)
				return error_code = JSONValidatorCode.INVALID_LAST_NAME;
		}
		catch(NullPointerException | JSONException e) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_LAST_NAME;
		}
		
		try {
			String phone_number = json.getString("phone"); 
			if(phone_number.compareTo("") == 0 || DataPatternFormatter.validatePhoneNumber(phone_number) == -1)
				return error_code = JSONValidatorCode.INVALID_PHONE_NUMBER;
		}
		catch(NullPointerException | JSONException e) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_PHONE_NUMBER;
		}
		
		try {
			String picture = json.getString("picture");
			if(picture.compareTo("") == 0)
				return error_code = JSONValidatorCode.INVALID_PICTURE;
		}
		catch(NullPointerException | JSONException e) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_PICTURE;
		}
		
		try {	
			boolean is_active = json.getBoolean("is_active");
		}
		catch(NullPointerException | JSONException e) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_ACTIVE_VALUE;
		}
	
		return error_code;
	}
	
	//Checks the incoming json to see if it is valid for a ride.
	@SuppressWarnings("unused")
	//Checks the incoming json to see if is valid for an account.
	public static JSONValidatorCode validRideJson(JSONObject json) {
	
		JSONValidatorCode error_code = JSONValidatorCode.VALID;
		
		try {
			int aid = json.getInt("aid");
		}
		catch(NullPointerException | JSONException e ) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_NUMBER;
		}
		
		try {
			int max_passengers = json.getInt("max_passengers"); 
		}
		catch(NullPointerException | JSONException e) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_MAX_PASSENGERS;
		}
		
		try {
			double ammount_per_passenger = json.getDouble("amount_per_passenger"); 
		}
		catch(NullPointerException | JSONException e) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_AMMOUNT_PER_PERSON;
		}
		
		try {
			String conditions = json.getString("conditions");
		}
		catch(NullPointerException | JSONException e) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_CONDITIONS;
		}
		
		JSONObject locationObject;
		try {
			locationObject = json.getJSONObject("location_info");
		}
		catch(NullPointerException | JSONException e) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_JSON;
		}
		
		error_code = validLocationJson(locationObject);
		
		if(error_code != JSONValidatorCode.VALID)
			return error_code;
		
		JSONObject dateTimeObject;
		try {
			dateTimeObject = json.getJSONObject("date_time");
		}
		catch(NullPointerException | JSONException e) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_JSON;
		}
		
		error_code = validDateTimeJson(dateTimeObject);
		
		if(error_code != JSONValidatorCode.VALID)
			return error_code;
		
		JSONObject vehicleObject;
		try {
			vehicleObject = json.getJSONObject("car_info");
		}
		catch(NullPointerException | JSONException e) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_JSON;
		}
		
		error_code = validVehicleJson(vehicleObject);
	
		return error_code;
	}
	
	//Checks the incoming json to see if it is valid for a location.
	public static JSONValidatorCode validLocationJson(JSONObject json) {
		
		JSONValidatorCode error_code = JSONValidatorCode.VALID;
		
		try {
			String from_city = json.getString("from_city");
			if(from_city.compareTo("") == 0 || DataPatternFormatter.validateCity(from_city) == -1)
				return error_code = JSONValidatorCode.INVALID_FROM_CITY;
		}
		catch(NullPointerException | JSONException e ) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_FROM_CITY;
		}
		
		try {
			//Optional Argument but if there is a value check the format
			String from_zip = json.getString("from_zip"); 
			if(from_zip.compareTo("") != 0) { 
				if(DataPatternFormatter.validateZipCode(from_zip) == -1)
					return error_code = JSONValidatorCode.INVALID_FROM_ZIP;
			}
		}
		catch(NullPointerException | JSONException e) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_FROM_ZIP;
		}
		
		try {
			String to_city = json.getString("to_city"); 
			if(to_city.compareTo("") == 0 || DataPatternFormatter.validateCity(to_city) == -1)
				return error_code = JSONValidatorCode.INVALID_TO_CITY;
		}
		catch(NullPointerException | JSONException e) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_TO_CITY;
		}
		
		try {
			//Optional Argument but if there is a value check the format
			String to_zip = json.getString("to_zip"); 
			if(to_zip.compareTo("") != 0) { 
				if(DataPatternFormatter.validateZipCode(to_zip) == -1)
					return error_code = JSONValidatorCode.INVALID_TO_ZIP;
			}
		}
		catch(NullPointerException | JSONException e) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_TO_ZIP;
		}
	
		return error_code;
	}
		
	//Checks the incoming json to see if it is valid for a date/time.
	public static JSONValidatorCode validDateTimeJson(JSONObject json) {
	
		JSONValidatorCode error_code = JSONValidatorCode.VALID;
		
		try {
			String date = json.getString("date");
			if(date.compareTo("") == 0 || DataPatternFormatter.validateDate(date) == -1)
				return error_code = JSONValidatorCode.INVALID_DATE;
		}
		catch(NullPointerException | JSONException e ) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_DATE;
		}
		
		try {
			String time = json.getString("time"); 
			if(time.compareTo("") == 0 || DataPatternFormatter.validateTime(time) == -1)
				return error_code = JSONValidatorCode.INVALID_TIME;
		}
		catch(NullPointerException | JSONException e) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_TIME;
		}
	
		return error_code;
	}
	
	//Checks the incoming json to see if it is valid for a vehicle.
	public static JSONValidatorCode validVehicleJson(JSONObject json) {
	
		JSONValidatorCode error_code = JSONValidatorCode.VALID;
		
		try {
			String make = json.getString("make");
			if(make.compareTo("") == 0)
				return error_code = JSONValidatorCode.INVALID_MAKE;
		}
		catch(NullPointerException | JSONException e ) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_MAKE;
		}
		
		try {
			String model = json.getString("model"); 
			if(model.compareTo("") == 0)
				return error_code = JSONValidatorCode.INVALID_MODEL;
		}
		catch(NullPointerException | JSONException e) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_MODEL;
		}
		
		try {
			String color = json.getString("color"); 
			if(color.compareTo("") == 0)
				return error_code = JSONValidatorCode.INVALID_COLOR;
		}
		catch(NullPointerException | JSONException e) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_COLOR;
		}
		
		try {
			String plate_state = json.getString("plate_state");
			if(plate_state.compareTo("") == 0 || DataPatternFormatter.validatePlateState(plate_state) == -1)
				return error_code = JSONValidatorCode.INVALID_PLATE_STATE;
		}
		catch(NullPointerException | JSONException e) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_PLATE_STATE;
		}
		
		try {	
			String plate_serial = json.getString("plate_serial");
			if(plate_serial.compareTo("") == 0)
					return error_code = JSONValidatorCode.INVALID_PLATE_SERIAL;
		}
		catch(NullPointerException | JSONException e) {
			e.printStackTrace();
			return error_code = JSONValidatorCode.INVALID_PLATE_SERIAL;
		}
	
		return error_code;
	}

}
