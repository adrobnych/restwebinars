package com.sysiq.restwebinars.eshop.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sysiq.restwebinars.eshop.model.Order;
import com.sysiq.restwebinars.eshop.model.OrderManager;

@Path("/order")
public class OrderResource {
	
	private OrderManager om = null;
	
	public OrderResource() {
		om = new OrderManager();
	}

    @GET 
    @Produces("text/plain")
    public String getClichedMessage() {
        // Return some cliched textual content
        return "Hello World";
    }
    
    @POST 
    @Consumes(MediaType.APPLICATION_XML)
    public Response setRate(String xmlString) {
    	
    	Order o = om.fromXML(xmlString);
    	String orderID = om.save(o);
    	
    	Response r;
		try {
			if(orderID == null)
				throw new WebApplicationException(Response.Status.BAD_REQUEST);
			r = Response.status(204).location(new URI("/order/" + orderID)).build();
		} catch (URISyntaxException e) {
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
    	return r;
    }
    
    @GET
    @Produces("application/xml")
    @Path("/{orderId}")
    public String getOrder(@PathParam("orderId") String orderID) {
    	
    	Order o = om.find(orderID);
    	
		return om.toXML(o).toString();
    }
}

