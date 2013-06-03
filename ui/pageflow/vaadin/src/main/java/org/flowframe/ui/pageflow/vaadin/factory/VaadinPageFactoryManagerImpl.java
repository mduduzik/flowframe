package org.flowframe.ui.pageflow.vaadin.factory;

import java.util.Map;

import org.flowframe.ui.pageflow.services.IPageFactory;
import org.flowframe.ui.pageflow.services.IPageFactoryManager;
import org.flowframe.ui.pageflow.vaadin.contribution.PageFactoryContributionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.mvp.presenter.PresenterFactory;

public class VaadinPageFactoryManagerImpl implements IPageFactoryManager {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected PageFactoryContributionManager pageFactoryContributionManager;	

	@Override
	public IPageFactory create(Map<String, Object> config,
			PresenterFactory presenterFactory) {
		return new VaadinPageFactoryImpl(config, presenterFactory,pageFactoryContributionManager);
	}

	public PageFactoryContributionManager getPageFactoryContributionManager() {
		return pageFactoryContributionManager;
	}

	public void setPageFactoryContributionManager(PageFactoryContributionManager pageFactoryContributionManager) {
		this.pageFactoryContributionManager = pageFactoryContributionManager;
	}

}
