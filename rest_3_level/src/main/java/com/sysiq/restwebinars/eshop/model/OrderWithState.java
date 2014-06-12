package com.sysiq.restwebinars.eshop.model;

import com.sysiq.restwebinars.eshop.model.statemachine.State;

public class OrderWithState {
	private Order order;
	private State state;
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	

}
