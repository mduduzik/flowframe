package org.flowframe.erp.app.contractmanagement.business.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.flowframe.erp.app.contractmanagement.business.services.IContractManagementJobServices;
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
	@Autowired
	protected PlatformTransactionManager transactionManager;
	
	
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
}
