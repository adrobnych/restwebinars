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
	private WebResource checkRateResource = null;
	
	private UserExchangeDispatcher(){}
	
	public static UserExchangeDispatcher createInstance(){
		UserExchangeDispatcher ed = new UserExchangeDispatcher();
		ed.client = Client.create();
		ed.checkRateResource = ed.client.resource("http://localhost:9998/checkrate");
		
		return ed;
	}

	public double getRate() {
		String responseCheckRate = checkRateResource.get(String.class);
		System.out.println("***************************************************");
		System.out.println(responseCheckRate);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		String rateAsString = null;
		double result = -1;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(responseCheckRate));
			Document doc = builder.parse(is);
			NodeList list = doc.getElementsByTagName("item");
			for(int i=0; i<list.getLength(); i++)
				if((list.item(i).getTextContent().indexOf("UAH") != -1) && (list.item(i).getChildNodes().getLength() == 3)){
						rateAsString = list.item(i).getChildNodes().item(2).getTextContent();	
						System.out.println("-->"+rateAsString);
				}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(rateAsString == null)
				 throw new RuntimeException("Failed : wrong responce " + responseCheckRate);
			else 
				result = new Double(rateAsString);
		}
		
		return result;
	}

}
