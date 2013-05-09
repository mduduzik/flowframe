package org.flowframe.erp.app.contractmanagement.business.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.flowframe.erp.app.contractmanagement.business.services.IContractManagementJobServices;
import org.flowframe.erp.app.contractmanagement.dao.services.ISubscriptionDAOService;
import org.flowframe.erp.app.contractmanagement.domain.Subscription;
import org.flowframe.erp.app.contractmanagement.domain.SubscriptionPlan;
import org.flowframe.erp.app.contractmanagement.domain.service.ServiceProvisionGroupService;
import org.flowframe.erp.app.financialmanagement.dao.services.IARReceiptDAOService;
import org.flowframe.erp.app.financialmanagement.domain.receivable.ARReceipt;
import org.flowframe.erp.app.financialmanagement.domain.receivable.ARReceiptLine;
import org.flowframe.erp.integration.adaptors.remote.services.payments.ICCRemotePaymentProcessorService;
import org.flowframe.erp.integration.adaptors.stripe.services.Event;
import org.flowframe.erp.integration.adaptors.stripe.services.IEventBusinessServicePortType;
import org.flowframe.erp.integration.adaptors.stripe.services.IEventDAOServicePortType;
import org.flowframe.kernel.common.mdm.dao.services.IOrganizationDAOService;
import org.flowframe.kernel.common.utils.Validator;
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
public class ContractManagementJobServicesImpl implements IContractManagementJobServices {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired(required=false)
	protected PlatformTransactionManager transactionManager;
	
	@Autowired
	protected IEventDAOServicePortType eventDAOService;
	
	@Autowired
	protected IEventBusinessServicePortType eventBusinessService;	
	
	@Autowired
	protected ICCRemotePaymentProcessorService paymentProcessorService;
	
	@Autowired
	protected IOrganizationDAOService organizationDAOService;	
	
	@Autowired
	private ISubscriptionDAOService subscriptionDAOService;	
	
	@Autowired
	private IARReceiptDAOService receiptDAOService;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void processNewInvoices() {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName("RegistrationBusinessServiceImpl.processNewInvoices");
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = this.transactionManager.getTransaction(def);
		try {		
			logger.info("ContractManagementJobServicesImpl.processNewInvoices...");
			
			//1. Check if Stripe has invoice.created
			List<Event> createdEvents = eventDAOService.getAllInvoiceEventsCreated();
			if (!createdEvents.isEmpty()) {
				//For each: 1) Get invoice from PP 2) Get customer; 3) Find all unbilled job instances for customer
				ARReceipt ffInv = null;
				Subscription ffSub = null;
				ARReceiptLine rl = null;
				Set<ServiceProvisionGroupService> services = null;
				SubscriptionPlan freePlan = subscriptionDAOService.getFreePlan();
				for (Event evt : createdEvents) {
					ffInv = paymentProcessorService.getInvoice(evt.getObjectId());
					ffSub = subscriptionDAOService.getByPlanIdAndCustomerId(freePlan.getId(), ffInv.getDebtor().getId());
					if (Validator.isNotNull(ffSub)) {//if this is a free plan customer - simply import invoice and add two invoice lines
						ffInv = receiptDAOService.provide(ffInv);
						services = freePlan.getServiceGroup().getServices();
						for (ServiceProvisionGroupService service : services) {
							rl = new ARReceiptLine(ffInv,service.getService(),null,"Internal",ffInv.getLivemode(),0,ffInv.getCurrency(),true,1,service.getService().getName());
							ffInv.getLines().add(rl);
						}
					}
					eventBusinessService.markInactive(evt.getStripeId());
				}
			}
		} catch (Exception e) {
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
		this.transactionManager.commit(status);
	}

	public IEventDAOServicePortType getEventDAOService() {
		return eventDAOService;
	}

	public IEventBusinessServicePortType getEventBusinessService() {
		return eventBusinessService;
	}
	
}
