package org.flowframe.ui.services.contribution;

import java.util.Map;

import org.flowframe.ui.component.domain.AbstractComponent;

public interface IComponentModelViewContribution extends IViewContribution {
	public AbstractComponent getComponentModel(Map<String, Object> properties);
}