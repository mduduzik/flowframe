package org.flowframe.ui.pageflow.vaadin.factory;

import java.util.HashMap;
import java.util.Map;

import org.flowframe.ui.pageflow.services.IPageFactoryContribution;

public class BasePageFactoryContributionImpl implements IPageFactoryContribution {
	
	private String contributionName = null;
	private Map<Class,Class> componentToPresenterMappings = new HashMap<Class,Class>();

	@Override
	public Map<Class,Class> getComponentToPresenterMappings() {
		return componentToPresenterMappings;
	}

	public void setComponentToPresenterMappings(Map<Class,Class> componentToPresenterMappings) {
		this.componentToPresenterMappings = componentToPresenterMappings;
	}

	@Override
	public String getContributionName() {
		return this.contributionName;
	}

	public void setContributionName(String contributionName) {
		this.contributionName = contributionName;
	}
}
