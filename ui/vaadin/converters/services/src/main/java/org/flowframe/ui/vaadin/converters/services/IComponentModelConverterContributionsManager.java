package org.flowframe.ui.vaadin.converters.services;

import org.flowframe.ui.component.domain.AbstractComponent;

public interface IComponentModelConverterContributionsManager {
	public IComponentModelConverter lookup(AbstractComponent componentModel);
}
