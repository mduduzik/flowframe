package org.flowframe.erp.integration.adaptors.stripe;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.flowframe.erp.app.contractmanagement.dao.services.ISubscriptionDAOService;
import org.flowframe.erp.app.contractmanagement.domain.Subscription;
import org.flowframe.erp.app.contractmanagement.domain.SubscriptionPlan;
import org.flowframe.erp.app.contractmanagement.type.INTERVALTYPE;
import org.flowframe.erp.app.contractmanagement.type.SUBSCRIPTIONSTATUS;
import org.flowframe.erp.app.financialmanagement.domain.payment.CreditCardPayment;
import org.flowframe.erp.app.financialmanagement.domain.payment.CreditCardToken;
import org.flowframe.erp.app.financialmanagement.domain.receivable.ARReceipt;
import org.flowframe.erp.app.financialmanagement.domain.receivable.ARReceiptLine;
import org.flowframe.erp.app.mdm.dao.services.ICurrencyUnitDAOService;
import org.flowframe.erp.app.mdm.domain.constants.CurrencyUnitCustomCONSTANTS;
import org.flowframe.erp.app.mdm.domain.currency.CurrencyUnit;
import org.flowframe.erp.integration.adaptors.remote.services.payments.ICCRemotePaymentProcessorService;
import org.flowframe.kernel.common.mdm.dao.services.IOrganizationDAOService;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.flowframe.kernel.common.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stripe.Stripe;
import com.stripe.model.Customer;
import com.stripe.model.Invoice;
import com.stripe.model.InvoiceItem;
import com.stripe.model.InvoiceLineItem;
import com.stripe.model.InvoiceLineItemCollection;
import com.stripe.model.Plan;

@Transactional
@Service
public class StripeServicesImpl extends BaseStripeSONWSServicesImpl implements ICCRemotePaymentProcessorService {
	
	@Autowired
	private ICurrencyUnitDAOService currencyUnitDAOService;
	
	@Autowired
	private ISubscriptionDAOService subscriptionDAOService;
	
	@Autowired
	private IOrganizationDAOService organizationDAOService;	
	
	private CurrencyUnit usd = null;
	
	public void init() {
		super.init();
		usd = currencyUnitDAOService.getByCode(CurrencyUnitCustomCONSTANTS.CURRENCY_USD_CODE);
	}

	public CurrencyUnit getUsd() {
		return usd;
	}

	public void setUsd(CurrencyUnit usd) {
		this.usd = usd;
	}

	/**
	 * 
	 * Customer
	 * 
	 */
	@Override
	public org.flowframe.erp.app.contractmanagement.domain.Customer getCustomer(String serviceId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public org.flowframe.erp.app.contractmanagement.domain.Customer getCustomerByDesription(String description) throws Exception {
		return getAllCustomers().get(description);
	}	

	@Override
	public Map<String,org.flowframe.erp.app.contractmanagement.domain.Customer> getAllCustomers() throws Exception {
		Map<String, Object> listParams = new HashMap<String, Object>();
		listParams.put("count", 1);
		List<Customer> customers = Customer.all(listParams).getData();
		return toFFCustomers(customers);
	}

	@Override
	public org.flowframe.erp.app.contractmanagement.domain.Customer createCustomer(org.flowframe.erp.app.contractmanagement.domain.Customer customerData) throws Exception {
		final Map<String,Object> defaultCardParams = new HashMap<String, Object>();
		defaultCardParams.put("number", getDefaultsCCNumber());
		defaultCardParams.put("exp_month", getDefaultsCCExpMonth());
		defaultCardParams.put("exp_year", getDefaultsCCExpYear());
		defaultCardParams.put("cvc", getDefaultsCCCvc());
		defaultCardParams.put("name", getDefaultsCCName());
		defaultCardParams.put("address_line1", getDefaultsCCAddressLine1());
		defaultCardParams.put("address_line2", getDefaultsCCAddressLine2());
		defaultCardParams.put("address_city", getDefaultsCCAddressCity());
		defaultCardParams.put("address_zip", getDefaultsCCAddressZip());
		defaultCardParams.put("address_state", getDefaultsCCAddressState());
		defaultCardParams.put("address_country", getDefaultsCCAddressCountry());		
	
		Map<String, Object> customerParams = toParamsMap(customerData);
		customerParams.put("card",defaultCardParams);
		
		Customer createdCustomer = Customer.create(customerParams);
		org.flowframe.erp.app.contractmanagement.domain.Customer convertedCustomer = toFFCustomer(createdCustomer);
		convertedCustomer.setExternalRefId(createdCustomer.getId());
		return convertedCustomer;
	}
	
	
	@Override
	public org.flowframe.erp.app.contractmanagement.domain.Customer createCustomerWithPlan(org.flowframe.erp.app.contractmanagement.domain.Customer customerData, SubscriptionPlan planData) throws Exception {
		Plan plan = Plan.retrieve(planData.getExternalRefId());
		Map<String, Object> newCustomerParams = toParamsMap(customerData);
		
		Map<String,Object> defaultTokenParams = new HashMap<String, Object>();
		newCustomerParams.put("card",((CreditCardToken)customerData.getActivePayment()).getToken());
		newCustomerParams.put("plan", plan.getId());
		Customer createdCustomer = Customer.create(newCustomerParams);
		org.flowframe.erp.app.contractmanagement.domain.Customer convertedCustomer = toFFCustomer(createdCustomer);
		convertedCustomer.setExternalRefId(createdCustomer.getId());
		return convertedCustomer;
	}
	
	@Override
	public org.flowframe.erp.app.contractmanagement.domain.Customer updateCustomer(org.flowframe.erp.app.contractmanagement.domain.Customer customerData,
			CreditCardToken cct) throws Exception {
		Customer existingCustomer = Customer.retrieve(customerData.getExternalRefId());
		Map<String, Object> updateParams = new HashMap<String, Object>();
		updateParams.put("card", cct.getToken());
		Customer updatedCustomer = existingCustomer.update(updateParams);	
		cct.setDateUsed(new Date());
		customerData.getPreviousPayments().add(customerData.getActivePayment());
		customerData.setActivePayment(cct);
		return customerData;
	}	

	@Override
	public org.flowframe.erp.app.contractmanagement.domain.Customer cancelCustomerSubscription(org.flowframe.erp.app.contractmanagement.domain.Customer customer) throws Exception {
		Customer createdCustomer = Customer.retrieve(customer.getExternalRefId());
		try {
			createdCustomer.cancelSubscription();
			customer.setSubscription(null);
		} catch (com.stripe.exception.InvalidRequestException e) {
		}
		
		return customer;
	}	
	
	@Override
	public org.flowframe.erp.app.contractmanagement.domain.Customer deleteCustomer(org.flowframe.erp.app.contractmanagement.domain.Customer customer) throws Exception {
		Customer createdCustomer = Customer.retrieve(customer.getExternalRefId());
		createdCustomer.delete();
		return customer;
	}
	
	private Map<String,org.flowframe.erp.app.contractmanagement.domain.Customer> toFFCustomers(List<Customer> customers) {
		Map<String,org.flowframe.erp.app.contractmanagement.domain.Customer> ffCustomers = new HashMap<String,org.flowframe.erp.app.contractmanagement.domain.Customer>();
		for (Customer customer : customers) {
			ffCustomers.put(customer.getDescription(),toFFCustomer(customer));
		}
		return ffCustomers;		
	}
	
	private org.flowframe.erp.app.contractmanagement.domain.Customer toFFCustomer(Customer createdCustomer) {
		org.flowframe.erp.app.contractmanagement.domain.Customer ffCustomer = new org.flowframe.erp.app.contractmanagement.domain.Customer();
		ffCustomer.setCode(createdCustomer.getId());
		ffCustomer.setName(createdCustomer.getDescription());
		ffCustomer.setExternalRefId(createdCustomer.getId());
		ffCustomer.setDateCreated(new Date(createdCustomer.getCreated()));
		ffCustomer.setDelinquent(createdCustomer.getDelinquent());
		
		if (Validator.isNotNull(createdCustomer.getSubscription())) {
			com.stripe.model.Subscription sub = createdCustomer.getSubscription();
			Plan plan = sub.getPlan();
			Subscription ffSub = toFFSubscription(sub);
			SubscriptionPlan ffPlan = toFFSubscriptionPlan(plan);
			ffSub.setSubscribedPlan(ffPlan);
			ffCustomer.setSubscription(ffSub);
		}
		return ffCustomer;
	}

	private Map<String,Object> toParamsMap(org.flowframe.erp.app.contractmanagement.domain.Customer customerData) {
		final Map<String,Object> defaultCardParams = new HashMap<String, Object>();
		defaultCardParams.put("description", customerData.getName());
		return defaultCardParams;
	}
	
	private Map<String,Object> toParamsMap(CreditCardPayment cc) {
		final Map<String,Object> defaultCardParams = new HashMap<String, Object>();
		defaultCardParams.put("number", cc.getNumber());
		defaultCardParams.put("exp_month", cc.getExpMonth());
		defaultCardParams.put("exp_year", cc.getExpYear());
		defaultCardParams.put("cvc", cc.getCvc());
		defaultCardParams.put("name", cc.getHolderContact().getName());
		defaultCardParams.put("address_line1", cc.getHolderAddress().getStreet1());
		defaultCardParams.put("address_line2", cc.getHolderAddress().getStreet2());
		defaultCardParams.put("address_city", cc.getHolderAddress().getUnloco().getPortCity());
		defaultCardParams.put("address_zip", cc.getHolderAddress().getZipCode());
		defaultCardParams.put("address_state", cc.getHolderAddress().getUnloco().getCountryState().getCode());
		defaultCardParams.put("address_country", cc.getHolderAddress().getUnloco().getCountry().getCode());		
	
		return defaultCardParams;
	}	
	
	/**
	 * 
	 * 
	 * Plan
	 * 
	 */
	@Override
	public SubscriptionPlan getSubscriptionPlan(String stripeId) throws Exception {
		return null;
	}

	@Override
	public SubscriptionPlan getSubscriptionPlanByName(String name) throws Exception {
		return getAllSubscriptionPlans().get(name);
	}
	
	@Override
	public Map<String,SubscriptionPlan> getAllSubscriptionPlans() throws Exception {
		Map<String, Object> listParams = new HashMap<String, Object>();
		listParams.put("count", 1);
		List<Plan> plans = Plan.all(listParams).getData();
		return toFFSubscriptionPlans(plans);
	}

	@Override
	public SubscriptionPlan createPlan(SubscriptionPlan planData) throws Exception {
		Plan plan = Plan.create(toParamsMap(planData));
		planData.setExternalRefId(plan.getId());
		return planData;
	}

	@Override
	public SubscriptionPlan deletePlan(SubscriptionPlan plan) throws Exception {
		Plan.retrieve(plan.getExternalRefId());
		return plan;
	}
	
	private Map<String,SubscriptionPlan> toFFSubscriptionPlans(List<Plan> plans) {
		Map<String,SubscriptionPlan> ffPlans = new HashMap<String,SubscriptionPlan>();
		for (Plan plan : plans) {
			ffPlans.put(plan.getName(),toFFSubscriptionPlan(plan));
		}
		return ffPlans;
	}	
	
	private SubscriptionPlan toFFSubscriptionPlan(com.stripe.model.Plan planData) {
		SubscriptionPlan plan = new SubscriptionPlan();
		plan.setCode(planData.getId());
		plan.setExternalRefId(planData.getId());
		plan.setName(planData.getName());
		return plan;
	}
	
	private Map<String,Object> toParamsMap(SubscriptionPlan sp) {
		final Map<String,Object> defaultPlanParams = new HashMap<String, Object>();
		defaultPlanParams.put("id", Long.toString(sp.getId()));
		int amountInCents  = (int)(sp.getRate().calculatePrice(0.0)*100);
		defaultPlanParams.put("amount", amountInCents);
		defaultPlanParams.put("currency", sp.getRate().getCurrency().getCode().toLowerCase());
		
		String interval = null;
		if (sp.getIntervalType() == INTERVALTYPE.WEEKLY)
			interval = "week";
		else if (sp.getIntervalType() == INTERVALTYPE.MONTHLY)
			interval = "month";
		else
			interval = "year";
		defaultPlanParams.put("interval", interval);
		defaultPlanParams.put("interval_count", sp.getIntervalCount());
		defaultPlanParams.put("name", sp.getName());
		
		return defaultPlanParams;
	}			
	
	/**
	 * 
	 * 
	 * Subscription
	 * 
	 * 
	 */
	@Override
	public Subscription getSubscription(String serviceId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Subscription> getAllSubscriptions() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subscription createSubscription(Subscription subData) throws Exception {
		Customer customer = Customer.retrieve(subData.getCustomer().getExternalRefId());
		Map<String, Object> subscriptionParams = new HashMap<String, Object>();
		subscriptionParams.put("plan", subData.getSubscribedPlan().getExternalRefId());
		com.stripe.model.Subscription sub = customer.updateSubscription(subscriptionParams);
		subData = toFFSubscription(sub);
		return subData;
	}

	private Subscription toFFSubscription(com.stripe.model.Subscription subData) {
		SubscriptionPlan plan = subscriptionDAOService.getPlanByExternalRefId(subData.getPlan().getId());
		org.flowframe.erp.app.contractmanagement.domain.Customer customer = (org.flowframe.erp.app.contractmanagement.domain.Customer)organizationDAOService.getByExternalRefId(subData.getCustomer());
		Subscription ffSubscription = new Subscription(plan,customer);
		ffSubscription.setStart(new Date(subData.getStart()));
		SUBSCRIPTIONSTATUS status = SUBSCRIPTIONSTATUS.ACTIVE;
		if ("trialing".equals(subData.getStatus())) {
			status = SUBSCRIPTIONSTATUS.TRAILING;
		}
		else if ("past_due".equals(subData.getStatus())) {
			status = SUBSCRIPTIONSTATUS.PASTDUE;			
		}		
		else if ("canceled".equals(subData.getStatus())) {
			status = SUBSCRIPTIONSTATUS.CANCELLED;
		}	
		else if ("unpaid".equals(subData.getStatus())) {
			status = SUBSCRIPTIONSTATUS.UNPAID;
		}	
		ffSubscription.setStatus(status);
		if (Validator.isNotNull(subData.getCancelAtPeriodEnd()))
			ffSubscription.setCancelAtPeriodEnd(subData.getCancelAtPeriodEnd());
		if (Validator.isNotNull(subData.getCurrentPeriodStart()))
			ffSubscription.setCurrentPeriodStart(new Date(subData.getCurrentPeriodStart()));
		if (Validator.isNotNull(subData.getCurrentPeriodEnd()))
			ffSubscription.setCurrentPeriodEnd(new Date(subData.getCurrentPeriodEnd()));
		if (Validator.isNotNull(subData.getEndedAt()))
		 ffSubscription.setEndedAt(new Date(subData.getEndedAt()));
		if (Validator.isNotNull(subData.getTrialStart()))
			ffSubscription.setTrialStart(new Date(subData.getTrialStart()));
		if (Validator.isNotNull(subData.getTrialEnd()))
			ffSubscription.setTrialEnd(new Date(subData.getTrialEnd()));
		if (Validator.isNotNull(subData.getCanceledAt()))
			ffSubscription.setCancelAt(new Date(subData.getCanceledAt()));
		ffSubscription.setQuantity(subData.getQuantity());
		
		return ffSubscription;
	}

	@Override
	public Subscription deleteSubscription(Subscription sub) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Map<String,Object> toParamsMap(Subscription sub) {
		final Map<String,Object> defaultSubParams = new HashMap<String, Object>();
		defaultSubParams.put("plan", sub.getSubscribedPlan().getExternalRefId());
		defaultSubParams.put("start", sub.getStart().getTime());
		defaultSubParams.put("current_period_start", sub.getCurrentPeriodStart().getTime());
		defaultSubParams.put("current_period_end", sub.getCurrentPeriodEnd().getTime());
		defaultSubParams.put("trial_end", sub.getTrialEnd());
		defaultSubParams.put("customer", sub.getCustomer().getExternalRefId());

		return defaultSubParams;
	}		
	
	/**
	 * 
	 * 
	 * Invoices
	 * 
	 * 
	 */	

	@Override
	public ARReceipt getInvoice(String invoiceId) throws Exception {
		Invoice inv = Invoice.retrieve(invoiceId);
		return toFFARReceipt(inv);
	}

	@Override
	public Set<ARReceipt> getAllInvoices() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ARReceipt createInvoice(ARReceipt invoice) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ARReceipt deleteInvoice(ARReceipt invoice) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ARReceiptLine getInvoiceLine(String invoiceLineId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ARReceiptLine> getAllInvoiceLines(String invoiceId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ARReceiptLine createInvoiceLine(ARReceiptLine invoiceLine) throws Exception {
		return null;
	}

	@Override
	public ARReceiptLine deleteInvoiceLine(ARReceiptLine invoiceLine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}	
	
	private ARReceipt toFFARReceipt(Invoice invoice) {
		Organization cust = organizationDAOService.getByExternalRefId(invoice.getCustomer());
		ARReceipt invoice_ = new ARReceipt();
		
		invoice_.setDebtor(cust);
		invoice_.setCurrency(usd);
		invoice_.setSubtotal(invoice.getSubtotal());
		invoice_.setTotal(invoice.getTotal());
		invoice_.setAmountDue(invoice.getAmountDue());
		invoice_.setStartingBalance(invoice.getStartingBalance());
		invoice_.setEndingBalance(invoice.getEndingBalance());
		invoice_.setExternalCode(invoice.getId());
		if (Validator.isNotNull(invoice.getNextPaymentAttempt()))
			invoice_.setNextPaymentAttempt(new Date(invoice.getNextPaymentAttempt()));
		invoice_.setAttempted(invoice.getAttempted());
		invoice_.setCharge(invoice.getCharge());
		invoice_.setClosed(invoice.getClosed());
		if (Validator.isNotNull(invoice.getDate()))
			invoice_.setDate(new Date(invoice.getDate()));
		invoice_.setPaid(invoice.getPaid());
		if (Validator.isNotNull(invoice.getPeriodStart()))
			invoice_.setPeriodStart(new Date(invoice.getPeriodStart()));
		if (Validator.isNotNull(invoice.getPeriodEnd()))
			invoice_.setPeriodEnd(new Date(invoice.getPeriodEnd()));
		invoice_.setLivemode(invoice_.getLivemode());
		invoice_.setAttemptCount(invoice.getAttemptCount());
		
		List<InvoiceLineItem> lines = invoice.getLines().getData();
		ARReceiptLine line_;
		for (InvoiceLineItem line : lines) {
			line_ = toFFARReceiptLine(invoice_, line);
			invoice_.getLines().add(line_);
		}
		
		return invoice_;
	}	
	
	private ARReceiptLine toFFARReceiptLine(ARReceipt invoice, InvoiceLineItem invoiceItem) {
		String description = null;
		if (Validator.isNotNull(invoiceItem.getPlan())) {
			description = invoiceItem.getPlan().getName();
		}
		ARReceiptLine invoiceLine_ = new ARReceiptLine(null, invoiceItem.getId(), null, false, invoiceItem.getAmount(), usd, false, 1, description);
		invoiceLine_.setSynchonized(true);
		return invoiceLine_;
	}
	
	private Map<String,Object> toParamsMap(ARReceiptLine line) {
		ARReceipt rcpt = (ARReceipt)line.getReceipt();
		final Map<String,Object> invoiceItemParams = new HashMap<String, Object>();
		invoiceItemParams.put("amount", line.getAmount());
		invoiceItemParams.put("currency", rcpt.getCurrency().getCode().toLowerCase());
		invoiceItemParams.put("customer", rcpt.getDebtor().getExternalRefId());
		invoiceItemParams.put("description", line.getDescription());
		invoiceItemParams.put("invoice", rcpt.getExternalRefId());
		
		return invoiceItemParams;
	}	
}