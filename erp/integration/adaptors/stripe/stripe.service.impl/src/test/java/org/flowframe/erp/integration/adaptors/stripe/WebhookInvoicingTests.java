package org.flowframe.erp.integration.adaptors.stripe;


import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.flowframe.erp.app.contractmanagement.domain.Customer;
import org.flowframe.erp.app.financialmanagement.domain.receivable.ARReceipt;
import org.flowframe.erp.app.financialmanagement.domain.receivable.ARReceiptLine;
import org.flowframe.erp.app.mdm.domain.currency.CurrencyUnit;
import org.flowframe.erp.app.mdm.domain.product.Product;
import org.flowframe.erp.integration.adaptors.stripe.services.Event;
import org.flowframe.erp.integration.adaptors.stripe.services.IEventBusinessServicePortType;
import org.flowframe.erp.integration.adaptors.stripe.services.IEventDAOServicePortType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.stripe.Stripe;
import com.stripe.model.Invoice;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/stripe-module-context-notx.xml"})
public class WebhookInvoicingTests  {
	static HashMap<String, Object> defaultCardParams = new HashMap<String, Object>();
	static HashMap<String, Object> defaultChargeParams = new HashMap<String, Object>();
	static HashMap<String, Object> defaultCustomerParams = new HashMap<String, Object>();
	static HashMap<String, Object> defaultPlanParams = new HashMap<String, Object>();
	static HashMap<String, Object> defaultCouponParams = new HashMap<String, Object>();
	static HashMap<String, Object> defaultTokenParams = new HashMap<String, Object>();
	static HashMap<String, Object> defaultBankAccountParams = new HashMap<String, Object>();
	static HashMap<String, Object> defaultTransferParams = new HashMap<String, Object>();
	static HashMap<String, Object> defaultRecipientParams = new HashMap<String, Object>();
	
	@Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
	private StripeServicesImpl stripeRemoteService;
	
	@Autowired
	private IEventBusinessServicePortType eventBusinessService;
	
	@Autowired
	private IEventDAOServicePortType eventDAOService;
	
	private String customerId = "cus_1mtFirtdycAMf2";
	private String planId = "-6096972491155374337";
	private Customer customer = new Customer();
	
	@Ignore
	@Before
	public void setUp() throws Exception {
        Assert.assertNotNull(applicationContext);
        Assert.assertNotNull(stripeRemoteService);
        Assert.assertNotNull(eventDAOService);
        Assert.assertNotNull(eventBusinessService);

        CurrencyUnit usd = new CurrencyUnit();
        usd.setCode("USD");
        stripeRemoteService.setUsd(usd);
        
        customer.setExternalRefId(customerId);
        
        Stripe.apiKey = "sk_test_IMyuftgRuAaqPdR66twbmylE";        
    }	
	
	@After
	public void tearDown() throws Exception {
	}
    

    @Test
    public void testAddInvoiceLineToExistingInvoice() throws Exception {
    	List<Event> createdInvoices = eventDAOService.getAllInvoiceEventsCreated();
    	for (Event createdInvoice : createdInvoices) {
    		String bdy = createdInvoice.getBody();
    		Invoice inv = Invoice.retrieve(createdInvoice.getObjectId());
    		Assert.assertNotNull(inv);
    		Product prod = new Product();
    		prod.setName("Bandwidth Usage");
    		ARReceipt rcpt = new ARReceipt();
    		rcpt.setExternalRefId(inv.getId());
    		rcpt.setCurrency(stripeRemoteService.getUsd());
    		rcpt.setDebtor(customer);
    		ARReceiptLine rl = new ARReceiptLine(rcpt, prod, null, null, null, 533, stripeRemoteService.getUsd(), null, 1, "Bandwidth Usage");
    		rl = stripeRemoteService.createInvoiceLine(rl);
    		Assert.assertNotNull(rl);
    	}
    }      
    
    private Long randomId(){
    	Random r = new Random(); 
    	return r.nextLong();    	
    }
}
