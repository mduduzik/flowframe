package org.flowframe.ui.vaadin.converters.services;

import org.flowframe.ui.component.domain.AbstractComponent;

import com.mvplite.presenter.Presenter;
import com.mvplite.view.View;


public interface IComponentModelConverterContributionsManager {
	public IComponentModelConverter lookup(AbstractComponent componentModel);
}
