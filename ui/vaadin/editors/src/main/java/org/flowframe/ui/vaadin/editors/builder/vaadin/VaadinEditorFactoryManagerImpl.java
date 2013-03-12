package org.flowframe.ui.vaadin.editors.builder.vaadin;

import java.util.Map;

import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.services.factory.IComponentFactoryManager;
import org.flowframe.ui.vaadin.editors.builder.vaadin.contribution.ComponentFactoryContributionManager;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.ConfigurablePresenterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.AbstractPresenterFactory;

public class VaadinEditorFactoryManagerImpl implements IComponentFactoryManager {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected ComponentFactoryContributionManager componentFactoryContributionManager;	

	@Override
	public IComponentFactory create(Map<String, Object> config, AbstractPresenterFactory factory) {
		return new VaadinEntityEditorFactoryImpl(componentFactoryContributionManager,(ConfigurablePresenterFactory)factory);
	}

	@Override
	public ComponentFactoryContributionManager getComponentFactoryContributionManager() {
		return componentFactoryContributionManager;
	}

	public void setComponentFactoryContributionManager(ComponentFactoryContributionManager pageFactoryContributionManager) {
		this.componentFactoryContributionManager = pageFactoryContributionManager;
	}

}
