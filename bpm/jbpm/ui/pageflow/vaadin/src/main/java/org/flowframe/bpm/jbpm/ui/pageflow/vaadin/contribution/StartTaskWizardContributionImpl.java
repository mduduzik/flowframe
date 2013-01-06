package org.flowframe.bpm.jbpm.ui.pageflow.vaadin.contribution;

import java.util.Map;

import org.flowframe.bpm.jbpm.ui.pageflow.services.IPageFlowManager;
import org.flowframe.ui.services.contribution.ITaskActionContribution;

import com.vaadin.Application;
import com.vaadin.ui.Component;

public class StartTaskWizardContributionImpl implements ITaskActionContribution {
	
	private IPageFlowManager pageFlowservice;

	public void setDefaultPageFlowEngine(IPageFlowManager pageFlowservice) {
		this.pageFlowservice = pageFlowservice;
	}

	@Override
	public String getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(Application application) {
		// TODO Auto-generated method stub

	}

	@Override
	public Component execute(Application application,
			Map<String, Object> properties) throws Exception {
		return (Component)pageFlowservice.createTaskWizard(properties);
	}

}
