package com.sysiq.restwebinars.admin_client;

import com.sysiq.restwebinars.admin_client.model.helpers.ExchangeDispatcher;

public class AdminMain {

	private static ExchangeDispatcher ed = null;
	
	public static void main(String[] args) {
		
		ed = ExchangeDispatcher.createInstance();
		
		ed.setRate(12.33);
		System.out.println("Check rate: " + ed.getRate());
		
	}

}
