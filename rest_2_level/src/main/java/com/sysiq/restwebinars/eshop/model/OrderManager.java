package com.sysiq.restwebinars.eshop.model;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class OrderManager {
	private XStream xstream = null;
	
	public OrderManager(){
		xstream = new XStream(new StaxDriver());
		xstream.alias("order", Order.class);
	}

	public Object toXML(Order o) {
		return xstream.toXML(o);
	}

	public Order fromXML(String xmlString) {
		return (Order)xstream.fromXML(xmlString);
	}

	public String save(Order o) {
		return App.SHOP_MANAGER.addOrder(o);
	}

	public Order find(String orderID) {
		return App.SHOP_MANAGER.getByID(orderID);
	}

}
