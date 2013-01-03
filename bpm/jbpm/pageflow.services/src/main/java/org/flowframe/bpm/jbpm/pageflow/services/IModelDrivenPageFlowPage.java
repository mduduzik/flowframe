package org.flowframe.bpm.jbpm.pageflow.services;

import org.flowframe.ui.component.domain.page.TaskPageComponent;

public interface IModelDrivenPageFlowPage extends IPageFlowPage {
	public TaskPageComponent getComponentModel();
}