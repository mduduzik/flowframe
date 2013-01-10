package org.flowframe.bpm.jbpm.ui.pageflow.services;


import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

import org.flowframe.bpm.jbpm.services.IBPMService;
import org.flowframe.kernel.common.mdm.domain.task.TaskDefinition;
import org.flowframe.kernel.metamodel.dao.services.IEntityTypeDAOService;
import org.springframework.transaction.PlatformTransactionManager;

public interface IPageFlowManager {
	/**
	 * Services:
	 */
	/**
	 * Given a TaskDefinition and UserId, createPageFlowSession
	 */
	public IPageFlowSession startPageFlowSession(String userid, TaskDefinition td);
	/**
	 * Given a TaskDefinition and UserId, createPageFlowSession
	 */
	public ITaskWizard createTaskWizard(Map<String, Object> properties) throws Exception;
	/**
	 * Given a TaskDefinition and UserId, createPageFlowSession
	 */
	public ITaskWizard executeTaskWizard(ITaskWizard tw, Object data) throws Exception;	
	
	/**
	 * 
	 */
	public ITaskWizard resumeProcessInstanceTaskWizard(String processInstanceId,Map<String, Object> properties) throws Exception;
	
	/**
	 * Given a TaskDefinition and UserId, pageFlowSessionId
	 */
	public IPageFlowSession closePageFlowSession(String userid, Long pageFlowSessionId);	
	/**
	 * Given a TaskDefinition and UserId, pageFlowSessionId
	 */
	public IPageFlowSession resumePageFlowSession(String userid, Long pageFlowSessionId);
	/**
	 * Update process instance variables
	 */
	public Map<String,Object> updateProcessInstanceVariables(ITaskWizard tw, Map<String,Object> varsToUpdate) throws Exception;		
	
	
	/**
	 * Other
	 */
	public EntityManagerFactory getConXEntityManagerfactory();
	public PlatformTransactionManager getJTAGlobalTransactionManager();
	public IBPMService getBPMService();
	public IEntityTypeDAOService getEntityTypeDAOService();
	public UserTransaction getUserTransaction();
}