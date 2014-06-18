package com.sysiq.restwebinars.eshop.model.statemachine;

import java.util.HashMap;

public class PaymentAcceptedState extends State {

	public PaymentAcceptedState(){
		actionsAvailable = new HashMap<String, Action>();
		actionsAvailable.put("deliver", new Deliver());
	}
}
