package org.flowframe.ui.vaadin.converters.manager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.vaadin.converters.services.IComponentModelConverter;
import org.flowframe.ui.vaadin.converters.services.IComponentModelConverterContributions;
import org.flowframe.ui.vaadin.converters.services.IComponentModelConverterContributionsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComponentFactoryContributionManager  implements IComponentModelConverterContributionsManager {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final Map<Class,IComponentModelConverter>  componentFactoryContributionsMap = Collections.synchronizedMap(new HashMap<Class,IComponentModelConverter>());

	public void registerComponentModelConverterContributions(IComponentModelConverterContributions pageFactoryContribution, Map<String, Object> properties) {
		Map<Class,IComponentModelConverter> mappings = pageFactoryContribution.getMap();
		String contributionName = pageFactoryContribution.getContributionName();
		logger.debug("registerComponentModelConverterContributions(" + contributionName + ")");
		
		for (Class ac : mappings.keySet())
		{
			logger.debug("registerComponentModelConverterContributions(" + contributionName + "): Adding: "+ac.getName()+"-->"+mappings.get(ac));
			this.componentFactoryContributionsMap.put(ac, mappings.get(ac));
		}
	}

	public void unregisterComponentModelConverterContributions(IComponentModelConverterContributions pageFactoryContribution, Map<String, Object> properties) {
		Map<Class,IComponentModelConverter> mappings = pageFactoryContribution.getMap();
		String contributionName = pageFactoryContribution.getContributionName();
		logger.debug("unregisterComponentModelConverterContributions(" + contributionName + ")");
		for (Class ac : mappings.keySet())
		{
			logger.debug("unregisterComponentModelConverterContributions(" + contributionName + "): Removing: "+ac.getName()+"-->"+mappings.get(ac));
			this.componentFactoryContributionsMap.remove(ac);
		}
	}

	@Override
	public IComponentModelConverter lookup(AbstractComponent componentModel) {
		return componentFactoryContributionsMap.get(componentModel.getClass());
	}

	
}