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

	public void changeOrder(String orderID, Order new_o) {
		Order o = orders.get(orderID);
		if(o == null)
			throw new RuntimeException("no order found");
		orders.put(orderID, new_o);
	}

	public void deleteOrder(String orderID) {
		orders.remove(orderID);
	}

}
