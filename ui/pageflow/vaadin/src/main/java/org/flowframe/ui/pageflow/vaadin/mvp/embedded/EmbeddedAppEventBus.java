package org.flowframe.ui.pageflow.vaadin.mvp.embedded;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

public interface EmbeddedAppEventBus extends EventBus {
	@Event(handlers = { EmbeddedAppPresenter.class })	
	public void launch(String userScreenName);
}
