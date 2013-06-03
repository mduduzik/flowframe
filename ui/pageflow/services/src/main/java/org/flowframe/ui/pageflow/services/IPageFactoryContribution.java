package org.flowframe.ui.pageflow.services;

import java.util.Map;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.vaadin.mvp.presenter.BasePresenter;

public interface IPageFactoryContribution {
	public Map<Class, Class> getComponentToPresenterMappings();
	public String getContributionName();
}