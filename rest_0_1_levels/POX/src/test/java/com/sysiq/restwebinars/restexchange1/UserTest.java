package com.sysiq.restwebinars.restexchange1;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.header.MediaTypes;
import com.sun.jersey.test.framework.JerseyTest;
import org.junit.Test;
import static org.junit.Assert.*;


public class UserTest extends JerseyTest {

    public UserTest()throws Exception {
        super("com.sysiq.restwebinars.restexchange1.resources");
    }


    @Test
    public void testSetAndGetRate() {
        WebResource webResource = resource();
        
        String input = 	"<setrate xmlns=\"http://schemas.restexchange.com/setrate\">"+
        					"<items>"+
        						"<item>"+
        							"<from>USD</from>"+
        							"<to>UAH</to>"+
        							"<factor>12.55</factor>"+
        						"</item>"+
        					"</items>"+
        				"</setrate>";
        
		ClientResponse response = webResource.path("setrate").type("application/xml").post(ClientResponse.class, input);

        assertEquals(204, response.getStatus());
        
        String responseConvertMoney = webResource.path("amount_of_uah_from/100/usd").get(String.class);
        assertEquals("1255.0", responseConvertMoney);
    }

}
