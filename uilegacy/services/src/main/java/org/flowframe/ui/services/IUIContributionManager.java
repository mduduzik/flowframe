package org.flowframe.ui.services;

import java.util.Collection;

import org.flowframe.ui.services.contribution.IActionContribution;
import org.flowframe.ui.services.contribution.IApplicationContribution;
import org.flowframe.ui.services.contribution.IViewContribution;

public interface IUIContributionManager {
	public final String UISERVICE_PROPERTY_CODE = "code";
	
	public Collection<IViewContribution> getAllViewContributions();
	public IViewContribution getViewContributionByCode(String code);
	public Collection<IActionContribution> getAllActionContributions();
	public IActionContribution getActionContributionByCode(String code);
	public Collection<IApplicationContribution> getAllApplicationContributions();
	public IApplicationContribution getApplicationContributionByCode(String code);
}
