package org.flowframe.bpm.jbpm.ui.pageflow.services;

import java.util.Map;

import org.vaadin.teemu.wizards.WizardStep;

public interface IPageComponent extends WizardStep {
	public static final String CONX_ENTITY_MANAGER_FACTORY = "CONX_ENTITY_MANAGER_FACTORY";
	public static final String JTA_GLOBAL_TRANSACTION_MANAGER = "JTA_GLOBAL_TRANSACTION_MANAGER";
	public static final String ENTITY_CONTAINER_PROVIDER = "ENTITY_CONTAINER_PROVIDER";
	public static final String TASK_WIZARD = "TASK_WIZARD";
	public static final String PAGE_FLOW_PAGE_CHANGE_EVENT_HANDLER = "PAGE_FLOW_PAGE_CHANGE_EVENT_HANDLER";
	public static final String MVP_PRESENTER_FACTORY = "MVP_PRESENTER_FACTORY";
	public static final String ENTITY_TYPE_DAO_SERVICE = "ENTITY_TYPE_DAO_SERVICE";
	public static final String FOLDER_DAO_SERVICE = "FOLDER_DAO_SERVICE";
	public static final String REMOTE_DOCUMENT_REPOSITORY = "REMOTE_DOCUMENT_REPOSITORY";
	public static final String OWNER_PAGE_PRESENTER = "OWNER_PAGE_PRESENTER";
	public static final String EVENT_BUS_MANAGER = "OWNER_PAGE_PRESENTER";
	public static final String DAO_PROVIDER = "DAO_PROVIDER";
	
	public void init(Map<String, Object> initParams);
	public void setParameterData(Map<String, Object> params);
	public Object getResultData();
	public boolean isExecuted();
	public void setExecuted(boolean executed);
}
