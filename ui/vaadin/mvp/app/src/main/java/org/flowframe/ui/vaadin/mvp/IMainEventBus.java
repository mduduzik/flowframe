package org.flowframe.ui.vaadin.mvp;

import org.flowframe.ui.vaadin.mvp.app.events.OpenComponentEvent;
import org.flowframe.ui.vaadin.mvp.app.events.StartApplicationEvent;
import org.flowframe.ui.vaadin.mvp.core.eventbus.IBaseEventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

public interface IMainEventBus extends IBaseEventBus {

	@Event(handlers = { MainPresenter.class })
	public void fireApplicationEvent(StartApplicationEvent appEvent);
	
	@Event(handlers = { MainPresenter.class })
	public void fireApplicationEvent(OpenComponentEvent appEvent);	
}
