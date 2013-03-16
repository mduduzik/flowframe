package org.flowframe.ui.vaadin.converters.services;

import org.flowframe.ds.domain.DataSource;

import com.vaadin.data.Container;

public interface IDataSourceConverter {
	public Container convert(DataSource dataSource);
}
