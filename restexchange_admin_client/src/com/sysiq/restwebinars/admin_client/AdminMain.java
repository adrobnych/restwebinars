package com.sysiq.restwebinars.admin_client;



import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class AdminMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Client client = Client.create();
		 
		WebResource webResource = client.resource("http://localhost:9998/checkrate");
 
		ClientResponse response = webResource.get(ClientResponse.class);
 
		if (response.getStatus() != 200)
			System.out.println("Failed : HTTP error code : " + response.getStatus());
		else
			System.out.println("Connected: \n" + response);
		
	}

}
