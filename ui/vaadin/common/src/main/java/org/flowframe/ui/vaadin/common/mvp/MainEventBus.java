package org.flowframe.ui.vaadin.common.mvp;

import org.flowframe.ui.services.contribution.IMainApplication;
import org.flowframe.ui.vaadin.common.ui.menu.app.AppMenuEntry;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;
import org.vaadin.mvp.presenter.BasePresenter;

public interface MainEventBus extends EventBus {

	@Event(handlers = { MainPresenter.class })
	public void start(IMainApplication app);

	@Event(handlers = { MainPresenter.class })
	public void updateAppContributions(AppMenuEntry[] appMenuEntries);

	@Event(handlers = { MainPresenter.class })
	public void addAppContribution(AppMenuEntry appMenuEntry);

	@Event(handlers = { MainPresenter.class })
	public void removeAppContribution(AppMenuEntry appMenuEntry);

	@Event(handlers = { MainPresenter.class })
	public void openModule(Class<? extends BasePresenter<?, ? extends EventBus>> presenterClass);

	@Event(handlers = { MainPresenter.class })
	public void openApplication(Class<? extends BasePresenter<?, ? extends EventBus>> appPresenterClass, String name, String iconPath, boolean closable);
}
