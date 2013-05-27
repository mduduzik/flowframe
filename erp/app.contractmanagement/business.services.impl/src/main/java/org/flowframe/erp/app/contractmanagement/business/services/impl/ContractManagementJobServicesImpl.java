package org.flowframe.erp.app.contractmanagement.business.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
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
import org.flowframe.erp.integration.adaptors.stripe.domain.event.Event;
import org.flowframe.erp.integration.adaptors.stripe.services.IEventBusinessService;
import org.flowframe.erp.integration.adaptors.stripe.services.IEventDAOService;
import org.flowframe.kernel.common.mdm.dao.services.IOrganizationDAOService;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
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
	
	//@Autowired
	protected IEventDAOService eventDAOService = null;
	
	//@Autowired
	protected IEventBusinessService eventBusinessService = null;	
	
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
			//BundleContext bundleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
			/*eventDAOService = (IEventDAOService)bundleContext.getServiceReference(IEventDAOService.class);
			OsgiServiceFactoryBean factory = new OsgiServiceFactoryBean();
			factory.setBundleContext(bundleContext);
			factory.setInterfaces(new Class[]{IEventDAOService.class});
			factory.setContextClassLoader(ExportContextClassLoader.SERVICE_PROVIDER);
			factory.afterPropertiesSet();
			eventDAOService = (IEventDAOService)factory.getObject();*/
			
			if (Validator.isNotNull(eventDAOService)) {
				List<Event> createdEvents = eventDAOService.getAllInvoiceEventsCreated();
				if (Validator.isNotNull(createdEvents) && !createdEvents.isEmpty()) {
					logger.info("ContractManagementJobServicesImpl.processNewInvoices "+createdEvents.size()+" new invoices were found");
					//For each: 1) Get invoice from PP 2) Get customer; 3) Find all unbilled job instances for customer
					ARReceipt ffInv = null;
					Subscription ffSub = null;
					ARReceiptLine rl = null;
					Set<ServiceProvisionGroupService> services = null;
					SubscriptionPlan freePlan = subscriptionDAOService.getFreePlan();
					Organization customer = null;
					for (Event evt : createdEvents) {
						ffInv = paymentProcessorService.getInvoice(evt.getObjectId());
						customer = ffInv.getDebtor();
						//customer = organizationDAOService.getByExternalRefId(ffInv.getCustomer());
						if (Validator.isNull(customer)) {//There's NO customer for this
							logger.info("ContractManagementJobServicesImpl.processNewInvoices Customer does not exist for Invoice/"+evt.getObjectId()+" was not found");
							eventBusinessService.markInactive(evt.getStripeId());
							continue;
						}
						else {
							logger.info("ContractManagementJobServicesImpl.processNewInvoices Customer/"+customer.getExternalRefId()+"for Invoice/"+evt.getObjectId()+" was found");
							ffInv.setCode(ffInv.getExternalRefId());
							ffSub = subscriptionDAOService.getByPlanIdAndCustomerId(freePlan.getId(), ffInv.getDebtor().getId());
							logger.info("ContractManagementJobServicesImpl.processNewInvoices Processing Customer/"+customer.getExternalRefId()+"for Invoice/"+evt.getObjectId()+" invoice");
							if (Validator.isNotNull(ffSub)) {//if this is a free plan customer - simply import invoice and add two invoice lines
								logger.info("ContractManagementJobServicesImpl.processNewInvoices Processing Free Plan Customer/"+customer.getExternalRefId()+"for Invoice/"+evt.getObjectId()+" invoice");
								ffInv = receiptDAOService.add(ffInv);
								services = freePlan.getServiceGroup().getServices();
								for (ServiceProvisionGroupService service : services) {
									rl = new ARReceiptLine(service.getService(),null,"Internal",ffInv.getLivemode(),0,ffInv.getCurrency(),true,1,service.getService().getName());
									rl.setReceipt(ffInv);
									ffInv.getLines().add(rl);
								}
								ffInv = receiptDAOService.update(ffInv);
								logger.info("ContractManagementJobServicesImpl.processNewInvoices Processed Free Plan Customer/"+customer.getExternalRefId()+"for Invoice/"+evt.getObjectId()+" invoice successfully");
							}
							eventBusinessService.markInactive(evt.getStripeId());
							logger.info("ContractManagementJobServicesImpl.processNewInvoices Invoice/"+evt.getObjectId()+" for Customer/"+customer.getExternalRefId()+" processed successfully");
						}
					}
				}
				else
				{
					logger.info("ContractManagementJobServicesImpl.processNewInvoices No new invoices were found");					
				}
			}
			else
			{
				logger.info("ContractManagementJobServicesImpl.processNewInvoices eventDAOService not available");
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

	public IEventDAOService getEventDAOService() {
		return eventDAOService;
	}

	public IEventBusinessService getEventBusinessService() {
		return eventBusinessService;
	}
/*
	@Autowired
	public void setEventDAOService(IEventDAOService eventDAOService) {
		logger.info("setEventDAOService...");
		this.eventDAOService = eventDAOService;
	}

	@Autowired
	public void setEventBusinessService(IEventBusinessService eventBusinessService) {
		logger.info("setEventBusinessService...");
		this.eventBusinessService = eventBusinessService;
	}*/
	
	

	public void setEventDAOService(IEventDAOService eventDAOService, Map<String,Object> properties) {
		logger.info("Wiring eventDAOService");
		this.eventDAOService = eventDAOService;
	}
	
	public void unsetEventDAOService(IEventDAOService eventDAOService, Map<String,Object> properties) {
		logger.info("Unwiring eventDAOService");
		this.eventDAOService = eventDAOService;
	}	
	
	public void setEventBusinessService(IEventBusinessService eventBusinessService, Map<String,Object> properties) {
		logger.info("Wiring eventBusinessService");
		this.eventBusinessService = eventBusinessService;
	}
	
	public void unsetEventBusinessService(IEventBusinessService eventBusinessService, Map<String,Object> properties) {
		logger.info("Unwiring eventBusinessService");
		this.eventBusinessService = eventBusinessService;
	}	
}
