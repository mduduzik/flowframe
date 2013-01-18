package org.flowframe.ui.manager;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.flowframe.kernel.common.utils.Validator;
import org.flowframe.ui.services.IUIContributionManager;
import org.flowframe.ui.services.contribution.IActionContribution;
import org.flowframe.ui.services.contribution.IApplicationContribution;
import org.flowframe.ui.services.contribution.IViewContribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContributionManager implements IUIContributionManager {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Map<String, IApplicationContribution> appContributions = Collections
			.synchronizedMap(new HashMap<String, IApplicationContribution>());
	private final Map<String, IViewContribution> viewContributions = Collections.synchronizedMap(new HashMap<String, IViewContribution>());
	private final Map<String, IActionContribution> actionContributions = Collections.synchronizedMap(new HashMap<String, IActionContribution>());

	public void bindViewContribution(IViewContribution viewContribution, Map<?, ?> properties) {
		String code = (String) properties.get(IUIContributionManager.UISERVICE_PROPERTY_CODE);
		if (Validator.isNotNull(code)) {
			logger.info("bindViewContribution(" + code + ")");
			viewContributions.put(code, viewContribution);
		} else {
			logger.error("bindViewContribution has no code associated with it. Registration failed.");
		}
	}

	public void unbindViewContribution(IViewContribution viewContribution, Map<?, ?> properties) {
		String code = (String) properties.get(IUIContributionManager.UISERVICE_PROPERTY_CODE);
		if (Validator.isNotNull(code)) {
			logger.info("unbindViewContribution(" + code + ")");
			viewContributions.remove(code);
		} else {
			logger.error("unbindViewContribution has no code associated with it. Deregistration failed.");
		}
	}

	public void bindActionContribution(IActionContribution actionContribution, Map<?, ?> properties) {
		String code = (String) properties.get(IUIContributionManager.UISERVICE_PROPERTY_CODE);
		if (Validator.isNotNull(code)) {
			logger.info("bindActionContribution(" + code + ")");
			actionContributions.put(code, actionContribution);
		} else {
			logger.error("bindActionContribution has no code associated with it. Registration failed.");
		}
	}

	public void unbindActionContribution(IActionContribution actionContribution, Map<?, ?> properties) {
		String code = (String) properties.get(IUIContributionManager.UISERVICE_PROPERTY_CODE);
		if (Validator.isNotNull(code)) {
			logger.info("unbindActionContribution(" + code + ")");
			actionContributions.remove(code);
		} else {
			logger.error("unbindActionContribution has no code associated with it. Deregistration failed.");
		}
	}

	public void bindApplicationContribution(IApplicationContribution appContribution, Map<?, ?> properties) {
		String code = (String) properties.get(IUIContributionManager.UISERVICE_PROPERTY_CODE);
		if (Validator.isNotNull(code)) {
			logger.info("bindApplicationContribution(" + code + ")");
			appContributions.put(code, appContribution);
		} else {
			logger.error("bindApplicationContribution has no code associated with it. Registration failed.");
		}
	}

	public void unbindApplicationContribution(IApplicationContribution appContribution, Map<?, ?> properties) {
		String code = (String) properties.get(IUIContributionManager.UISERVICE_PROPERTY_CODE);
		if (Validator.isNotNull(code)) {
			logger.info("unbindApplicationContribution(" + code + ")");
			appContributions.remove(code);
		} else {
			logger.error("unbindApplicationContribution has no code associated with it. Deregistration failed.");
		}
	}

	@Override
	public IApplicationContribution getApplicationContributionByCode(String code) {
		IApplicationContribution ac = (IApplicationContribution) appContributions.get(code);
		return ac;
	}

	@Override
	public IViewContribution getViewContributionByCode(String code) {
		IViewContribution vc = (IViewContribution) viewContributions.get(code);
		return vc;
	}
	
	@Override
	public IActionContribution getActionContributionByCode(String code) {
		IActionContribution ac = (IActionContribution) actionContributions.get(code);
		return ac;
	}

	@Override
	public Collection<IViewContribution> getAllViewContributions() {
		HashSet<IViewContribution> viewContributionSet = new HashSet<IViewContribution>();
		for (String code : this.viewContributions.keySet()) {
			viewContributionSet.add(this.viewContributions.get(code));
		}
		return viewContributionSet;
	}

	@Override
	public Collection<IActionContribution> getAllActionContributions() {
		HashSet<IActionContribution> actionContributionSet = new HashSet<IActionContribution>();
		for (String code : this.actionContributions.keySet()) {
			actionContributionSet.add(this.actionContributions.get(code));
		}
		return actionContributionSet;
	}

	@Override
	public Collection<IApplicationContribution> getAllApplicationContributions() {
		HashSet<IApplicationContribution> applicationContributionSet = new HashSet<IApplicationContribution>();
		for (String code : this.appContributions.keySet()) {
			applicationContributionSet.add(this.appContributions.get(code));
		}
		return applicationContributionSet;
	}
}
