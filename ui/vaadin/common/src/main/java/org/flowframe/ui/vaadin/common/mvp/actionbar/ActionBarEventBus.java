package org.flowframe.ui.vaadin.common.mvp.actionbar;

import org.flowframe.ui.services.contribution.IMainApplication;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

public interface ActionBarEventBus extends EventBus {
	@Event(handlers = { ActionBarPresenter.class })
	public void start(IMainApplication app);
}
