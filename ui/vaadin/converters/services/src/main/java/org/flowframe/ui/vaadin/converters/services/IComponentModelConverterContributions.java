package org.flowframe.ui.vaadin.converters.services;

import java.util.Map;

import org.flowframe.ui.component.domain.AbstractComponent;


public interface IComponentModelConverterContributions {
	public IComponentModelConverter lookup(AbstractComponent componentModel);
	public Map<Class,IComponentModelConverter> getMap();
	public String getContributionName();
}
