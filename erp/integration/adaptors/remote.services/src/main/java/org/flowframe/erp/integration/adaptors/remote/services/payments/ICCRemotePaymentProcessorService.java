package org.flowframe.erp.integration.adaptors.remote.services.payments;

import java.util.Set;

import org.flowframe.erp.app.contractmanagement.domain.Customer;
import org.flowframe.erp.app.contractmanagement.domain.Subscription;
import org.flowframe.erp.app.contractmanagement.domain.SubscriptionPlan;
import org.flowframe.erp.app.financialmanagement.domain.payment.CreditCardToken;


public interface ICCRemotePaymentProcessorService extends IRemotePaymentProcessorService {
	/**
	 * Customer
	 */
	public Customer getCustomer(String serviceId) throws Exception;	
	
	public Set<Customer> getAllCustomers() throws Exception;	
	
	public Customer createCustomer(Customer customerData) throws Exception;
	
	public Customer createCustomerWithPlan(Customer customerData, SubscriptionPlan planData) throws Exception;	
	
	public Customer updateCustomer(Customer customerData, CreditCardToken cct) throws Exception;
	
	public Customer deleteCustomer(Customer customer) throws Exception;	
	
	
	/**
	 * Subscription Plan
	 */	
	public SubscriptionPlan getSubscriptionPlan(String serviceId) throws Exception;	
	
	public Set<SubscriptionPlan> getAllSubscriptionPlans() throws Exception;	
	
	public SubscriptionPlan createPlan(SubscriptionPlan planData) throws Exception;
	
	public SubscriptionPlan deletePlan(SubscriptionPlan plan) throws Exception;		
	
	
	/**
	 * Subscription
	 */	
	public Subscription getSubscription(String serviceId) throws Exception;	
	
	public Set<Subscription> getAllSubscriptions() throws Exception;	
	
	public Subscription createSubscription(Subscription subData) throws Exception;
	
	public Subscription deleteSubscription(Subscription sub) throws Exception;
	
	
	
	/**
	 * Invoice
	 */		
}
