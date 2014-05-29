package com.sysiq.restwebinars.user_client_spec;


import org.junit.BeforeClass;
import org.junit.Test;

import com.sysiq.restwebinars.user_client.model.helpers.UserExchangeDispatcher;


import static org.junit.Assert.*;


public class UserExchangeDispatcherSpec{
	private static UserExchangeDispatcher ed = null;
	
	@BeforeClass
	public static void init(){
		ed = UserExchangeDispatcher.createInstance();
	}

	//brittle test - depends on already set rate by admin. we need more work on response codes and state machine 
	@Test
	public void testGetRate() {
		assertTrue(ed.getRate() > 0.01);
	}

}