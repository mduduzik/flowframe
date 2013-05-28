package org.flowframe.ui.services.factory;

import java.util.Map;

import org.flowframe.ui.component.domain.AbstractComponent;

public interface IComponentModelToPresenterFactoryContribution {
	public Object create(AbstractComponent component, Map<String,Object> params);
	public Boolean canCreate(AbstractComponent component);
}
