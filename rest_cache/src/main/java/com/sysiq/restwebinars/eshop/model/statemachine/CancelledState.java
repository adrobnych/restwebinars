package com.sysiq.restwebinars.eshop.model.statemachine;

import java.util.HashMap;

public class CancelledState extends State{
	
	public CancelledState(){
		actionsAvailable = new HashMap<String, Action>();
		actionsAvailable.put("self", new Self());
	}
	
}
