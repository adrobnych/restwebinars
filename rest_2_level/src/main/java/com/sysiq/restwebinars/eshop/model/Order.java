package com.sysiq.restwebinars.eshop.model;

public class Order {

	private String product_uid;
	private String shop_url;
	private int quantity;
	public String getProduct_uid() {
		return product_uid;
	}
	public void setProduct_uid(String product_uid) {
		this.product_uid = product_uid;
	}
	public String getShop_url() {
		return shop_url;
	}
	public void setShop_url(String shop_url) {
		this.shop_url = shop_url;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	

}
