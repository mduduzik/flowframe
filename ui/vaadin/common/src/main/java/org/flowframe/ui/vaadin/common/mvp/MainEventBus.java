package org.flowframe.ui.vaadin.common.mvp;

import org.flowframe.ui.services.contribution.IMainApplication;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

public interface MainEventBus extends EventBus {

	@Event(handlers = { MainPresenter.class })
	public void start(IMainApplication app);
}
