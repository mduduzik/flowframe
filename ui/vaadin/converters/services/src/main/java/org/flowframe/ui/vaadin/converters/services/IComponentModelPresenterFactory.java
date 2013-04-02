package org.flowframe.ui.vaadin.converters.services;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.vaadin.mvp.core.eventbus.IBaseEventBus;
import org.vaadin.mvp.presenter.IPresenter;

public interface IComponentModelPresenterFactory {
	public IPresenter<?, ? extends IBaseEventBus> convert(AbstractComponent componentModel);
}
