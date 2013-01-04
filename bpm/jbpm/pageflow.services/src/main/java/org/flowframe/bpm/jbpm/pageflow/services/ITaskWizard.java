package org.flowframe.bpm.jbpm.pageflow.services;

import java.util.Map;

import org.flowframe.kernel.common.mdm.domain.application.Feature;

public interface ITaskWizard extends IPageFlowListener {
	public Object getComponent();
	public Feature getOnCompletionFeature();
	public Map<String,Object> getProperties();
	public void setNextEnabled(boolean isEnabled);
	public boolean isNextEnabled();
	public void setBackEnabled(boolean isEnabled);
	public boolean isBackEnabled();
}
