package com.sysiq.restwebinars.eshop.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public enum App {
	SHOP_MANAGER();
	
	private Map<String, Order> orders;
	
	App(){
		this.orders = new HashMap<String, Order>();
	}
	
	synchronized public String addOrder(Order o){
		String id  = UUID.randomUUID().toString();
		orders.put(id, o);
		return id;
	}

	public Order getByID(String orderID) {
		return orders.get(orderID);
	}

}
