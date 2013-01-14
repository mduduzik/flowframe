package org.flowframe.ui.services.contribution;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;

public interface IApplicationContribution {
	public String getIcon();

	public String getName();
	
	public String getCode();

	public Class<? extends BasePresenter<?, ? extends EventBus>> getPresenterClass();
}
