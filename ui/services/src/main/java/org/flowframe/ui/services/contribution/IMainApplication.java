package org.flowframe.ui.services.contribution;

import org.vaadin.mvp.presenter.IPresenterFactory;

import org.flowframe.ui.services.IUIContributionManager;

public interface IMainApplication {
	public IUIContributionManager getUiContributionManager();
	public IPresenterFactory getPresenterFactory();
	public IViewContribution getViewContributionByCode(String code);
	public IApplicationViewContribution getApplicationContributionByCode(String code);
	public Object createPersistenceContainer(Class entityClass);
	
	/**
	 * Get the explicit url for the reporting service, in a string format.
	 * 
	 * @return the non-relative url of the reporting web service
	 */
	public String getReportingUrl();
}
