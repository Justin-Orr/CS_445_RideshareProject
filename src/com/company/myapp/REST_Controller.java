package com.company.myapp;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.json.*;
import javax.json.stream.JsonGenerator;

@Path("/sar")
public class REST_Controller {
	
	@GET
    @Produces("text/plain")
    public Response test() {
        // Test if the server is working properly
        String s = "Working properly";
        return Response.status(Response.Status.OK).entity(s).build();
    }
	
	private JsonObject stringToJsonObject(String json) {
		JsonReader jsonReader = Json.createReader(new StringReader(json));
		JsonObject jsonObject = jsonReader.readObject();
		jsonReader.close();
		return jsonObject;
	}
	
	private String jsonToString(JsonObject jsonObject) {
		Map<String, Boolean> config = new HashMap<>();
		 
		config.put(JsonGenerator.PRETTY_PRINTING, true);
		        
		JsonWriterFactory writerFactory = Json.createWriterFactory(config);
		        
		String jsonString = "";
		 
		try(Writer writer = new StringWriter()) {
		    writerFactory.createWriter(writer).write(jsonObject);
		    jsonString = writer.toString();
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return jsonString;
	}
	
	private String validationErrorResponse(String detail, String instance) {
		JsonObjectBuilder obj = Json.createObjectBuilder();
		obj.add("type", "http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation");
		obj.add("title", "Your request data didn't pass validation");
		obj.add("detail", detail);
		obj.add("status", 400);
		obj.add("instance", instance);
		JsonObject jsonObject = obj.build();
		return jsonToString(jsonObject);
	}
	
	@Path("/accounts")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/plain")
	public Response createAccount(@Context UriInfo uriInfo, String json) {
		
		JsonObject jsonObject = stringToJsonObject(json);
		
		int error_code = validAccountJson(jsonObject);
		String output = "";
		
		if(error_code == 0) {
			String first_name = jsonObject.getString("first_name"); 
			String last_name = jsonObject.getString("last_name"); 
			String phone_number = jsonObject.getString("phone"); 
			String picture = jsonObject.getString("picture");
			Account a = new Account(first_name, last_name, phone_number, picture);
			
			JsonObject obj = Json.createObjectBuilder().add("aid", a.getID()).build();
			output = jsonToString(obj);
			 
			// Build the URI for the "Location:" header
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			builder.path(Integer.toString(a.getID()));
		}
		else if(error_code == 1) {
			output = validationErrorResponse("Invalid first name", "/accounts");
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else if(error_code == 1) {
			output = validationErrorResponse("Invalid last name", "/accounts");
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else if(error_code == 1) {
			output = validationErrorResponse("Invalid phone number", "/accounts");
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else if(error_code == 1) {
			output = validationErrorResponse("Invalid picture URL/filetype", "/accounts");
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		else if(error_code == 1) {
			output = validationErrorResponse("Invalid activation code", "/accounts");
			return Response.status(Response.Status.BAD_REQUEST).entity(output).build();
		}
		return Response.status(Response.Status.OK).entity(output).build();
	}
	
	public int validAccountJson(JsonObject json) {
		
		@SuppressWarnings("unused")
		int error_code;
		
		try {
			String first_name = json.getString("first_name");
			if(first_name.compareTo("") == 0)
				return error_code = 1;
		}
		catch(NullPointerException e) {
			return error_code = 1;
		}
		
		try {
			String last_name = json.getString("last_name"); 
			if(last_name.compareTo("") == 0)
				return error_code = 2;
		}
		catch(NullPointerException e) {
			return error_code = 2;
		}
		
		try {
			String phone_number = json.getString("phone"); 
			if(phone_number.compareTo("") == 0 || DataPatternFormatter.validatePhoneNumber(phone_number) == -1)
				return error_code = 3;
		}
		catch(NullPointerException e) {
			return error_code = 3;
		}
		
		try {
			String picture = json.getString("picture");
			if(picture.compareTo("") == 0)
				return error_code = 4;
		}
		catch(NullPointerException e) {
			return error_code = 4;
		}
		
		try {
			boolean is_active = json.getBoolean("is_active");
		}
		catch(NullPointerException e) {
			return error_code = 5;
		}

		return error_code = 0;

	}
	
}
