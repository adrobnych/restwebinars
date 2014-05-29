package com.sysiq.restwebinars.admin_client_spec;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sysiq.restwebinars.admin_client.model.helpers.ExchangeDispatcher;

import static org.junit.Assert.*;


public class ExchangeDispatcherSpec{

	private static ExchangeDispatcher ed = null;
	
	@BeforeClass
	public static void init(){
		ed = ExchangeDispatcher.createInstance();
	}

	@Test
	public void testSetRate() {
		ed.setRate(12.01);
		assertEquals(12.01, ed.getRate(), 0.0001);
	}

}