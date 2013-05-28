package org.flowframe.ui.services.contribution;

import java.util.Map;

import org.flowframe.ui.component.domain.AbstractComponent;

public interface IComponentFactoryContributionManager {
	public Object create(AbstractComponent component, Map<String,Object> params);
}
