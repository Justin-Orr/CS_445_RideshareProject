package com.company.myapp;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/sar")
public class REST_Controller {
	
	AccountRepository abi = new AccountRepository();
	
	@GET
    @Produces("text/plain")
    public Response test() {
        // Test if the server is working properly
        String s = "Working properly";
        return Response.status(Response.Status.OK).entity(s).build();
    }
	
	@Path("/accounts")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAccount(@Context UriInfo uriInfo, String json) {
		String output;
		int error_code = validJson(json);
		
		//Check if the input was a proper json at all
		if(error_code == -1) {
			output = validationErrorResponse("JSON syntax error", "/accounts", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		
		JSONObject jsonObject = new JSONObject(json);
		
		error_code = validAccountJson(jsonObject);
		
		if(error_code == 0) {
			String first_name = jsonObject.getString("first_name"); 
			String last_name = jsonObject.getString("last_name"); 
			String phone_number = jsonObject.getString("phone"); 
			String picture = jsonObject.getString("picture");
			int id = abi.creatAccount(first_name, last_name, phone_number, picture);
			
			//Create response json
			JSONObject obj = new JSONObject();
			obj.put("aid", id);
			output = obj.toString();
			 
			// Build the URI for the "Location:" header
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			builder.path(Integer.toString(id));
			
			return Response.status(Response.Status.CREATED).entity(output).build();
		}
		else if(error_code == 1) {
			output = validationErrorResponse("Invalid first name", "/accounts", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else if(error_code == 2) {
			output = validationErrorResponse("Invalid last name", "/accounts", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else if(error_code == 3) {
			output = validationErrorResponse("Invalid phone number", "/accounts", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else if(error_code == 4) {
			output = validationErrorResponse("Invalid picture URL/filetype", "/accounts", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else if(error_code == 5) {
			output = validationErrorResponse("Invalid value for is_active", "/accounts", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else {
			output = validationErrorResponse("Unhandled_error_code", "/accounts", 500).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
	
	}
	
	@Path("/accounts")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchAccounts(@Context UriInfo uriInfo) {
		//Grab the query parameters if present
		MultivaluedMap<String, String> params = uriInfo.getQueryParameters(); 
		String key = params.getFirst("key");
		String output;
		
		if(key == null)
			output = abi.viewAllAccounts();
		else if(key.compareTo("") == 0)
			output = abi.viewAllAccounts();
		else
			output = abi.searchAccounts(key);
		
		return Response.status(Response.Status.OK).entity(output).build();
	}
	
	@Path("/accounts/{aid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response viewAccount(@PathParam("aid") String id) {
		
		String output = "";
		Account a = abi.getAccount(Integer.valueOf(id));
		
		if(a == null) {
			output = validationErrorResponse("Account not found", "/accounts/" + id, 404).toString();
			return Response.status(Response.Status.NOT_FOUND).entity(output).build();
		}
		else {
			output = a.toPrettyJson().toString();
			return Response.status(Response.Status.OK).entity(output).build();
		}
	}
	
	@Path("/accounts/{aid}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAccount(@PathParam("aid") String id, String json) {
		String output;
		int error_code = validJson(json);
		
		//Check if the input was a proper json at all
		if(error_code == -1) {
			output = validationErrorResponse("JSON syntax error", "/accounts/" + id, 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		
		JSONObject jsonObject = new JSONObject(json);
		
		//Check if all values for an account are there and have a value.
		error_code = validAccountJson(jsonObject);
		
		if(jsonObject.getBoolean("is_active") == true) //Call activate account to make the account active
			error_code = 5;
		
		if(error_code == 0) {
			Account a = abi.getAccount(Integer.valueOf(id));
			
			if(a == null) {
				output = validationErrorResponse("Account not found", "/accounts/" + id + "/status", 404).toString();
				return Response.status(Response.Status.NOT_FOUND).entity(output).build();
			}
			
			abi.updateAccount(a, jsonObject);		 
			
			return Response.noContent().build();
		}
		else if(error_code == 1) {
			output = validationErrorResponse("Invalid first name", "/accounts", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else if(error_code == 2) {
			output = validationErrorResponse("Invalid last name", "/accounts", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else if(error_code == 3) {
			output = validationErrorResponse("Invalid phone number", "/accounts", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else if(error_code == 4) {
			output = validationErrorResponse("Invalid picture URL/filetype", "/accounts", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else if(error_code == 5) {
			output = validationErrorResponse("Invalid value for is_active", "/accounts", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else {
			output = validationErrorResponse("Unhandled_error_code", "/accounts", 500).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		
	}
	
	@Path("/accounts/{aid}/status")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response activateAccount(@PathParam("aid") String id, String json) {
		String output;
		int error_code = validJson(json);
		
		//Check if the input was a proper json at all
		if(error_code == -1) {
			output = validationErrorResponse("JSON syntax error", "/accounts/" + id + "/status", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		
		JSONObject jsonObject = new JSONObject(json);
		
		//Check if all values for an account are there and have a value.
		error_code = validAccountJson(jsonObject);
		
		if(jsonObject.getBoolean("is_active") == false) //Should be true
			error_code = 5;
		
		if(error_code == 0) {
			Account a = abi.getAccount(Integer.valueOf(id));
			
			if(a == null) {
				output = validationErrorResponse("Account not found", "/accounts/" + id + "/status", 404).toString();
				return Response.status(Response.Status.NOT_FOUND).entity(output).build();
			}
			
			abi.activateAccount(Integer.valueOf(id));		 
			
			return Response.ok().build();
		}
		else if(error_code == 1) {
			output = validationErrorResponse("Invalid first name", "/accounts", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else if(error_code == 2) {
			output = validationErrorResponse("Invalid last name", "/accounts", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else if(error_code == 3) {
			output = validationErrorResponse("Invalid phone number", "/accounts", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else if(error_code == 4) {
			output = validationErrorResponse("Invalid picture URL/filetype", "/accounts", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else if(error_code == 5) {
			output = validationErrorResponse("Invalid value for is_active", "/accounts", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else {
			output = validationErrorResponse("Unhandled_error_code", "/accounts", 500).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		
	}
	
	@Path("/rides")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createRide(@Context UriInfo uriInfo, String json) {
		String output;
		
		//Check if the input was a proper json at all
		int error_code = validJson(json);
		
		if(error_code == -1) {
			output = validationErrorResponse("JSON syntax error", "/rides", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		
		//Create new json object out of input
		JSONObject jsonObject = new JSONObject(json);
		
		//Check if json is a valid account json.
		error_code = validRideJson(jsonObject);
		
		/*if(error_code == 0) {
			String first_name = jsonObject.getString("first_name"); 
			String last_name = jsonObject.getString("last_name"); 
			String phone_number = jsonObject.getString("phone"); 
			String picture = jsonObject.getString("picture");
			int id = abi.creatAccount(first_name, last_name, phone_number, picture);
			
			//Create response json
			JSONObject obj = new JSONObject();
			obj.put("aid", id);
			output = obj.toString();
			 
			// Build the URI for the "Location:" header
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			builder.path(Integer.toString(id));
			
			return Response.status(Response.Status.CREATED).entity(output).build();
		}
		else if(error_code == 1) {
			output = validationErrorResponse("Invalid first name", "/rides", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else if(error_code == 2) {
			output = validationErrorResponse("Invalid last name", "/rides", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else if(error_code == 3) {
			output = validationErrorResponse("Invalid phone number", "/rides", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else if(error_code == 4) {
			output = validationErrorResponse("Invalid picture URL/filetype", "/rides", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else if(error_code == 5) {
			output = validationErrorResponse("Invalid value for is_active", "/rides", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else {
			output = validationErrorResponse("Unhandled_error_code", "/rides", 500).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}*/
	
	}
	
	//Error response generator for incorrect client input data.
	private JSONObject validationErrorResponse(String detail, String instance, int status) {
		JSONObject obj = new JSONObject();
		obj.put("type", "http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation");
		obj.put("title", "Your request data didn't pass validation");
		obj.put("detail", detail);
		obj.put("status", status);
		obj.put("instance", instance);
		return obj;
	}
	
	
	//Check if the incoming string is json at all
	public int validJson(String json) {
		try {
			@SuppressWarnings("unused")
			JSONObject obj = new JSONObject(json);
			return 0;
		}
		catch (JSONException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	//Checks the incoming json to see if is valid for an account.
	public int validAccountJson(JSONObject json) {
	
		@SuppressWarnings("unused")
		int error_code = 0;
		
		try {
			String first_name = json.getString("first_name");
			if(first_name.compareTo("") == 0 || DataPatternFormatter.validateFirstName(first_name) == -1)
				return error_code = 1;
		}
		catch(NullPointerException | JSONException e ) {
			return error_code = 1;
		}
		
		try {
			String last_name = json.getString("last_name"); 
			if(last_name.compareTo("") == 0 || DataPatternFormatter.validateLastName(last_name) == -1)
				return error_code = 2;
		}
		catch(NullPointerException | JSONException e) {
			return error_code = 2;
		}
		
		try {
			String phone_number = json.getString("phone"); 
			if(phone_number.compareTo("") == 0 || DataPatternFormatter.validatePhoneNumber(phone_number) == -1)
				return error_code = 3;
		}
		catch(NullPointerException | JSONException e) {
			return error_code = 3;
		}
		
		try {
			String picture = json.getString("picture");
			if(picture.compareTo("") == 0)
				return error_code = 4;
		}
		catch(NullPointerException | JSONException e) {
			return error_code = 4;
		}
		
		try {	
			@SuppressWarnings("unused")
			boolean is_active = json.getBoolean("is_active");
		}
		catch(NullPointerException | JSONException e) {
			return error_code = 5;
		}
	
		return error_code;
	}
	
	@SuppressWarnings("unused")
	//Checks the incoming json to see if is valid for an account.
	public int validRideJson(JSONObject json) {
	
		int error_code = 0;
		
		try {
			int aid = json.getInt("aid");
		}
		catch(NullPointerException | JSONException e ) {
			return error_code = 1;
		}
		
		try {
			int max_passengers = json.getInt("max_passengers"); 
		}
		catch(NullPointerException | JSONException e) {
			return error_code = 2;
		}
		
		try {
			double ammount_per_passenger = json.getDouble("ammount_per_passenger"); 
		}
		catch(NullPointerException | JSONException e) {
			return error_code = 3;
		}
		
		try {
			String conditions = json.getString("conditions");
		}
		catch(NullPointerException | JSONException e) {
			return error_code = 4;
		}
		
		JSONObject locationObject;
		try {
			locationObject = json.getJSONObject("location_info");
		}
		catch(NullPointerException | JSONException e) {
			return error_code = 5;
		}
		
		error_code = validLocationJson(locationObject);
		
		if(error_code != 0)
			return error_code;
		
		JSONObject dateTimeObject;
		try {
			dateTimeObject = json.getJSONObject("date_time");
		}
		catch(NullPointerException | JSONException e) {
			return error_code = 10;
		}
		
		error_code = validDateTimeJson(dateTimeObject);
		
		if(error_code != 0)
			return error_code;
		
		JSONObject vehicleObject;
		try {
			vehicleObject = json.getJSONObject("car_info");
		}
		catch(NullPointerException | JSONException e) {
			return error_code = 13;
		}
		
		error_code = validVehicleJson(vehicleObject);
	
		return error_code;
	}
	
	@SuppressWarnings("unused")
	//Checks the incoming json to see if is valid for an location.
	public int validLocationJson(JSONObject json) {
		
		int error_code = 0;
		
		try {
			String from_city = json.getString("from_city");
			if(from_city.compareTo("") == 0 || DataPatternFormatter.validateCity(from_city) == -1)
				return error_code = 6;
		}
		catch(NullPointerException | JSONException e ) {
			return error_code = 6;
		}
		
		try {
			//Optional Argument but if there is a value check the format
			String from_zip = json.getString("from_zip"); 
			if(from_zip.compareTo("") != 0) { 
				if(DataPatternFormatter.validateZipCode(from_zip) == -1)
					return error_code = 7;
			}
		}
		catch(NullPointerException | JSONException e) {
			return error_code = 7;
		}
		
		try {
			String to_city = json.getString("to_city"); 
			if(to_city.compareTo("") == 0 || DataPatternFormatter.validateCity(to_city) == -1)
				return error_code = 8;
		}
		catch(NullPointerException | JSONException e) {
			return error_code = 8;
		}
		
		try {
			//Optional Argument but if there is a value check the format
			String to_zip = json.getString("to_zip"); 
			if(to_zip.compareTo("") != 0) { 
				if(DataPatternFormatter.validateZipCode(to_zip) == -1)
					return error_code = 9;
			}
		}
		catch(NullPointerException | JSONException e) {
			return error_code = 9;
		}
	
		return error_code;
	}
		
	//Checks the incoming json to see if is valid for a date/time.
	public int validDateTimeJson(JSONObject json) {
	
		@SuppressWarnings("unused")
		int error_code = 0;
		
		try {
			String date = json.getString("date");
			if(date.compareTo("") == 0 || DataPatternFormatter.validateDate(date) == -1)
				return error_code = 11;
		}
		catch(NullPointerException | JSONException e ) {
			return error_code = 11;
		}
		
		try {
			String time = json.getString("time"); 
			if(time.compareTo("") == 0 || DataPatternFormatter.validateTime(time) == -1)
				return error_code = 12;
		}
		catch(NullPointerException | JSONException e) {
			return error_code = 12;
		}
	
		return error_code;
	}
	
	//Checks the incoming json to see if is valid for a vehicle.
	public int validVehicleJson(JSONObject json) {
	
		int error_code = 0;
		
		try {
			String make = json.getString("make");
			if(make.compareTo("") == 0)
				return error_code = 14;
		}
		catch(NullPointerException | JSONException e ) {
			return error_code = 14;
		}
		
		try {
			String model = json.getString("model"); 
			if(model.compareTo("") == 0)
				return error_code = 15;
		}
		catch(NullPointerException | JSONException e) {
			return error_code = 15;
		}
		
		try {
			String color = json.getString("color"); 
			if(color.compareTo("") == 0)
				return error_code = 16;
		}
		catch(NullPointerException | JSONException e) {
			return error_code = 16;
		}
		
		try {
			String plate_state = json.getString("plate_state");
			if(plate_state.compareTo("") == 0 || DataPatternFormatter.validatePlateState(plate_state) == -1)
				return error_code = 17;
		}
		catch(NullPointerException | JSONException e) {
			return error_code = 17;
		}
		
		try {	
			String plate_serial = json.getString("plate_serial");
			if(plate_serial.compareTo("") == 0)
					return error_code = 18;
		}
		catch(NullPointerException | JSONException e) {
			return error_code = 18;
		}
	
		return error_code;
	}
	
}
