package com.company.myapp;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.io.IOException;
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
	@GET
	@Produces("text/plain")
	public Response createAccount() {
		String first_name = "John"; String last_name = "Doe"; 
		String phone_number = "708-123-4567"; String picture = "www.example.com/pic.jpeg";
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
		//System.out.println(jsonString);
		
		return Response.status(Response.Status.OK).entity(jsonString).build();
	}
	
}
