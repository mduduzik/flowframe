package org.flowframe.ui.pageflow.services;

import java.util.Map;

import org.flowframe.kernel.common.mdm.domain.application.Feature;

import com.vaadin.ui.Component;

public interface ITaskWizard extends IPageFlowListener {
	public Component getComponent();
	public Feature getOnCompletionFeature();
	public Map<String,Object> getProperties();
	public void setNextEnabled(boolean isEnabled);
	public boolean isNextEnabled();
	public void setBackEnabled(boolean isEnabled);
	public boolean isBackEnabled();
}
