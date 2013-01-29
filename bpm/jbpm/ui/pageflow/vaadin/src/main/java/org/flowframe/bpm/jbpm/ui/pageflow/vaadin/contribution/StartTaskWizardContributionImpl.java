package org.flowframe.bpm.jbpm.ui.pageflow.vaadin.contribution;

import org.flowframe.bpm.jbpm.ui.pageflow.services.IPageFlowManager;
import org.flowframe.ui.services.contribution.ITaskViewContribution;

public class StartTaskWizardContributionImpl implements ITaskViewContribution {
	
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
	public String getCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessId() {
		// TODO Auto-generated method stub
		return null;
	}

}
