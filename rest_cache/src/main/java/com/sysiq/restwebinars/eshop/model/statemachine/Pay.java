package com.sysiq.restwebinars.eshop.model.statemachine;


public class Pay extends Action {

	public Pay(){
		setResource("payment");
	}
	
	@Override
	public State apply() {
		return new PaymentAcceptedState();
	}
}
