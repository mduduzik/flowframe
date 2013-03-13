package org.flowframe.ui.vaadin.converters.common;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.vaadin.common.mvp.AbstractMainApplication;
import org.flowframe.ui.vaadin.converters.services.IComponentModelConverter;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.presenter.Presenter;
import com.mvplite.view.View;

public abstract class BaseConverter implements IComponentModelConverter {
	
	@Autowired
	protected AbstractMainApplication application;

	@Override
	public abstract Presenter<? extends View> convert(AbstractComponent componentModel);
}
