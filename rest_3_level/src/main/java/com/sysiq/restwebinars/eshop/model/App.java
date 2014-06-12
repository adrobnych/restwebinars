package com.sysiq.restwebinars.eshop.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public enum App {
	SHOP_MANAGER();
	
	private Map<String, OrderWithState> orders;
	
	App(){
		this.orders = new HashMap<String, OrderWithState>();
	}
	
	synchronized public String addOrder(OrderWithState o){
		String id  = UUID.randomUUID().toString();
		orders.put(id, o);
		return id;
	}

	public OrderWithState getByID(String orderID) {
		return orders.get(orderID);
	}

	public void changeOrder(String orderID, OrderWithState new_o) {
		OrderWithState o = orders.get(orderID);
		if(o == null)
			throw new RuntimeException("no order found");
		orders.put(orderID, new_o);
	}

	public void deleteOrder(String orderID) {
		orders.remove(orderID);
	}

}
