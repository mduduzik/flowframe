package org.flowframe.bpm.jbpm.ui.pageflow.services;

import java.util.Map;

public interface IPageFlowPage {
	public static final String PROCESS_ID = "PROCESS_ID";
	public static final String TASK_NAME = "TASK_NAME";
	
	public String getTaskName();
	
	public Class<?> getType();
	
	public Map<Class<?>, String> getParamKeyMap();
	
	public Map<Class<?>, String> getResultKeyMap();
}
