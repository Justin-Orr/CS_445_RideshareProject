package com.company.myapp;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.json.JSONObject;

import com.company.myapp.entities.*;
import com.company.myapp.enums.*;
import com.company.myapp.interfaces.*;
import com.company.myapp.repositories.*;
import com.company.myapp.utilities.*;

@Path("/sar")
public class REST_Controller {
	
	AccountRepositoryInterface account_repo = new AccountRepository();
	RideRepositoryInterface ride_repo = new RideRepository();
	RatingRepositoryInterface rate_repo = new RatingRepository();
	
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
			int id = account_repo.createAccount(first_name, last_name, phone_number, picture);
			
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
			output = account_repo.viewAllAccounts();
		else if(key.compareTo("") == 0)
			output = account_repo.viewAllAccounts();
		else
			output = account_repo.searchAccounts(key);
		
		return Response.status(Response.Status.OK).entity(output).build();
	}
	
	@Path("/accounts/{aid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response viewAccount(@PathParam("aid") String id) {
		
		String output = "";
		Account a = account_repo.getAccount(Integer.valueOf(id));
		
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
				Account a = account_repo.getAccount(Integer.valueOf(id));
				
				if(a == null) {
					output = JSONValidator.validationErrorResponse("Account not found", "/accounts/" + id + "/status", 404).toString();
					return Response.status(Response.Status.NOT_FOUND).entity(output).build();
				}
				
				account_repo.updateAccount(a, jsonObject);		 
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
			
				Account a = account_repo.getAccount(Integer.valueOf(id));
				
				if(a == null) {
					output = JSONValidator.validationErrorResponse("Account not found", "/accounts/" + id + "/status", 404).toString();
					return Response.status(Response.Status.NOT_FOUND).entity(output).build();
				}
				
				account_repo.activateAccount(Integer.valueOf(id));
				return Response.ok().build();
			}
		}
		
	}
	
	/*@Path("/accounts/{aid}/ratings")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response rateAccount(@Context UriInfo uriInfo, String json) {
		String output;
		String error_message;
		
		
		//Check if the input was a proper json at all
		JSONValidatorCode error_code = JSONValidator.validJson(json);
		if(error_code == JSONValidatorCode.INVALID_JSON) {
			error_message = JSONValidator.generateErrorMessage(error_code);
			output = JSONValidator.validationErrorResponse(error_message, uriInfo.getPath(), 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		
		//Check if json is a valid rating json.
		JSONObject jsonObject = new JSONObject(json);
		error_code = JSONValidator.validRatingJson(jsonObject);
		
		if(error_code != JSONValidatorCode.VALID) {
			error_message = JSONValidator.generateErrorMessage(error_code);
			output = JSONValidator.validationErrorResponse(error_message, uriInfo.getPath(), 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else {
			int rid = jsonObject.getInt("rid");
			int sent_by_id = jsonObject.getInt("sent_by_id");
			int rating = jsonObject.getInt("rating");
			String comment = jsonObject.getString("comment");
			
			//Check if ride exists
			Ride r = ride_repo.getRide(rid);
			if(r == null) {
				output = JSONValidator.validationErrorResponse("Ride does not exist", "/rides", 404).toString();
				return Response.status(Response.Status.NOT_FOUND).entity(output).build();
			}
			
			//Check if accounts exist
			Account a = account_repo.getAccount(sent_by_id);
			Account b = account_repo.getAccount(r.getDriverID());
			if(a == null || b == null) {
				output = JSONValidator.validationErrorResponse("One of the accounts listed do not exist", "/rides", 404).toString();
				return Response.status(Response.Status.NOT_FOUND).entity(output).build();
			}
			
			//Check if sender was apart of the ride
			//FIX ME
			
			//Create rating
			int id = rate_repo.rateAccount(rid, sent_by_id, rating, comment);
			
			//Create response json
			JSONObject obj = new JSONObject();
			obj.put("sid", id);
			output = obj.toString();
			 
			// Build the URI for the "Location:" header
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			builder.path(Integer.toString(id));
			
			return Response.status(Response.Status.CREATED).entity(output).build();
		}
		
	}*/
	
	/*@Path("/accounts/{aid}/ratings/driver")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response viewDriverRatings(@Context UriInfo uriInfo) {
		
	}
	
	@Path("/accounts/{aid}/ratings/rider")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response viewRiderRatings(@Context UriInfo uriInfo) {
		
	}*/
	
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
			//Check if account listed exists
			int driverID = jsonObject.getInt("aid");
			
			Account a = account_repo.getAccount(driverID);
			if(a == null) {
				output = JSONValidator.validationErrorResponse("Account not found", "/rides", 404).toString();
				return Response.status(Response.Status.NOT_FOUND).entity(output).build();
			}
			
			//Create Ride
			int maxNumberOfPassengers = jsonObject.getInt("max_passengers");
			double ammountPerPerson = jsonObject.getDouble("amount_per_passenger");
			String conditions = jsonObject.getString("conditions");
			
			JSONObject loc = jsonObject.getJSONObject("location_info");
			Location location = new Location(loc.getString("from_city"), loc.getString("from_zip"), loc.getString("to_city"),loc.getString("to_zip"));
			
			JSONObject dat = jsonObject.getJSONObject("date_time");
			DateTime dateTime = new DateTime(dat.getString("date"), dat.getString("time"));
			
			JSONObject veh = jsonObject.getJSONObject("car_info");
			Vehicle vehicle = new Vehicle(veh.getString("make"), veh.getString("model"), veh.getString("color"), veh.getString("plate_state"), veh.getString("plate_serial"));
			int id = ride_repo.createRide(driverID, maxNumberOfPassengers, ammountPerPerson, location, dateTime, vehicle, conditions);
			
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
	
	/*@Path("/rides/{rid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response viewRide(@PathParam("rid") String id) {
		String output = "";
		Ride r = ride_repo.getRide(Integer.valueOf(id));
		
		if(r == null) {
			output = JSONValidator.validationErrorResponse("Ride not found", "/rides/" + id, 404).toString();
			return Response.status(Response.Status.NOT_FOUND).entity(output).build();
		}
		
		//Grab associated account for details
		Account a = account_repo.getAccount(r.getDriverID());
		
		if(a == null) {
			output = JSONValidator.validationErrorResponse("Driver account identified by ride does not exist", "/rides/" + id, 404).toString();
			return Response.status(Response.Status.NOT_FOUND).entity(output).build();
		}
		
		//Grab associated account ratings for details
		FIX ME, need driver comments from ratings repo
		
	}*/
	
	@Path("/rides")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchRides(@Context UriInfo uriInfo) {
		//Grab the query parameters if present
		MultivaluedMap<String, String> params = uriInfo.getQueryParameters(); 
		String from = params.getFirst("from");
		String to = params.getFirst("to");
		String date = params.getFirst("date");
		String output;
		
		//Check the format of date
		if(DataPatternFormatter.validateDate2(date) == -1) {
			output = JSONValidator.validationErrorResponse("Invalid date", "/rides", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		
		if(from == null && to == null && date == null) {
			output = ride_repo.viewAllRides();
		}
		else if(from.compareTo("") == 0 || to.compareTo("") == 0 || date.compareTo("") == 0) {
			output = ride_repo.searchRides(from, to, date);
		}
		else if(from != null && to != null && date != null) {
			output = ride_repo.searchRides(from, to, date);
		}
		else {
			output = JSONValidator.validationErrorResponse("Invalid query: missing at least one attribute", "/rides", 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		
		return Response.status(Response.Status.OK).entity(output).build();
	}
	
	@Path("/rides/{rid}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateRide(@PathParam("rid") String rid, String json) {
		String output;
		String error_message;
		Ride r = ride_repo.getRide(Integer.parseInt(rid));
		if(r == null) {
			output = JSONValidator.validationErrorResponse("Ride not found", "/rides/" + rid, 404).toString();
			return Response.status(Response.Status.NOT_FOUND).entity(output).build();
		}
		
		//Check if the input was a proper json at all
		JSONValidatorCode error_code = JSONValidator.validJson(json);
		
		//Check if the input was a proper json at all
		if(error_code == JSONValidatorCode.INVALID_JSON) {
			error_message = JSONValidator.generateErrorMessage(error_code);
			output = JSONValidator.validationErrorResponse(error_message, "/rides/" + rid, 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		
		//Check if json is a valid ride json.
		JSONObject jsonObject = new JSONObject(json);
		error_code = JSONValidator.validRideJson(jsonObject);
		
		if(error_code != JSONValidatorCode.VALID) {
			error_message = JSONValidator.generateErrorMessage(error_code);
			output = JSONValidator.validationErrorResponse(error_message, "/rides/" + rid, 400).toString();
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else {
			//Check if account listed exists
			int aid = jsonObject.getInt("aid");
			
			Account a = account_repo.getAccount(aid);
			if(a == null) {
				output = JSONValidator.validationErrorResponse("Account not found", "/rides/" + rid, 404).toString();
				return Response.status(Response.Status.NOT_FOUND).entity(output).build();
			}
			
			//Verify account is the driver
			if(r.getDriverID() != aid) {
				output = JSONValidator.validationErrorResponse("Only the creator of the ride may change it", "/rides/" + rid, 400).toString();
				return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
			}
			
			//Update Ride
			ride_repo.updateRide(r, jsonObject);
			return Response.noContent().build(); 
		}
	}
	
	@Path("/rides/{rid}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRide(@PathParam("rid") String rid) {
		String output;
		Ride r = ride_repo.getRide(Integer.parseInt(rid));
		if(r == null) {
			output = JSONValidator.validationErrorResponse("Ride not found", "/rides/" + rid, 404).toString();
			return Response.status(Response.Status.NOT_FOUND).entity(output).build();
		}
		ride_repo.deleteRide(Integer.parseInt(rid));
		return Response.noContent().build();
	}
	
	/*@Path("/rides/{rid}/join_requests")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response requestToJoinRide(@Context UriInfo uriInfo, String json) {
		
	}
	
	@Path("/rides/{rid}/join_requests/{jid}")
	@PATCH
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response replyToRideRequest(@Context UriInfo uriInfo, String json) {
		//Used to reply to requests and confirm pickup
	}
	
	@Path("/rides/{rid}/messages")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMessageToRide(@Context UriInfo uriInfo, String json) {
		
	}
	
	@Path("/rides/{rid}/messages")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMessageToRide(@Context UriInfo uriInfo) {
		
	}*/
	
}
