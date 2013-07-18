package org.flowframe.erp.app.contractmanagement.business.services.impl.tests.integration;

import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.flowframe.erp.app.contractmanagement.business.services.impl.ContractManagementJobServicesImpl;
import org.flowframe.erp.app.contractmanagement.dao.services.ISubscriptionDAOService;
import org.flowframe.erp.app.contractmanagement.dao.services.impl.ServiceProvisionDAOImpl;
import org.flowframe.erp.app.contractmanagement.dao.services.impl.SubscriptionDAOImpl;
import org.flowframe.erp.app.contractmanagement.domain.Customer;
import org.flowframe.erp.app.contractmanagement.domain.Subscription;
import org.flowframe.erp.app.contractmanagement.domain.SubscriptionPlan;
import org.flowframe.erp.app.contractmanagement.domain.service.ServiceProvision;
import org.flowframe.erp.app.contractmanagement.domain.service.ServiceProvisionGroup;
import org.flowframe.erp.app.contractmanagement.domain.service.ServiceProvisionGroupService;
import org.flowframe.erp.app.contractmanagement.domain.service.ServiceProvisionQuota;
import org.flowframe.erp.app.contractmanagement.type.INTERVALTYPE;
import org.flowframe.erp.app.contractmanagement.type.SERVICEJOBTYPE;
import org.flowframe.erp.app.contractmanagement.type.SERVICEQUOTARESETTYPE;
import org.flowframe.erp.app.financialmanagement.dao.services.impl.PaymentDAOImpl;
import org.flowframe.erp.app.financialmanagement.domain.payment.CreditCardToken;
import org.flowframe.erp.app.mdm.domain.enums.ITEMUNIT;
import org.flowframe.erp.app.salesmanagement.domain.rates.CalculatableRate;
import org.flowframe.erp.app.salesmanagement.domain.rates.calculator.BaseRateCalculator;
import org.flowframe.erp.integration.adaptors.stripe.StripeServicesImpl;
import org.flowframe.erp.integration.adaptors.stripe.domain.event.Event;
import org.flowframe.kernel.common.mdm.dao.services.impl.OrganizationDAOImpl;
import org.flowframe.kernel.common.utils.Validator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.stripe.model.Token;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/flowframe/tm.jta-module-context.xml", "/META-INF/core.dao.datasource.mysql-module-context.xml",
		"/META-INF/flowframe/jpa.lob.jpacontainer.springdm.mysql-module-context.xml", "/META-INF/flowframe/dao.services.impl-module-context.xml",
		"/META-INF/flowframe/metamodel.dao.services.impl-module-context.xml", "/META-INF/documentlibrary-module-context.xml", "/META-INF/portal-module-context.xml",
		"/META-INF/financialmanagement-module-context.xml", "/META-INF/mdm-module-context.xml", "/META-INF/paymentprocessor-module-context.xml",
		"/META-INF/contractmanagementjobservicesimpl-module-context.xml" })
public class ContractManagementJobServicesImplTests extends AbstractJUnit4SpringContextTests {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	static HashMap<String, Object> defaultTokenParams = new HashMap<String, Object>();
	static HashMap<String, Object> defaultCardParams = new HashMap<String, Object>();

	@Autowired
	private PlatformTransactionManager globalTransactionManager;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ContractManagementJobServicesImpl contractManagementJobService;

	@Autowired
	private StripeServicesImpl stripeRemoteService;

	@Autowired
	private SubscriptionDAOImpl subscriptionDAO;
	
	@Autowired
	private ServiceProvisionDAOImpl serviceProvisionDAO;

	@Autowired
	private PaymentDAOImpl paymentDAOImpl;
	
	@Autowired
	private OrganizationDAOImpl organizationDAO;

	private SubscriptionPlan ffPlan;
	private Customer ffCustomer;

	@Before
	public void setUp() throws Exception {
		Assert.assertNotNull(applicationContext);
		Assert.assertNotNull(contractManagementJobService);
		Assert.assertNotNull(subscriptionDAO);
		Assert.assertNotNull(stripeRemoteService);

		defaultCardParams.put("number", "4242424242424242");
		defaultCardParams.put("exp_month", 12);
		defaultCardParams.put("exp_year", 2015);
		defaultCardParams.put("cvc", "123");
		defaultCardParams.put("name", "Mike Burns");
		defaultCardParams.put("address_line1", "140 2nd Street");
		defaultCardParams.put("address_line2", "4th Floor");
		defaultCardParams.put("address_city", "San Francisco");
		defaultCardParams.put("address_zip", "94105");
		defaultCardParams.put("address_state", "CA");
		defaultCardParams.put("address_country", "USA");

		defaultTokenParams.put("card", defaultCardParams);

		// 1. Reset webhooks server
		contractManagementJobService.getEventDAOService().resetDB();

		// 2. Create freeplan, customer, and subscribe to a free plan
		createCustomerAndSubscriberToFreePlan();

		// 3. Wait until invoice.created is available
		int tryCount = 0;
		List<Event> events = new ArrayList<Event>();
		while (tryCount < 3) {
			Thread.sleep(10000);
			events = contractManagementJobService.getEventDAOService().getAllInvoiceEventsCreated();
			if (!events.isEmpty()) {
				logger.info("Found events on " + tryCount + "...");
				break;
			} else
				logger.info("Try " + tryCount + "...");
			tryCount++;
		}

		Assert.assertNotNull(events);
		Assert.assertTrue(!events.isEmpty());

	}

	@After
	public void tearDown() throws Exception {
		// em.close();
	}

	@Test
	public void testProcessNewInvoices() throws Exception {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName("ContractManagementJobServicesImplTests.testProcessNewInvoices");
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		def.setTimeout(1000);
		TransactionStatus status = this.globalTransactionManager.getTransaction(def);
		try {
			contractManagementJobService.processNewInvoices();
			this.globalTransactionManager.commit(status);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
			this.globalTransactionManager.rollback(status);

			throw new RuntimeException(stacktrace, e);
		}
	}

	public void createCustomerAndSubscriberToFreePlan() throws Exception {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName("ContractManagementJobServicesImplTests.createCustomerAndSubscriberToFreePlan");
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		def.setTimeout(1000);
		TransactionStatus status = this.globalTransactionManager.getTransaction(def);
		try {

			// 1. Create Free Plan
			ffPlan = stripeRemoteService.getSubscriptionPlanByName(ISubscriptionDAOService.FREE_TRIAL_PLAN_NAME);
			if (Validator.isNull(ffPlan)) {
				ffPlan = provideFreePlanData();
				ffPlan = stripeRemoteService.createPlan(ffPlan);
			}
			else {
				ffPlan.setCode(ISubscriptionDAOService.FREE_TRIAL_PLAN_NAME);
			}
			assertEquals(ffPlan != null, true);

			// Save plan
			ffPlan = subscriptionDAO.providePlan(ffPlan);

			// 2. Create customer - registration
			ffCustomer = stripeRemoteService.getCustomerByDesription("Logistics LLC.");
			if (Validator.isNull(ffCustomer)) {
				ffCustomer = new org.flowframe.erp.app.contractmanagement.domain.Customer();
				ffCustomer.setName("Logistics LLC.");
				ffCustomer = stripeRemoteService.createCustomer(ffCustomer);
			}
			ffCustomer.setSubscription(null);
			ffCustomer = (Customer) organizationDAO.provide(ffCustomer);

			// 3. Upgrade to paid-for plan
			ffCustomer = stripeRemoteService.cancelCustomerSubscription(ffCustomer);
			ffCustomer = (Customer) organizationDAO.update(ffCustomer);

			Token token = Token.create(defaultTokenParams);
			CreditCardToken cct = new CreditCardToken(token.getId());
			cct = (CreditCardToken)paymentDAOImpl.add(cct);
			//ffCustomer.setActivePayment(cct);
			ffCustomer = (Customer) organizationDAO.update(ffCustomer);

			ffCustomer = stripeRemoteService.updateCustomer(ffCustomer, cct);
			Subscription ffSubscription = new Subscription(ffPlan, ffCustomer);
			ffSubscription.setStart(new Date(System.currentTimeMillis()));
			ffSubscription.setCurrentPeriodStart(new Date(System.currentTimeMillis()));
			ffSubscription.setCurrentPeriodEnd(new Date(System.currentTimeMillis()));
			ffSubscription.setTrialEnd(new Date(System.currentTimeMillis()));
			ffSubscription.setSubscribedPlan(ffPlan);
			ffSubscription.setCustomer(ffCustomer);

			ffSubscription = stripeRemoteService.createSubscription(ffSubscription);
			assertEquals(ffSubscription != null, true);

			ffSubscription = subscriptionDAO.provide(ffSubscription);

			this.globalTransactionManager.commit(status);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
			this.globalTransactionManager.rollback(status);

			throw new RuntimeException(stacktrace, e);
		}
	}
	
	private SubscriptionPlan provideFreePlanData() throws Exception {
		SubscriptionPlan freePlan = null;
		try
		{
			/**
			 * Free Plan
			 */
			/**
			 * == ETL Service with 5 job limit
			 */
			ServiceProvision etlService = new ServiceProvision();
			etlService.setName("Free ETL Service : 5 Job Limit");
			etlService.setCode("Free ETL Service : 5 Job Limit");
			etlService = (ServiceProvision)serviceProvisionDAO.add(etlService);
			
			/**
			 * == Reporting Service with 5 job limit
			 */
/*			ReportingServiceProvision rptService = new ReportingServiceProvision();
			rptService.setName("Free Reporting Service : 2 Job Defs/3 Runs");
			rptService.setCode("Free Reporting Service : 2 Job Defs/3 Runs");
			
			ServiceProvisionQuota jobDefQuota = new ServiceProvisionQuota(SERVICEJOBTYPE.REPORTING, SERVICEQUOTARESETTYPE.DAILY,ITEMUNIT.JOBDEFCOUNT, 2.0, null);
			ServiceProvisionQuota jobRunQuota = new ServiceProvisionQuota(SERVICEJOBTYPE.REPORTING, SERVICEQUOTARESETTYPE.DAILY,ITEMUNIT.JOBRUNCOUNT, 3.0, null);
			ServiceProvisionQuota rptPageQuota = new ServiceProvisionQuota(SERVICEJOBTYPE.REPORTING, SERVICEQUOTARESETTYPE.NEVER,ITEMUNIT.REPORTPAGECOUNT, 5.0, null);
			rptService.getQuotas().add(jobDefQuota);
			rptService.getQuotas().add(jobRunQuota);
			rptService.getQuotas().add(rptPageQuota);
			
			rptService = (ReportingServiceProvision)serviceProvisionDAO.add(rptService);			
			*/
			freePlan = new SubscriptionPlan();
			freePlan.setName(ISubscriptionDAOService.FREE_TRIAL_PLAN_NAME);
			freePlan.setCode(ISubscriptionDAOService.FREE_TRIAL_PLAN_NAME);
	    	CalculatableRate rate = new CalculatableRate();
	    	BaseRateCalculator calc = new BaseRateCalculator(ITEMUNIT.STORAGEINMB, 0.0, 0.0);
	    	rate.setCalculator(calc);
	    	rate.setCurrency(stripeRemoteService.getUsd());
	    	freePlan.setRate(rate);		
	    	freePlan.setIntervalType(INTERVALTYPE.MONTHLY);
	    	freePlan.setIntervalCount(1);
			
			ServiceProvisionGroup freePlanSPG = new ServiceProvisionGroup();
			freePlanSPG.setName(freePlan.getName()+" Service Group");
			freePlanSPG.setCode(freePlan.getCode()+" Service Group");
			freePlanSPG = serviceProvisionDAO.addGroup(freePlanSPG);
/*			ServiceProvisionGroupRate sgr = new ServiceProvisionGroupRate();
			BaseRateCalculator brc = new BaseRateCalculator(CALCULATORUNIT.MEGABYTES, 10.0, 0.01);
			CalculatableRate cr = new CalculatableRate();
			cr.setCurrency(currency)*/
			
			/**
			 * 
			 * ETL Service Group
			 * 
			 */
			ServiceProvisionGroupService etlSGS = new ServiceProvisionGroupService();
			etlSGS.setCode(freePlanSPG.getCode()+" ETL");
			etlSGS.setService(etlService);
			etlSGS.setServiceGroup(freePlanSPG);
			freePlanSPG.getServices().add(etlSGS);
			
			/**
			 * 
			 * Reporting Service Group
			 * 
			 */			
/*			ServiceProvisionGroupService rptSGS = new ServiceProvisionGroupService();
			rptSGS.setCode(freePlanSPG.getCode()+" Reporting");
			rptSGS.setService(rptService);
			rptSGS.setServiceGroup(freePlanSPG);
			freePlanSPG.getServices().add(rptSGS);	
			
			
			freePlan.setServiceGroup(freePlanSPG);
			
			freePlan = subscriptionDAO.addPlan(freePlan);*/

			
			//== Create ETL Job Definition
			
			
			/**
			 * == Reporting Service with 5 job limit
			 */
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("",e);
		}	
		
		return freePlan;
	}	

	private Long randomId() {
		Random r = new Random();
		return r.nextLong();
	}
}
