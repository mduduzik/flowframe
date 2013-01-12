package org.flowframe.ui.manager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.flowframe.kernel.common.utils.Validator;
import org.flowframe.ui.services.IUIContributionManager;
import org.flowframe.ui.services.contribution.IActionContribution;
import org.flowframe.ui.services.contribution.IApplicationViewContribution;
import org.flowframe.ui.services.contribution.IViewContribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.Application;

public class ContributionManager implements IUIContributionManager {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Map<String, IApplicationViewContribution> appContributions = Collections
			.synchronizedMap(new HashMap<String, IApplicationViewContribution>());
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

	public void bindApplicationContribution(IApplicationViewContribution appContribution, Map<?, ?> properties) {
		String code = (String) properties.get(IUIContributionManager.UISERVICE_PROPERTY_CODE);
		if (Validator.isNotNull(code)) {
			logger.info("bindApplicationContribution(" + code + ")");
			appContributions.put(code, appContribution);
		} else {
			logger.error("bindApplicationContribution has no code associated with it. Registration failed.");
		}
	}

	public void unbindApplicationContribution(IApplicationViewContribution appContribution, Map<?, ?> properties) {
		String code = (String) properties.get(IUIContributionManager.UISERVICE_PROPERTY_CODE);
		if (Validator.isNotNull(code)) {
			logger.info("unbindApplicationContribution(" + code + ")");
			appContributions.remove(code);
		} else {
			logger.error("unbindApplicationContribution has no code associated with it. Deregistration failed.");
		}
	}

	@Override
	public IApplicationViewContribution getApplicationContributionByCode(Application application, String code) {
		IApplicationViewContribution ac = (IApplicationViewContribution) appContributions.get(code);
		return ac;
	}

	@Override
	public IViewContribution getViewContributionByCode(Application application, String code) {
		IViewContribution vc = (IViewContribution) viewContributions.get(code);
		return vc;
	}

	@Override
	public IApplicationViewContribution[] getCurrentApplicationContributions() {
		return appContributions.values().toArray(new IApplicationViewContribution[] {});
	}

	@Override
	public IActionContribution getActionContributionByCode(Application application, String code) {
		IActionContribution ac = (IActionContribution) actionContributions.get(code);
		return ac;
	}
}
