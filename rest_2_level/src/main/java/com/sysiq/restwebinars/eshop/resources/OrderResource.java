package com.sysiq.restwebinars.eshop.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import com.sysiq.restwebinars.eshop.model.Order;
import com.sysiq.restwebinars.eshop.model.OrderManager;

@Path("/order")
public class OrderResource {
	
	private OrderManager om = null;
	
	public OrderResource() {
		om = new OrderManager();
	}
    
    @POST 
    @Consumes(MediaType.APPLICATION_XML)
    public Response createOrder(String xmlString) {
    	
    	//Create cache control header
        CacheControl cc = new CacheControl();
        //Set max age to one day
        cc.setMaxAge(86400);
    	
    	Order o = om.fromXML(xmlString);
    	if(o.getStatus() == null)
    		o.setStatus("active");
    	String orderID = om.save(o);
    	
    	//Calculate the ETag
        EntityTag etag = new EntityTag(om.calculateETAG(orderID));
    	
    	Response r;
		try {
			if(orderID == null)
				throw new WebApplicationException(Response.Status.BAD_REQUEST);        // 400
			r = Response.status(204).location(new URI("/order/" + orderID)).cacheControl(cc).tag(etag).build();
		} catch (URISyntaxException e) {
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);  // 500
		}
    	return r;
    }
    
    @GET
    @Produces("application/xml")
    @Path("/{orderId}")
    public String getOrder(@PathParam("orderId") String orderID) {
    	
    	Order o = om.find(orderID);
    	
    	if (o != null) 
    		return om.toXML(o).toString();  // 200
    	else 
    		throw new WebApplicationException(Response.Status.NOT_FOUND);  // 404

    }
    
    @PUT 
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/{orderId}")
    public Response updateOrder(@PathParam("orderId") String orderID, String xmlString, @Context Request request){
    	
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
    	
    	Order o = om.find(orderID);
    	    	
    	if(o == null) 
    		throw new WebApplicationException(Response.Status.NOT_FOUND);  // 404
    	
    	if(o.getStatus().equals("completed"))
    		return Response.status(405).build(); // 405 Method not allowed
    	
    	EntityTag etag = new EntityTag(om.calculateETAG(orderID));

    	System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
    	System.out.println(etag);
    	
    	//check for outdated eTag
    	if(request.evaluatePreconditions(etag) != null)
    		throw new WebApplicationException(Response.Status.CONFLICT);     // 409; other option is 412 - Precondition Failed
    		
    	Response r;
    	
    	try {
    		Order new_o = om.fromXML(xmlString);
    		if(new_o.getStatus() == null)
    			new_o.setStatus("active");
    		if(!om.update(orderID, new_o))
    			throw new WebApplicationException(Response.Status.BAD_REQUEST);        // 400
    		etag = new EntityTag(om.calculateETAG(orderID));
    		r = Response.status(204).location(new URI("/order/" + orderID)).cacheControl(cc).tag(etag).build();
    	} catch (URISyntaxException e) {
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);  // 500
		}
    	return r;
    }
    
    @DELETE
    @Path("/{orderId}")
    public Response deleteOrder(@PathParam("orderId") String orderID) {
    	
    	Order o = om.find(orderID);
    	
    	if (o != null) {
    		om.delete(orderID);
    		return Response.status(204).build();  // 204
    	}
    	else 
    		throw new WebApplicationException(Response.Status.NOT_FOUND);  // 404

    }
}

