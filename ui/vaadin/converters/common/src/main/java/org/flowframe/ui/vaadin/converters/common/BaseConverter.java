package org.flowframe.ui.vaadin.converters.common;

import org.flowframe.ui.vaadin.converters.services.IComponentModelPresenterFactory;
import org.flowframe.ui.vaadin.converters.services.IDataSourceContainerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseConverter {
	@Autowired
	protected IComponentModelPresenterFactory componentModelFactory;
	@Autowired
	protected IDataSourceContainerFactory dataSourceFactory;
}
