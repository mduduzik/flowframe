package org.flowframe.ui.services.contribution;

import java.util.Map;

import org.flowframe.ui.services.IUIContributionManager;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.vaadin.mvp.presenter.IPresenterFactory;

public interface IMainApplication {
	public IComponentFactory getComponentFactory();
	public IPresenterFactory getPresenterFactory();
	
	public IViewContribution getViewContributionByCode(String code);
	public IActionContribution getActionContributionByCode(String code);
	public IApplicationContribution getApplicationContributionByCode(String code);
	
	public IUIContributionManager getUiContributionManager();
	public Object createPersistenceContainer(Class<?> entityClass);
	public Map<String, Object> getApplicationConfiguration();
	public String getReportingUrl();
}
