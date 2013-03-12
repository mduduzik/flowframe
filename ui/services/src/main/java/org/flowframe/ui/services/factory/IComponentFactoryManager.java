package org.flowframe.ui.services.factory;

import java.util.Map;

import org.flowframe.ui.services.contribution.IComponentFactoryContributionManager;
import org.vaadin.mvp.presenter.AbstractPresenterFactory;

public interface IComponentFactoryManager {
	public IComponentModelFactory create(Map<String, Object> config, AbstractPresenterFactory factory);

	public IComponentFactoryContributionManager getComponentFactoryContributionManager();
}
