package org.flowframe.ui.services.contribution;

import java.util.Map;

import org.flowframe.ui.services.IUIContributionManager;
import org.vaadin.mvp.presenter.IPresenterFactory;

public interface IMainApplication {
	public IUIContributionManager getUiContributionManager();
	public IPresenterFactory getPresenterFactory();
	public IViewContribution getViewContributionByCode(String code);
	public IActionContribution getActionContributionByCode(String code);
	public IApplicationContribution getApplicationContributionByCode(String code);
	public Object createPersistenceContainer(Class<?> entityClass);
	public Map<String, Object> getApplicationConfiguration();
	public String getReportingUrl();
}
