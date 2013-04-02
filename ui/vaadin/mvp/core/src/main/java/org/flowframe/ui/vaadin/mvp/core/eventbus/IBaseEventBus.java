package org.flowframe.ui.vaadin.mvp.core.eventbus;

import org.flowframe.ui.vaadin.mvp.core.events.BaseEvent;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;
import org.vaadin.mvp.presenter.BasePresenter;

public interface IBaseEventBus extends EventBus {
	@Event(handlers = { BasePresenter.class })
	public void fireEvent(BaseEvent e);
}

