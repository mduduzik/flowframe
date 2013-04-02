package org.flowframe.ui.vaadin.converters.common;

import org.flowframe.ui.vaadin.converters.services.IComponentModelPresenterFactory;
import org.flowframe.ui.vaadin.converters.services.IDataSourceContainerFactory;
import org.vaadin.mvp.presenter.spring.CustomSpringPresenterFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseConverter {
	
	protected IComponentModelPresenterFactory componentModelFactory;
	protected CustomSpringPresenterFactory presenterFactory;
	protected IDataSourceContainerFactory dataSourceFactory;
	
	@Autowired
	public void setComponentModelFactory(
			IComponentModelPresenterFactory componentModelFactory) {
		this.componentModelFactory = componentModelFactory;
	}
	
	@Autowired
	public void setPresenterFactory(CustomSpringPresenterFactory presenterFactory) {
		this.presenterFactory = presenterFactory;
	}
	
	@Autowired
	public void setDataSourceFactory(IDataSourceContainerFactory dataSourceFactory) {
		this.dataSourceFactory = dataSourceFactory;
	}
	
	
}
