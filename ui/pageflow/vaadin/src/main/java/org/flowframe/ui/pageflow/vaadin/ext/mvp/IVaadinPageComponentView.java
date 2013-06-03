package org.flowframe.ui.pageflow.vaadin.ext.mvp;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;

public interface IVaadinPageComponentView {
	public IPresenter<?, ? extends EventBus> getOwner();
}
