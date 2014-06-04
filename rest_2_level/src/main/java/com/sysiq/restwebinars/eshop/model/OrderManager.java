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
		if(o.getQuantity() > 0 && o.getProduct_uid() != null && o.getShop_url() != null)
			return App.SHOP_MANAGER.addOrder(o);
		return null;
	}

	public Order find(String orderID) {
		return App.SHOP_MANAGER.getByID(orderID);
	}

	public boolean update(String orderID, Order o) {
		if(o.getQuantity() > 0 && o.getProduct_uid() != null && o.getShop_url() != null){
			App.SHOP_MANAGER.changeOrder(orderID, o);
			return true;
		}
		return false;
	}

	public boolean delete(String orderID) {
		if(App.SHOP_MANAGER.getByID(orderID) != null){
			App.SHOP_MANAGER.deleteOrder(orderID);
			return true;
		}
		return false;
		
	}
	
	public String calculateETAG(String orderID){
		Order o = find(orderID);
		if(o != null)		
			return "" + o.getProduct_uid() + o.getShop_url() + o.getShop_url() + "[" + o.getQuantity() + "]" +  o.getStatus();
		return null;
	}

}
