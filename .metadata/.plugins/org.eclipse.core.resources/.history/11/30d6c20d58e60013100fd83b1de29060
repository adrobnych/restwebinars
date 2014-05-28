package com.sysiq.restwebinars.restexchange1;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.header.MediaTypes;
import com.sun.jersey.test.framework.JerseyTest;
import org.junit.Test;
import static org.junit.Assert.*;


public class AdminTest extends JerseyTest {

    public AdminTest()throws Exception {
        super("com.sysiq.restwebinars.restexchange1.resources");
    }


    @Test
    public void testSetRate() {
        WebResource webResource = resource();
        
        String input = "<setrate xmlns=\"http://schemas.restexchange.com/setrate\"></setrate>";
        
		ClientResponse response = webResource.path("setrate").type("application/xml").post(ClientResponse.class, input);

        assertEquals(204, response.getStatus());
    }

}