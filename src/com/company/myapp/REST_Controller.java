package com.company.myapp;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.google.gson.JsonParser;

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
	
	@Path("/accounts")
	@POST
	@Consumes("application/json")
	@Produces("text/plain")
	public Response createAccount(@Context UriInfo uriInfo, String json) {
		JsonReader jsonReader = Json.createReader(new StringReader(json));
		JsonObject jsonObject = jsonReader.readObject();
		jsonReader.close();
		String first_name = jsonObject.getString("first_name").toString(); 
		String last_name = jsonObject.getString("last_name").toString(); 
		String phone_number = jsonObject.getString("phone").toString(); 
		String picture = jsonObject.getString("picture").toString();
		Account a = new Account(first_name, last_name, phone_number, picture);
		
		JsonObject obj = Json.createObjectBuilder().add("aid", a.getID()).build();
		Map<String, Boolean> config = new HashMap<>();
		 
		config.put(JsonGenerator.PRETTY_PRINTING, true);
		        
		JsonWriterFactory writerFactory = Json.createWriterFactory(config);
		        
		String jsonString = "";
		 
		try(Writer writer = new StringWriter()) {
		    writerFactory.createWriter(writer).write(obj);
		    jsonString = writer.toString();
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Build the URI for the "Location:" header
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path(Integer.toString(a.getID()));
		
		return Response.status(Response.Status.OK).entity(jsonString).build();
	}
	
}
