package com.sysiq.restwebinars.eshop.model.statemachine;

public class Self extends Action {
	
	Self(){
		setResource("order");
	}

	@Override
	public State apply() {
		return null;
	}

}
