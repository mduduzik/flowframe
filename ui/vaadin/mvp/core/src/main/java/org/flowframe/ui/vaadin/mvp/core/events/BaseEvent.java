package org.flowframe.ui.vaadin.mvp.core.events;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;

public class BaseEvent {
	protected IPresenter<?, ? extends EventBus> source;
	
	
	public BaseEvent() {
		super();
	}

	public BaseEvent(IPresenter<?, ? extends EventBus> source) {
		super();
		this.source = source;
	}

	public IPresenter<?, ? extends EventBus> getSource() {
		return source;
	}

	public void setSource(IPresenter<?, ? extends EventBus> source) {
		this.source = source;
	}
}
