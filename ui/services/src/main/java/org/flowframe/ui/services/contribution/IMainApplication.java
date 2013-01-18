package org.flowframe.ui.services.contribution;

import java.util.Collection;
import java.util.Map;

import org.flowframe.ui.services.factory.IComponentFactory;
import org.vaadin.mvp.presenter.IPresenterFactory;

public interface IMainApplication {
	public IComponentFactory getComponentFactory();
	public IPresenterFactory getPresenterFactory();
	
	public Collection<IViewContribution> getAllViewContributions();
	public IViewContribution getViewContributionByCode(String code);
	public Collection<IActionContribution> getAllActionContributions();
	public IActionContribution getActionContributionByCode(String code);
	public Collection<IApplicationContribution> getAllApplicationContributions();
	public IApplicationContribution getApplicationContributionByCode(String code);
	
	public Object createPersistenceContainer(Class<?> entityClass);
	public Map<String, Object> getApplicationConfiguration();
}
