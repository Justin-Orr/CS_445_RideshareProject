package com.company.myapp;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.json.*;
import javax.json.stream.JsonGenerator;

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
		
		JSONObject jsonObject = new JSONObject(json);
		
		int error_code = validAccountJson(jsonObject);
		String output;
		
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
		JSONObject jsonObject = new JSONObject(json);
		
		//Check if all values for an account are there and have a value.
		int error_code = validAccountJson(jsonObject);
		
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
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response activateAccount(@PathParam("aid") String id, String json) {
		String output;
		JSONObject jsonObject = new JSONObject(json);
		
		//Check if all values for an account are there and have a value.
		int error_code = validAccountJson(jsonObject);
		
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
	
	
	//Checks the incoming json to see if is valid for an account.
	public int validAccountJson(JSONObject json) {
	
		@SuppressWarnings("unused")
		int error_code;
		
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
			boolean is_active = json.getBoolean("is_active");
		}
		catch(NullPointerException | JSONException e) {
			return error_code = 5;
		}
	
		return error_code = 0;
	}
	
}
