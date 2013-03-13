package org.flowframe.ui.vaadin.converters.common;

import java.util.HashMap;
import java.util.Map;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.vaadin.converters.services.IComponentModelConverter;
import org.flowframe.ui.vaadin.converters.services.IComponentModelConverterContributions;

public class ComponentModelConverterContributionsImpl implements IComponentModelConverterContributions {
	
	private String contributionName; 
	
	private Map<Class,IComponentModelConverter> componentModelClassToEditorPresenterClassMap = new HashMap<Class, IComponentModelConverter>();
	

	public Map<Class, IComponentModelConverter> getComponentModelClassToEditorPresenterClassMap() {
		return componentModelClassToEditorPresenterClassMap;
	}

	public void setComponentModelClassToEditorPresenterClassMap(Map<Class, IComponentModelConverter> componentModelClassToEditorPresenterClassMap) {
		this.componentModelClassToEditorPresenterClassMap = componentModelClassToEditorPresenterClassMap;
	}

	@Override
	public IComponentModelConverter lookup(AbstractComponent componentModel) {
		return componentModelClassToEditorPresenterClassMap.get(componentModel.getClass());
	}

	@Override
	public Map<Class, IComponentModelConverter> getMap() {
		return this.componentModelClassToEditorPresenterClassMap;
	}

	public String getContributionName() {
		return this.contributionName;
	}

	public void setContributionName(String contributionName) {
		this.contributionName = contributionName;
	}
}
