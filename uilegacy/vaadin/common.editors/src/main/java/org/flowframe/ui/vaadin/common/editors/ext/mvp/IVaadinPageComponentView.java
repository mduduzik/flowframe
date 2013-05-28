package org.flowframe.ui.vaadin.common.editors.ext.mvp;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;

public interface IVaadinPageComponentView {
	public IPresenter<?, ? extends EventBus> getOwner();
}
