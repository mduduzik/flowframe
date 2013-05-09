package org.flowframe.erp.integration.adaptors.remote.services.payments;

import java.util.Map;
import java.util.Set;

import org.flowframe.erp.app.contractmanagement.domain.Customer;
import org.flowframe.erp.app.contractmanagement.domain.Subscription;
import org.flowframe.erp.app.contractmanagement.domain.SubscriptionPlan;
import org.flowframe.erp.app.financialmanagement.domain.payment.CreditCardToken;
import org.flowframe.erp.app.financialmanagement.domain.receivable.ARReceipt;
import org.flowframe.erp.app.financialmanagement.domain.receivable.ARReceiptLine;


public interface ICCRemotePaymentProcessorService extends IRemotePaymentProcessorService {
	/**
	 * Customer
	 */
	public Customer getCustomer(String externalId) throws Exception;	
	
	public Customer getCustomerByDesription(String description) throws Exception;	
	
	public Map<String, Customer> getAllCustomers() throws Exception;	
	
	public Customer createCustomer(Customer customerData) throws Exception;
	
	public Customer createCustomerWithPlan(Customer customerData, SubscriptionPlan planData) throws Exception;	
	
	public Customer updateCustomer(Customer customerData, CreditCardToken cct) throws Exception;
	
	public Customer deleteCustomer(Customer customer) throws Exception;	
	
	public Customer cancelCustomerSubscription(Customer customer) throws Exception;
	
	
	/**
	 * Subscription Plan
	 */	
	public SubscriptionPlan getSubscriptionPlan(String externalId) throws Exception;	
	
	public SubscriptionPlan getSubscriptionPlanByName(String name) throws Exception;		
	
	public Map<String, SubscriptionPlan> getAllSubscriptionPlans() throws Exception;	
	
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
	public ARReceipt getInvoice(String invoiceId) throws Exception;	
	
	public Set<ARReceipt> getAllInvoices() throws Exception;	
	
	public ARReceipt createInvoice(ARReceipt invoice) throws Exception;
	
	public ARReceipt deleteInvoice(ARReceipt invoice) throws Exception;		
	
	public ARReceiptLine getInvoiceLine(String invoiceLineId) throws Exception;	
	
	public Set<ARReceiptLine> getAllInvoiceLines(String invoiceId) throws Exception;	
	
	public ARReceiptLine createInvoiceLine(ARReceiptLine invoiceLine) throws Exception;
	
	public ARReceiptLine deleteInvoiceLine(ARReceiptLine invoiceLine) throws Exception;
}
