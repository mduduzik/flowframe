package org.flowframe.ui.services;

import org.flowframe.ui.services.contribution.IActionContribution;
import org.flowframe.ui.services.contribution.IApplicationContribution;
import org.flowframe.ui.services.contribution.IViewContribution;
import com.vaadin.Application;

public interface IUIContributionManager {
	public final String UISERVICE_PROPERTY_CODE = "code";
	
	public IViewContribution getViewContributionByCode(Application application,String code);

	public IApplicationContribution getApplicationContributionByCode(Application application,String code);
	
	public IApplicationContribution[] getCurrentApplicationContributions();
	
	public IActionContribution getActionContributionByCode(Application application, String code);
}
