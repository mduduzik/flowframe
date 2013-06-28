package org.flowframe.erp.app.contractmanagement.business.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.ws.WebServiceRef;

import org.flowframe.erp.app.contractmanagement.business.services.IContractManagementJobServices;
import org.flowframe.erp.app.contractmanagement.dao.services.ISubscriptionDAOService;
import org.flowframe.erp.app.contractmanagement.domain.Subscription;
import org.flowframe.erp.app.contractmanagement.domain.SubscriptionPlan;
import org.flowframe.erp.app.contractmanagement.domain.service.ServiceProvisionGroupService;
import org.flowframe.erp.app.financialmanagement.dao.services.IARReceiptDAOService;
import org.flowframe.erp.app.financialmanagement.domain.receivable.ARReceipt;
import org.flowframe.erp.app.financialmanagement.domain.receivable.ARReceiptLine;
import org.flowframe.erp.integration.adaptors.remote.services.payments.ICCRemotePaymentProcessorService;
import org.flowframe.erp.integration.adaptors.services.soap.IEventBusinessServicePortType;
import org.flowframe.erp.integration.adaptors.services.soap.IEventDAOServicePortType;
import org.flowframe.erp.integration.adaptors.stripe.domain.event.Event;
import org.flowframe.erp.integration.adaptors.stripe.services.IEventBusinessService;
import org.flowframe.erp.integration.adaptors.stripe.services.IEventDAOService;
import org.flowframe.kernel.common.mdm.dao.services.IOrganizationDAOService;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.flowframe.kernel.common.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("http://${ff.erp.integration.adaptors.stripe.webhook.hostname}:${ff.erp.integration.adaptors.stripe.webhook.port}/cxf/org/flowframe/erp/integration/adaptors/stripe/services/IEventDAOService?wsdl")
	private String eventDAOServiceURL;
	protected IEventDAOService eventDAOService = null;	
	protected org.flowframe.erp.integration.adaptors.services.soap.IEventDAOService eventDAORemoteService;
	protected IEventDAOServicePortType eventDAORemoteServicePort;

	
	@Value("http://${ff.erp.integration.adaptors.stripe.webhook.hostname}:${ff.erp.integration.adaptors.stripe.webhook.port}/cxf/org/flowframe/erp/integration/adaptors/stripe/services/IEventBusinessService?wsdl")
	private String eventBusinessServiceURL;	
	protected IEventBusinessService eventBusinessService = null;	
	protected org.flowframe.erp.integration.adaptors.services.soap.IEventBusinessService eventBusinessRemoteService;	
	protected IEventBusinessServicePortType eventBusinessRemoteServicePort;
	
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
	
	public void init(){
		try {
			eventDAORemoteService = new org.flowframe.erp.integration.adaptors.services.soap.IEventDAOService(new URL(eventDAOServiceURL));
			eventDAORemoteServicePort = eventDAORemoteService.getIEventDAOServicePort();
			eventBusinessRemoteService = new org.flowframe.erp.integration.adaptors.services.soap.IEventBusinessService(new URL(eventBusinessServiceURL));
			eventBusinessRemoteServicePort = eventBusinessRemoteService.getIEventBusinessServicePort();
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
			
			throw new IllegalArgumentException("Error creating service", e);
		} catch (Error e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
			
			throw new IllegalArgumentException("Error creating service", e);
		}
	}

	@Override
	public void processNewInvoices() {
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
					DefaultTransactionDefinition def = new DefaultTransactionDefinition();
					def.setName("RegistrationBusinessServiceImpl.processNewInvoices");
					def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
					TransactionStatus status = this.transactionManager.getTransaction(def);
					try
					{
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
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		} catch (Error e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}
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

	public org.flowframe.erp.integration.adaptors.services.soap.IEventDAOService getEventDAORemoteService() {
		return eventDAORemoteService;
	}

	public void setEventDAORemoteService(org.flowframe.erp.integration.adaptors.services.soap.IEventDAOService eventDAORemoteService) {
		this.eventDAORemoteService = eventDAORemoteService;
		logger.info("Injected eventDAORemoteService");
	}

	public org.flowframe.erp.integration.adaptors.services.soap.IEventBusinessService getEventBusinessRemoteService() {
		return eventBusinessRemoteService;
	}

	public void setEventBusinessRemoteService(org.flowframe.erp.integration.adaptors.services.soap.IEventBusinessService eventBusinessRemoteService) {
		this.eventBusinessRemoteService = eventBusinessRemoteService;
		logger.info("Injected eventBusinessRemoteService");
	}	
}
