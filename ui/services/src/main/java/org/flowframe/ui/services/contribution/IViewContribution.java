package org.flowframe.ui.services.contribution;

import java.util.Map;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;

public interface IViewContribution {
	public AbstractComponent getComponentModel(Map<String, Object> properties);

	public String getIcon();

	public String getName();
	
	public String getCode();

	public Class<? extends BasePresenter<?, ? extends EventBus>> getPresenterClass();
}
