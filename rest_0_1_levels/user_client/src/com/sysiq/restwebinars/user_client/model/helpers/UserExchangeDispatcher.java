package com.sysiq.restwebinars.user_client.model.helpers;


import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;


public class UserExchangeDispatcher {
	
	private Client client = null;
	static UserExchangeDispatcher ed;
	private WebResource convertResource = null;
	
	private UserExchangeDispatcher(){}
	
	public static UserExchangeDispatcher createInstance(){
		ed = new UserExchangeDispatcher();
		ed.client = Client.create();		
		return ed;
	}

	public double convertUSDToUAH(double amount) {
		ed.convertResource  = ed.client.resource("http://localhost:9998/amount_of_uah_from/" + amount + "/usd");
		String responseAmount = convertResource.get(String.class);
		System.out.println("***************************************************");
		System.out.println(responseAmount);
		return new Double(responseAmount);
	}


}
