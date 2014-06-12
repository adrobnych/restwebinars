package com.sysiq.restwebinars.eshop.model.statemachine;


public abstract class Action {
	
	private String resource;
	
	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
	
	public abstract State apply();
	
}
