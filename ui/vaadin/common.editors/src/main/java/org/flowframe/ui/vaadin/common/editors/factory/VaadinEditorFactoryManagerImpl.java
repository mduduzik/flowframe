package org.flowframe.ui.vaadin.common.editors.factory;

import java.util.Map;

import org.flowframe.ui.services.contribution.IComponentFactoryContributionManager;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.services.factory.IComponentFactoryManager;
import org.flowframe.ui.services.factory.IComponentModelFactory;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.ConfigurablePresenterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.AbstractPresenterFactory;

public class VaadinEditorFactoryManagerImpl implements IComponentFactoryManager {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected IComponentFactoryContributionManager componentFactoryContributionManager;	

	@Override
	public IComponentModelFactory create(Map<String, Object> config, AbstractPresenterFactory factory) {
		return new VaadinEntityEditorFactoryImpl(componentFactoryContributionManager,(ConfigurablePresenterFactory)factory);
	}

	@Override
	public IComponentFactoryContributionManager getComponentFactoryContributionManager() {
		return componentFactoryContributionManager;
	}

	public void setComponentFactoryContributionManager(IComponentFactoryContributionManager pageFactoryContributionManager) {
		this.componentFactoryContributionManager = pageFactoryContributionManager;
	}

}
