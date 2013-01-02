package org.flowframe.bpm.jbpm.pageflow.services;

import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.flowframe.bpm.jbpm.pageflow.event.IPageFlowPageChangedEventHandler;
import org.springframework.transaction.PlatformTransactionManager;

import com.conx.logistics.kernel.persistence.services.IEntityContainerProvider;
import com.vaadin.ui.Component;

public abstract class BasePageComponent implements IPageComponent {
	private boolean executed;
	private IPageFlowPage page;
	private Component content;
	
	private EntityManagerFactory emf; 
	private PlatformTransactionManager ptm;
	private IPageFlowPageChangedEventHandler pfpEventHandler; 
	private ITaskWizard wizard;
	private IEntityContainerProvider containerProvider;
	
	public BasePageComponent(IPageFlowPage page) {
		this.page = page;
		this.executed = false;
	}
	
	public BasePageComponent(IPageFlowPage page, Component content) {
		this(page);
		this.content = content;
	}

	@Override
	public String getCaption() {
		return this.page.getTaskName();
	}

	@Override
	public Component getContent() {
		return this.content;
	}
	
	public void setContent(Component component) {
		this.content = component;
	}

	@Override
	public boolean onAdvance() {
		return true;
	}

	@Override
	public boolean onBack() {
		return true;
	}

	@Override
	public void init(Map<String, Object> initParams) {
		this.emf = (EntityManagerFactory) initParams.get(IPageComponent.CONX_ENTITY_MANAGER_FACTORY);
		this.ptm = (PlatformTransactionManager) initParams.get(IPageComponent.JTA_GLOBAL_TRANSACTION_MANAGER);
		this.pfpEventHandler = (IPageFlowPageChangedEventHandler) initParams.get(IPageComponent.PAGE_FLOW_PAGE_CHANGE_EVENT_HANDLER);
		this.wizard = (ITaskWizard) initParams.get(IPageComponent.TASK_WIZARD);
		this.containerProvider = (IEntityContainerProvider) initParams.get(IPageComponent.ENTITY_CONTAINER_PROVIDER);
	}

	@Override
	public abstract void setParameterData(Map<String, Object> params);

	@Override
	public abstract Map<String, Object> getResultData();

	@Override
	public boolean isExecuted() {
		return executed;
	}

	@Override
	public void setExecuted(boolean executed) {
		this.executed = executed;
	}

	public IPageFlowPage getPage() {
		return page;
	}

	protected PlatformTransactionManager getPlatformTransactionManager() {
		return ptm;
	}

	protected IPageFlowPageChangedEventHandler getPageFlowPageEventHandler() {
		return pfpEventHandler;
	}

	protected ITaskWizard getWizard() {
		return wizard;
	}

	protected IEntityContainerProvider getContainerProvider() {
		return containerProvider;
	}
	
	protected EntityManagerFactory getEntityEditorFactory() {
		return this.emf;
	}
}
