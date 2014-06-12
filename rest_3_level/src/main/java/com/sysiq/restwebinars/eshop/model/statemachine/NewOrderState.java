package com.sysiq.restwebinars.eshop.model.statemachine;

import java.util.HashMap;

public class NewOrderState extends State {
	public NewOrderState(){
		actionsAvailable = new HashMap<String, Action>();
		actionsAvailable.put("pay", new Pay());
		actionsAvailable.put("cancel", new Cancel());
	}
}
