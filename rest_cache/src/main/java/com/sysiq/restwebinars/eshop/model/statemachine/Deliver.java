package com.sysiq.restwebinars.eshop.model.statemachine;

public class Deliver extends Action {
	
	Deliver(){
		setResource("order");
	}

	@Override
	public State apply() {
		return new CompletedState();
	}

}
