package org.flowframe.ui.vaadin.editors.builder.vaadin.contribution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.services.contribution.IComponentFactoryContribution;
import org.flowframe.ui.services.contribution.IComponentFactoryContributionManager;
import org.flowframe.ui.services.factory.IComponentModelToPresenterFactoryContribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComponentFactoryContributionManager  implements IComponentFactoryContributionManager {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final List<IComponentModelToPresenterFactoryContribution> factoryContributions = Collections.synchronizedList(new ArrayList<IComponentModelToPresenterFactoryContribution>());
	
	public void registerComponentFactoryContribution(IComponentModelToPresenterFactoryContribution factoryContribution, Map<String, Object> properties) {
		logger.debug("registerComponentFactoryContribution(" + factoryContribution + ")");
		factoryContributions.add(factoryContribution);
	}

	public void unregisterComponentFactoryContribution(IComponentModelToPresenterFactoryContribution factoryContribution, Map<String, Object> properties) {
		logger.debug("unregisterComponentFactoryContribution(" + factoryContribution + ")");
		factoryContributions.remove(factoryContribution);
	}

	@Override
	public Object create(AbstractComponent component, Map<String, Object> params) {
		for ( IComponentModelToPresenterFactoryContribution factoryContribution : factoryContributions) {
			if (factoryContribution.canCreate(component)) {
				return factoryContribution.create(component, params);
			}
		}
		throw new IllegalArgumentException("No IComponentModelToPresenterFactoryContribution was found to create Component("+component+")");
	}
}