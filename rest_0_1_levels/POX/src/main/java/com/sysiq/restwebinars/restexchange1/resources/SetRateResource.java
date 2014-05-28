package com.sysiq.restwebinars.restexchange1.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/setrate")
public class SetRateResource {

    @POST 
    @Consumes(MediaType.APPLICATION_XML)
    public Response setRate(String xmlString) {
    	 
		System.out.println("____________________ request body: __________________________");
		System.out.println(xmlString);
		
		return Response.status(204).build();
 
	}
}