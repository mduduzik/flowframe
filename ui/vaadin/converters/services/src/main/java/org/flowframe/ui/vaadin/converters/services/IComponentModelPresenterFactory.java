package org.flowframe.ui.vaadin.converters.services;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.PresenterFactory;

public interface IComponentModelPresenterFactory {
	public IPresenter<?, ? extends EventBus> convert(AbstractComponent componentModel, PresenterFactory presenterFactory);
}
