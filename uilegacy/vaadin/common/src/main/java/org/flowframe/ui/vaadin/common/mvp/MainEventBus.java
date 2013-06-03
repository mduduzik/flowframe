package org.flowframe.ui.vaadin.common.mvp;

import org.flowframe.ui.pageflow.services.IMainApplication;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;
import org.vaadin.mvp.presenter.BasePresenter;

import com.vaadin.ui.Component;

public interface MainEventBus extends EventBus {

	@Event(handlers = { MainPresenter.class })
	public void start(IMainApplication app);
	
	@Event(handlers = { MainPresenter.class })
	public void openApplicationComponent(Component appComponent, String name, String iconPath, Class<? extends BasePresenter<?, ? extends EventBus>> presenterClass, boolean closable);
}
