package org.flowframe.erp.app.contractmanagement.business.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.flowframe.erp.app.contractmanagement.business.services.IContractManagementBusinessServices;
import org.flowframe.erp.app.contractmanagement.dao.customer.ICustomerDAOService;
import org.flowframe.erp.app.contractmanagement.dao.services.ISubscriptionDAOService;
import org.flowframe.erp.app.contractmanagement.domain.Subscription;
import org.flowframe.erp.app.contractmanagement.domain.SubscriptionChange;
import org.flowframe.erp.app.contractmanagement.domain.SubscriptionPlan;
import org.flowframe.erp.app.contractmanagement.type.SUBSCRIPTIONCHANGETYPE;
import org.flowframe.erp.app.contractmanagement.type.SUBSCRIPTIONSTATUS;
import org.flowframe.erp.app.financialmanagement.dao.services.IARReceiptDAOService;
import org.flowframe.erp.app.financialmanagement.domain.payment.CreditCardToken;
import org.flowframe.erp.integration.adaptors.remote.services.payments.ICCRemotePaymentProcessorService;
import org.flowframe.kernel.common.mdm.dao.services.IOrganizationDAOService;
import org.flowframe.kernel.common.mdm.dao.services.user.IUserDAOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Transactional
@Repository
public class ContractManagementBusinessServicesImpl implements IContractManagementBusinessServices {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired(required=false)
	protected PlatformTransactionManager transactionManager;
	
	@Autowired
	protected ICCRemotePaymentProcessorService paymentProcessorService;
	
	@Autowired
	protected IOrganizationDAOService organizationDAOService;	
	
	@Autowired
	private ISubscriptionDAOService subscriptionDAOService;	
	
	@Autowired
	private IARReceiptDAOService receiptDAOService;
	
	@Autowired
	private ICustomerDAOService customerDAOService;
	
	@Autowired
	private IUserDAOService userDAOService;	
	
	@Override
	public Subscription changeSubscription(Long planId, String ppToken, String userEmaillAddress) {
		Subscription ffSubscription = null;
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName("changeSubscriptionById");
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = this.transactionManager.getTransaction(def);
		try {			
			//-- Get plan and current sub
			SubscriptionPlan newPlan = subscriptionDAOService.getPlan(planId);
			ffSubscription = changeSubscription_(ppToken, userEmaillAddress, newPlan);
			
			this.transactionManager.commit(status);	
		} 
		catch (Exception e) {
			this.transactionManager.rollback(status);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		} catch (Error e) {
			this.transactionManager.rollback(status);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}
		
		return ffSubscription;
	}
	
	@Override
	public Subscription changeSubscription(String planName, String ppToken, String userEmaillAddress) {
		Subscription ffSubscription = null;
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName("changeSubscriptionByName");
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = this.transactionManager.getTransaction(def);
		try {			
			//-- Get plan and current sub
			SubscriptionPlan newPlan = subscriptionDAOService.getPlanByCode(planName);
			ffSubscription = changeSubscription_(ppToken, userEmaillAddress, newPlan);
			
			this.transactionManager.commit(status);	
		} 
		catch (Exception e) {
			this.transactionManager.rollback(status);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		} catch (Error e) {
			this.transactionManager.rollback(status);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}
		
		return ffSubscription;
	}	

	private Subscription changeSubscription_(String ppToken, String userEmaillAddress, SubscriptionPlan newPlan) throws Exception {
		Subscription ffSubscription;
		Subscription currentSubscription = subscriptionDAOService.getCurrentSubscriptionByUserEmail(userEmaillAddress);
		logger.info("Retrieved subscription"+currentSubscription+" by email "+userEmaillAddress);
		
		//Update customer payment
		CreditCardToken cct = new CreditCardToken(ppToken);
		paymentProcessorService.updateCustomer(currentSubscription.getCustomer(), cct);
		
		//-- Create subscription
		ffSubscription = new Subscription(newPlan, currentSubscription.getCustomer());
		ffSubscription.setStart(new Date(System.currentTimeMillis()));
		ffSubscription.setCurrentPeriodStart(new Date(System.currentTimeMillis()));
		ffSubscription.setCurrentPeriodEnd(new Date(System.currentTimeMillis()));
		ffSubscription = paymentProcessorService.createSubscription(ffSubscription);
		ffSubscription = subscriptionDAOService.add(ffSubscription);
		
		//-- Create subscription change
		SubscriptionChange sc = new SubscriptionChange(currentSubscription, null, newPlan, SUBSCRIPTIONCHANGETYPE.UPDATE, new Date(), SUBSCRIPTIONSTATUS.ACTIVE);
		sc = subscriptionDAOService.addSubscriptionChange(sc);
		
		//-- Make cuurent inActive
		currentSubscription.setActive(false);
		subscriptionDAOService.update(currentSubscription);
		
		return ffSubscription;
	}

	
}
