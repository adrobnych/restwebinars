package com.sysiq.restwebinars.restexchange1.resources;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sysiq.restwebinars.restexchange1.model.managers.App;

@Path("/amount_of_uah_from/{amount}/{originCurrency}")
public class ConverterResource {

	@GET 
	@Produces(MediaType.TEXT_PLAIN)
	public String getConvertedAmount(	
			@PathParam("amount") Double amount,
			@PathParam("originCurrency") String originCurrency) {

		if(!originCurrency.equals("usd"))
			return "origin currency not supported: " + originCurrency;   // replace by responce code 4**
		
		return "" + amount * App.EXCHANGE_MANAGER.getRate();
		
	}
}
