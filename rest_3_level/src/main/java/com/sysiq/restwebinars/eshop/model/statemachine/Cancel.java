package com.sysiq.restwebinars.eshop.model.statemachine;

public class Cancel extends Action {
	
	Cancel(){
		setResource("order");
	}

	@Override
	public State apply() {
		return new CancelledState();
	}

}
