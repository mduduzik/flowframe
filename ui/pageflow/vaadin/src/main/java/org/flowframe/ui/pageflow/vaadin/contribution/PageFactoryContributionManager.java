package org.flowframe.ui.pageflow.vaadin.contribution;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.flowframe.ui.pageflow.services.IPageFactoryContribution;
import org.flowframe.ui.pageflow.vaadin.builder.VaadinPageDataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageFactoryContributionManager  {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private VaadinPageDataBuilder vaadinPageDataBuilder = null;
	
	private final Map<Class,Class>  pageFactoryContributionsMap = Collections.synchronizedMap(new HashMap<Class,Class>());

	public void registerPageFactoryContribution(IPageFactoryContribution pageFactoryContribution, Map<String, Object> properties) {
		Map<Class,Class> mappings = pageFactoryContribution.getComponentToPresenterMappings();
		String contributionName = pageFactoryContribution.getContributionName();
		logger.info("registerPageFactoryContribution(" + contributionName + ")");
		
		for (Class ac : mappings.keySet())
		{
			logger.info("registerPageFactoryContribution(" + contributionName + "): Adding: "+ac.getName()+"-->"+mappings.get(ac).getName());
			this.pageFactoryContributionsMap.put(ac, mappings.get(ac));
		}
	}

	public void unregisterPageFactoryContribution(IPageFactoryContribution pageFactoryContribution, Map<String, Object> properties) {
		if (pageFactoryContribution != null){
			Map<Class,Class> mappings = pageFactoryContribution.getComponentToPresenterMappings();
			String contributionName = pageFactoryContribution.getContributionName();
			logger.info("unregisterPageFactoryContribution(" + contributionName + ")");
			for (Class ac : mappings.keySet())
			{
				logger.debug("unregisterPageFactoryContribution(" + contributionName + "): Removing: "+ac.getName()+"-->"+mappings.get(ac).getName());
				this.pageFactoryContributionsMap.remove(ac);
			}
		}
		else
			logger.info("unregisterPageFactoryContribution called with NULL service");
	}

	public Map<Class,Class> getPageFactoryContributionsMap() {
		return pageFactoryContributionsMap;
	}

	public VaadinPageDataBuilder getVaadinPageDataBuilder() {
		if (this.vaadinPageDataBuilder == null)
			this.vaadinPageDataBuilder = new VaadinPageDataBuilder();
		return vaadinPageDataBuilder;
	}

	public void setVaadinPageDataBuilder(VaadinPageDataBuilder vaadinPageDataBuilder) {
		this.vaadinPageDataBuilder = vaadinPageDataBuilder;
	}	
}