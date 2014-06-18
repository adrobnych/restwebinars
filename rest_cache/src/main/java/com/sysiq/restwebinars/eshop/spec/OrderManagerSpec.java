package com.sysiq.restwebinars.eshop.spec;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sysiq.restwebinars.eshop.model.Order;
import com.sysiq.restwebinars.eshop.model.OrderManager;
import com.sysiq.restwebinars.eshop.model.OrderWithState;
import com.sysiq.restwebinars.eshop.model.statemachine.NewOrderState;

public class OrderManagerSpec {
	private OrderManager om = null;
	
	@Before
	public void init(){
		om = new OrderManager();
	}

	@Test
	public void testOrderManagerToXML() {
		Order o = new Order();
		o.setProduct_uid("asdf3456zxcv");
		o.setShop_url("http://restmall/123");
		o.setQuantity(2);
		
		String expected = "<?xml version=\"1.0\" ?><order><product__uid>asdf3456zxcv</product__uid><shop__url>http://restmall/123</shop__url><quantity>2</quantity></order>";
		assertEquals(expected, om.toXML(o));
	}
	
	@Test
	public void testOrderManagerFromXML() {
		Order expectedOrder = om.fromXML("<?xml version=\"1.0\" ?><order><product__uid>asdf3456zxcv</product__uid><shop__url>http://restmall/123</shop__url><quantity>2</quantity></order>");
		assertEquals("asdf3456zxcv", expectedOrder.getProduct_uid());
	}
	
	@Test
	public void testSaveAndFindByID(){
		Order o = new Order();
		o.setProduct_uid("asdf3456zxcv111");
		o.setShop_url("http://restmall/124");
		o.setQuantity(3);
		OrderWithState ows = new OrderWithState();
		ows.setOrder(o);
		
		String orderID = om.save(ows);
		Order expectedOrder = om.find(orderID).getOrder();
		assertEquals("asdf3456zxcv111", expectedOrder.getProduct_uid());
	}

}
