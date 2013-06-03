package org.flowframe.ui.pageflow.services;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.vaadin.mvp.presenter.PresenterFactory;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.ui.Component;

public interface IPageDataBuilder {
	public Set<Object> buildResultData(Component component);

	public Map<String, Object> buildResultDataMap(Map<String, Object> parameterData, Collection<?> data, Map<Class<?>, String> resultKeyMap);

	public void applyData(Map<String, Object> config, Component componentRoot);
	
	public void applyParamData(Map<String, Object> config, Component component, Map<String, Object> params, PresenterFactory presenterFactory) throws Exception;

	public void applyItemDataSource(Component component, Container itemContainer, Item item, final PresenterFactory presenterFactory, Map<String, Object> config)
			throws Exception;
	
	public void applyItemDataSource(boolean isDeclaritive, Component component, Container itemContainer, Item item, final PresenterFactory presenterFactory, Map<String, Object> config)
			throws Exception;
}