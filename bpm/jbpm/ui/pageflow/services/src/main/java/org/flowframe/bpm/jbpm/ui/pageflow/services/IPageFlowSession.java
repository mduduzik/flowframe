package org.flowframe.bpm.jbpm.ui.pageflow.services;

import java.util.Collection;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.flowframe.bpm.jbpm.services.IBPMProcessInstance;
import org.flowframe.kernel.common.mdm.domain.application.Feature;
import org.springframework.transaction.PlatformTransactionManager;

import com.vaadin.ui.Component;

public interface IPageFlowSession {
	public IBPMProcessInstance getBPMProcessInstance();
	public Collection<IPageFlowPage> getPages();
	public Component getWizardComponent();
	public Feature getOnCompletionFeature();
	public void nextPage();
	public void previousPage();
	public void abort();
	public void start();
	public Map<String, Object> getProcessVars();
	public EntityManagerFactory getConXEntityManagerfactory();
	public PlatformTransactionManager getJTAGlobalTransactionManager();
	public IPageFlowManager getPageFlowEngine();
	public boolean isOnLastPage();
	
	public void completeProcess(UserTransaction userTransaction, Object data) throws Exception;
	public boolean getNextTaskAndUpdatePagesPath(UserTransaction ut) throws SystemException, Exception;	
	public boolean executeNext(UserTransaction userTransaction, Object data) throws Exception;
	public Map<String, Object> updateProcessInstanceVariables(
			UserTransaction userTransaction, Map<String, Object> varsToUpdate) throws Exception;
}