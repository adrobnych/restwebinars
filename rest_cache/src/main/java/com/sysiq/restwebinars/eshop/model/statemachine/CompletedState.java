package com.sysiq.restwebinars.eshop.model.statemachine;

import java.util.HashMap;

public class CompletedState extends State {

	public CompletedState(){
		actionsAvailable = new HashMap<String, Action>();
		actionsAvailable.put("self", new Self());
	}
}
