package org.flowframe.ui.services.contribution;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;

public interface IMVPViewContribution extends IViewContribution {
	public Class<? extends BasePresenter<?, ? extends EventBus>> getPresenterClass();
}
