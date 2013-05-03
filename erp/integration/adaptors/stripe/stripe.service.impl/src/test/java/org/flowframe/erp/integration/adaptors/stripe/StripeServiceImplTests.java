package org.flowframe.erp.integration.adaptors.stripe;


import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Random;

import org.flowframe.erp.app.contractmanagement.domain.SubscriptionPlan;
import org.flowframe.erp.app.contractmanagement.type.INTERVALTYPE;
import org.flowframe.erp.app.financialmanagement.domain.payment.CreditCardToken;
import org.flowframe.erp.app.mdm.domain.currency.CurrencyUnit;
import org.flowframe.erp.app.mdm.domain.enums.ITEMUNIT;
import org.flowframe.erp.app.salesmanagement.domain.rates.CalculatableRate;
import org.flowframe.erp.app.salesmanagement.domain.rates.calculator.BaseRateCalculator;
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
import com.stripe.model.Token;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/stripe-module-context-notx.xml"})
public class StripeServiceImplTests  {
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
	
	@Before
	public void setUp() throws Exception {
        Assert.assertNotNull(applicationContext);
        Assert.assertNotNull(stripeRemoteService);
        stripeRemoteService.init();
        
        Stripe.apiKey = "sk_test_IMyuftgRuAaqPdR66twbmylE";
        
		defaultCardParams.put("number", "4242424242424242");
		defaultCardParams.put("exp_month", 12);
		defaultCardParams.put("exp_year", 2015);
		defaultCardParams.put("cvc", "123");
		defaultCardParams.put("name", "Java Bindings Cardholder");
		defaultCardParams.put("address_line1", "140 2nd Street");
		defaultCardParams.put("address_line2", "4th Floor");
		defaultCardParams.put("address_city", "San Francisco");
		defaultCardParams.put("address_zip", "94105");
		defaultCardParams.put("address_state", "CA");
		defaultCardParams.put("address_country", "USA");

		defaultChargeParams.put("amount", 100);
		defaultChargeParams.put("currency", "usd");
		defaultChargeParams.put("card", defaultCardParams);

		defaultTokenParams.put("card", defaultCardParams);

		defaultCustomerParams.put("card", defaultCardParams);
		defaultCustomerParams.put("description", "Java Bindings Customer");

		defaultPlanParams.put("amount", 0);
		defaultPlanParams.put("currency", "usd");
		defaultPlanParams.put("interval", "month");
		defaultPlanParams.put("interval_count", 1);
		defaultPlanParams.put("name", "Limited Free Plan");

		defaultCouponParams.put("duration", "once");
		defaultCouponParams.put("percent_off", 10);

		defaultBankAccountParams.put("country", "US");
		defaultBankAccountParams.put("routing_number", "110000000");
		defaultBankAccountParams.put("account_number", "000123456789");

		defaultRecipientParams.put("name", "Java Test");
		defaultRecipientParams.put("type", "individual");
		defaultRecipientParams.put("tax_id", "000000000");
		defaultRecipientParams.put("bank_account", defaultBankAccountParams);

		defaultTransferParams.put("amount", 100);
		defaultTransferParams.put("currency", "usd");        
    }	
	
	@After
	public void tearDown() throws Exception {
	}
    
    @Ignore
	@Test
    public void testCreatePlan() throws Exception {
    	SubscriptionPlan ffPlan = new SubscriptionPlan();
    	ffPlan.setId(randomId());
    	ffPlan.setName("FF Free Plan - $0");
    	
    	//Price
    	CalculatableRate rate = new CalculatableRate();
    	BaseRateCalculator calc = new BaseRateCalculator(ITEMUNIT.STORAGEINMB, 10.0, 0.0);
    	rate.setCalculator(calc);
    	CurrencyUnit curr = new CurrencyUnit();
    	curr.setCode("USD");
    	rate.setCurrency(curr);
    	ffPlan.setRate(rate);
    	
    	//INT
    	ffPlan.setIntervalType(INTERVALTYPE.MONTHLY);
    	ffPlan.setIntervalCount(1);
    	

    	ffPlan = stripeRemoteService.createPlan(ffPlan);

		assertEquals(ffPlan != null, true);
    }  
    
    @Test
    public void testCreateCustomerAndSubscriberToPlan() throws Exception {
    	SubscriptionPlan ffPlan = new SubscriptionPlan();
    	ffPlan.setId(randomId());
    	ffPlan.setName("FF Free Plan - $0");
    	
    	//Price
    	CalculatableRate rate = new CalculatableRate();
    	BaseRateCalculator calc = new BaseRateCalculator(ITEMUNIT.STORAGEINMB, 10.0, 0.0);
    	rate.setCalculator(calc);
    	CurrencyUnit curr = new CurrencyUnit();
    	curr.setCode("USD");
    	rate.setCurrency(curr);
    	ffPlan.setRate(rate);
    	
    	//INT
    	ffPlan.setIntervalType(INTERVALTYPE.MONTHLY);
    	ffPlan.setIntervalCount(1);
    	

    	ffPlan = stripeRemoteService.createPlan(ffPlan);

		assertEquals(ffPlan != null, true);
		
		
		org.flowframe.erp.app.contractmanagement.domain.Customer cust = new org.flowframe.erp.app.contractmanagement.domain.Customer();
		Token token = Token.create(defaultTokenParams);
		CreditCardToken cct = new CreditCardToken(token.getId());
		cust.setActivePayment(cct);
		cust.setName("Mike Burns");
		
		cust = stripeRemoteService.createCustomerWithPlan(cust, ffPlan);
		assertEquals(cust != null, true);		
    }      
    
    private Long randomId(){
    	Random r = new Random(); 
    	return r.nextLong();    	
    }
}
