package org.flowframe.bpm.jbpm.ui.pageflow.services;

import java.util.HashMap;

public abstract class BasePageFlowPage implements IPageFlowPage {
	protected HashMap<Class<?>, String> paramKeyMap;
	protected HashMap<Class<?>, String> resultKeyMap;
	
	@Override
	public abstract String getTaskName();
	@Override
	public abstract Class<?> getType();
}
