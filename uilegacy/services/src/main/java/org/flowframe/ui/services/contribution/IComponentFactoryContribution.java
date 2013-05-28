package org.flowframe.ui.services.contribution;

import java.util.Map;

public interface IComponentFactoryContribution {
	public Map<Class, Class> getComponentToPresenterMappings();
	public String getContributionName();
}