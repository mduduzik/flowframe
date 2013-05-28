package org.flowframe.ui.vaadin.common.editors.factory.contribution;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.services.contribution.IComponentFactoryContribution;
import org.flowframe.ui.services.contribution.IComponentFactoryContributionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComponentFactoryContributionManager  implements IComponentFactoryContributionManager {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final Map<Class,Class>  componentFactoryContributionsMap = Collections.synchronizedMap(new HashMap<Class,Class>());

	public void registerComponentFactoryContribution(IComponentFactoryContribution pageFactoryContribution, Map<String, Object> properties) {
		Map<Class,Class> mappings = pageFactoryContribution.getComponentToPresenterMappings();
		String contributionName = pageFactoryContribution.getContributionName();
		logger.debug("registerComponentFactoryContribution(" + contributionName + ")");
		
		for (Class ac : mappings.keySet())
		{
			logger.debug("registerComponentFactoryContribution(" + contributionName + "): Adding: "+ac.getName()+"-->"+mappings.get(ac).getName());
			this.componentFactoryContributionsMap.put(ac, mappings.get(ac));
		}
	}

	public void unregisterComponentFactoryContribution(IComponentFactoryContribution pageFactoryContribution, Map<String, Object> properties) {
		Map<Class,Class> mappings = pageFactoryContribution.getComponentToPresenterMappings();
		String contributionName = pageFactoryContribution.getContributionName();
		logger.debug("unregisterComponentFactoryContribution(" + contributionName + ")");
		for (Class ac : mappings.keySet())
		{
			logger.debug("unregisterComponentFactoryContribution(" + contributionName + "): Adding: "+ac.getName()+"-->"+mappings.get(ac).getName());
			this.componentFactoryContributionsMap.remove(ac);
		}
	}

	public Map<Class,Class> getComponentFactoryContributionsMap() {
		return componentFactoryContributionsMap;
	}

	@Override
	public Object create(AbstractComponent component, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}
}