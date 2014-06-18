package com.sysiq.restwebinars.eshop.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.sysiq.restwebinars.eshop.model.Order;
import com.sysiq.restwebinars.eshop.model.OrderManager;
import com.sysiq.restwebinars.eshop.model.OrderWithState;
import com.sysiq.restwebinars.eshop.model.statemachine.NewOrderState;
import com.sysiq.restwebinars.eshop.model.statemachine.State;

@Path("/order")
public class OrderResource {
	
	private OrderManager om = null;
	
	public OrderResource() {
		om = new OrderManager();
	}
    
    @POST 
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response createOrder(String xmlString) {
    	
    	//Create cache control header
        CacheControl cc = new CacheControl();
        //Set max age to one day
        cc.setMaxAge(86400);
    	
    	Order o = om.fromXML(xmlString);
    	if(o.getStatus() == null)
    		o.setStatus("active");
    	OrderWithState ows = new OrderWithState();
    	ows.setOrder(o);
    	ows.setState(new NewOrderState());
    	String orderID = om.save(ows);
    	
    	//Calculate the ETag
        EntityTag etag = new EntityTag(om.calculateETAG(orderID));
    	
    	Response r = null;
		try {
			if(orderID == null)
				throw new WebApplicationException(Response.Status.BAD_REQUEST);        // 400
			r = Response.status(201).entity(decorateWithDAP(om.toXML(ows.getOrder()).toString(), ows.getState(), orderID)).location(new URI("" + orderID)).cacheControl(cc).tag(etag).build();
		} catch (URISyntaxException e) {
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);  // 500
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return r;
    }
    
    @GET
    @Produces("application/xml")
    @Path("/{orderId}")
    public Response getOrder(@PathParam("orderId") String orderID, @Context Request request) {
    	
    	EntityTag etag = null;
    	Response.ResponseBuilder responseBuilder = null;
    	
    	OrderWithState ows = om.find(orderID);
    	
    	if (ows != null)
			try {
				
				etag = new EntityTag(om.calculateETAG(orderID));
		    	responseBuilder = request.evaluatePreconditions(etag);

		    	if (responseBuilder != null) {
		    		// Etag match
		    		//Order has not changed..returning unmodified response code
		    		return responseBuilder.build();
		    	}
				
				CacheControl cc = new CacheControl();
				cc.setPrivate(true);
		        cc.setMaxAge(0);
				
				return Response.ok(decorateWithDAP(om.toXML(ows.getOrder()).toString(), ows.getState(), orderID)).tag(etag).cacheControl(cc).build();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else 
    		throw new WebApplicationException(Response.Status.NOT_FOUND);  // 404
    	
    	return null;

    }
    
   

	@PUT 
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/{orderId}")
    public Response updateOrder(@PathParam("orderId") String orderID, String xmlString, @Context Request request){
    	
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
    	
    	OrderWithState ows = om.find(orderID);
    	    	
    	if(ows == null) 
    		throw new WebApplicationException(Response.Status.NOT_FOUND);  // 404
    	
    	if(ows.getOrder().getStatus().equals("completed"))
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
    		
    		OrderWithState n_ows = new OrderWithState();
    		n_ows.setOrder(new_o);
    		
    		if(!om.update(orderID, n_ows))
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
    	
    	Order o = om.find(orderID).getOrder();
    	
    	if (o != null) {
    		om.delete(orderID);
    		return Response.status(204).build();  // 204
    	}
    	else 
    		throw new WebApplicationException(Response.Status.NOT_FOUND);  // 404

    }
   
    private String decorateWithDAP(String xmlString, State state, String orderID) throws ParserConfigurationException, SAXException, IOException {
    	DocumentBuilderFactory dbFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    	Document doc = dBuilder.parse(new ByteArrayInputStream(xmlString.getBytes())); // dBuilder.newDocument();
    	
    	Element el = doc.createElement("hypermedia");
    	doc.appendChild(el);
    	
    	String xml = getHypermediaLinks(state, orderID);
    	Document doc2 = dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));

    	Node node = doc.importNode(doc2.getDocumentElement(), true);
    	el.appendChild(node);
		return getStringFromDocument(doc);
	}
    
    private String getHypermediaLinks(State state, String orderID) {
		String result = "<actions>";
		for(String actionKey : state.getAvailableActions().keySet())
			result += "<dap:link mediaType=\"application/vnd.restmall+xml\" " +
						"uri=\""+ getBaseURI() + "/" + state.getAvailableActions().get(actionKey).getResource() + "/" + orderID + "\" " +
						"rel=\"http://relations.restmall.com/" + actionKey +"\" />";
		return result + "</actions>";
	}

	public String getStringFromDocument(Document doc)
    {
        try
        {
           DOMSource domSource = new DOMSource(doc);
           StringWriter writer = new StringWriter();
           StreamResult result = new StreamResult(writer);
           TransformerFactory tf = TransformerFactory.newInstance();
           Transformer transformer = tf.newTransformer();
           transformer.transform(domSource, result);
           return writer.toString();
        }
        catch(TransformerException ex)
        {
           ex.printStackTrace();
           return null;
        }
    } 
    
    private String getBaseURI() {
        return "http://localhost:9998/";
    }
}

