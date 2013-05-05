package org.flowframe.erp.integration.adaptors.stripe.webhooks.webapp.tests;


import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EventSubmissionTests  {
	
	private InputStream event1 = null;

	@Before
	public void setUp() throws Exception {
		try {
			event1 = EventSubmissionTests.class.getResourceAsStream("/event1.json");
		} catch (Exception e) {
			throw new RuntimeException(
					"Could not load event1.json", e);
		}		
    }     
	
	@After
	public void destroy() throws Exception {
		try {
			event1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
    } 	


	@Test
	public void testEventSubmission(){
		try {
			HttpHost targetHost = new HttpHost("stage.conxbi.com", Integer.valueOf(8082), "http");
			//HttpHost targetHost = new HttpHost("localhost", Integer.valueOf(8082), "http");
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost post = new HttpPost(
					"/stripe/webhook");
			
			StringWriter writer = new StringWriter();
			IOUtils.copy(event1, writer, "UTF-8");
			String event1Str = writer.toString();
			StringEntity re = new StringEntity(event1Str,"UTF-8");
			post.setEntity(re);
			
			
			HttpResponse resp = httpclient.execute(targetHost, post);
			System.out.println("Status:["+resp.getStatusLine()+"]");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
    private Long randomId(){
    	Random r = new Random(); 
    	return r.nextLong();    	
    }
}
