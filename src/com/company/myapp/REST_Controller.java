package com.company.myapp;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.json.JSONObject;

@Path("/sar")
public class REST_Controller {
	
	AccountRepositoryInterface ari = new AccountRepository();
	RideRepositoryInterface rri = new RideRepository();
	
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
		String error_message;
		JSONValidatorCode error_code = JSONValidator.validJson(json);
		
		//Check if the input was a proper json at all
		if(error_code == JSONValidatorCode.INVALID_JSON) {
			error_message = JSONValidator.generateErrorMessage(error_code);
			output = JSONValidator.validationErrorResponse(error_message, "/accounts", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		
		//Check if the json has an account format
		JSONObject jsonObject = new JSONObject(json);
		error_code = JSONValidator.validAccountJson(jsonObject);
		
		if(error_code != JSONValidatorCode.VALID) {
			error_message = JSONValidator.generateErrorMessage(error_code);
			output = JSONValidator.validationErrorResponse(error_message, "/accounts", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else {
			//Create an account
			String first_name = jsonObject.getString("first_name"); 
			String last_name = jsonObject.getString("last_name"); 
			String phone_number = jsonObject.getString("phone"); 
			String picture = jsonObject.getString("picture");
			int id = ari.createAccount(first_name, last_name, phone_number, picture);
			
			//Create response json
			JSONObject obj = new JSONObject();
			obj.put("aid", id);
			output = obj.toString();
			 
			// Build the URI for the "Location:" header
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			builder.path(Integer.toString(id));
			
			return Response.status(Response.Status.CREATED).entity(output).build();
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
			output = ari.viewAllAccounts();
		else if(key.compareTo("") == 0)
			output = ari.viewAllAccounts();
		else
			output = ari.searchAccounts(key);
		
		return Response.status(Response.Status.OK).entity(output).build();
	}
	
	@Path("/accounts/{aid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response viewAccount(@PathParam("aid") String id) {
		
		String output = "";
		Account a = ari.getAccount(Integer.valueOf(id));
		
		if(a == null) {
			output = JSONValidator.validationErrorResponse("Account not found", "/accounts/" + id, 404).toString();
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
		String error_message;
		JSONValidatorCode error_code = JSONValidator.validJson(json);
		
		//Check if the input was a proper json at all
		if(error_code == JSONValidatorCode.INVALID_JSON) {
			error_message = JSONValidator.generateErrorMessage(error_code);
			output = JSONValidator.validationErrorResponse(error_message, "/accounts/" + id, 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		
		//Check if all values for an account are there and have a value.
		JSONObject jsonObject = new JSONObject(json);
		error_code = JSONValidator.validAccountJson(jsonObject);
		
		//Was it a valid account object?
		if(error_code != JSONValidatorCode.VALID) {
			error_message = JSONValidator.generateErrorMessage(error_code);
			output = JSONValidator.validationErrorResponse(error_message, "/accounts/" + id, 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else {
			
			//Check for the appropriate is_active value
			if(jsonObject.getBoolean("is_active") == true) 
				error_code = JSONValidatorCode.INVALID_ACTIVE_VALUE;
			
			if(error_code == JSONValidatorCode.INVALID_ACTIVE_VALUE) {
				error_message = JSONValidator.generateErrorMessage(error_code);
				output = JSONValidator.validationErrorResponse(error_message, "/accounts/" + id, 400).toString();
				return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
			}
			else {
				//Update account information
				Account a = ari.getAccount(Integer.valueOf(id));
				
				if(a == null) {
					output = JSONValidator.validationErrorResponse("Account not found", "/accounts/" + id + "/status", 404).toString();
					return Response.status(Response.Status.NOT_FOUND).entity(output).build();
				}
				
				ari.updateAccount(a, jsonObject);		 
				return Response.noContent().build();
			}
		}
		
	}
	
	@Path("/accounts/{aid}/status")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response activateAccount(@PathParam("aid") String id, String json) {
		String output;
		String error_message;
		JSONValidatorCode error_code = JSONValidator.validJson(json);
		
		//Check if the input was a proper json at all
		if(error_code == JSONValidatorCode.INVALID_JSON) {
			error_message = JSONValidator.generateErrorMessage(error_code);
			output = JSONValidator.validationErrorResponse(error_message, "/accounts/" + id + "/status", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		
		//Check if all values for an account are there and have a value.
		JSONObject jsonObject = new JSONObject(json);
		error_code = JSONValidator.validAccountJson(jsonObject);
		
		//Activate account information
		if(error_code != JSONValidatorCode.VALID) {
			error_message = JSONValidator.generateErrorMessage(error_code);
			output = JSONValidator.validationErrorResponse(error_message, "/accounts/" + id + "/status", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else {
			//Check for the appropriate is_active value, should be true.
			if(jsonObject.getBoolean("is_active") == false) 
				error_code = JSONValidatorCode.INVALID_ACTIVE_VALUE;
			
			if(error_code == JSONValidatorCode.INVALID_ACTIVE_VALUE) {
				error_message = JSONValidator.generateErrorMessage(error_code);
				output = JSONValidator.validationErrorResponse(error_message, "/accounts/" + id + "/status", 400).toString();
				return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
			}
			else {
			
				Account a = ari.getAccount(Integer.valueOf(id));
				
				if(a == null) {
					output = JSONValidator.validationErrorResponse("Account not found", "/accounts/" + id + "/status", 404).toString();
					return Response.status(Response.Status.NOT_FOUND).entity(output).build();
				}
				
				ari.activateAccount(Integer.valueOf(id));
				return Response.ok().build();
			}
		}
		
	}
	
	@Path("/rides")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createRide(@Context UriInfo uriInfo, String json) {
		String output;
		String error_message;
		//Check if the input was a proper json at all
		JSONValidatorCode error_code = JSONValidator.validJson(json);
		
		//Check if the input was a proper json at all
		if(error_code == JSONValidatorCode.INVALID_JSON) {
			error_message = JSONValidator.generateErrorMessage(error_code);
			output = JSONValidator.validationErrorResponse(error_message, "/rides", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		
		//Check if json is a valid ride json.
		JSONObject jsonObject = new JSONObject(json);
		error_code = JSONValidator.validRideJson(jsonObject);
		
		if(error_code != JSONValidatorCode.VALID) {
			error_message = JSONValidator.generateErrorMessage(error_code);
			output = JSONValidator.validationErrorResponse(error_message, "/rides", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else {
			//Create ride object
			int driverID = jsonObject.getInt("aid");
			int maxNumberOfPassengers = jsonObject.getInt("max_passengers");
			double ammountPerPerson = jsonObject.getDouble("amount_per_passenger");
			String conditions = jsonObject.getString("conditions");
			
			JSONObject loc = jsonObject.getJSONObject("location_info");
			Location location = new Location(loc.getString("from_city"), loc.getString("from_zip"), loc.getString("to_city"),loc.getString("to_zip"));
			
			JSONObject dat = jsonObject.getJSONObject("date_time");
			DateTime dateTime = new DateTime(dat.getString("date"), dat.getString("time"));
			
			JSONObject veh = jsonObject.getJSONObject("car_info");
			Vehicle vehicle = new Vehicle(veh.getString("make"), veh.getString("model"), veh.getString("color"), veh.getString("plate_state"), veh.getString("plate_serial"));
			int id = rri.createRide(driverID, maxNumberOfPassengers, ammountPerPerson, location, dateTime, vehicle, conditions);
			
			//Create response json
			JSONObject obj = new JSONObject();
			obj.put("rid", id);
			output = obj.toString();
			 
			// Build the URI for the "Location:" header
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			builder.path(Integer.toString(id));
			
			return Response.status(Response.Status.CREATED).entity(output).build();
		}
	
	}	
	
}
