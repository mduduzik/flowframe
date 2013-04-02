package org.flowframe.ui.vaadin.converters.factory;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.vaadin.converters.services.IComponentModelConverter;
import org.flowframe.ui.vaadin.converters.services.IComponentModelConverterContributionsManager;
import org.flowframe.ui.vaadin.converters.services.IComponentModelPresenterFactory;
import org.flowframe.ui.vaadin.mvp.core.eventbus.IBaseEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.mvp.presenter.IPresenter;


public class ComponentModelPresenterFactoryImpl implements IComponentModelPresenterFactory {

	
	protected IComponentModelConverterContributionsManager converterContributionsManager;
	
	@Autowired(required = true)
	public void setConverterContributionsManager(
			IComponentModelConverterContributionsManager converterContributionsManager) {
		this.converterContributionsManager = converterContributionsManager;
	}



	@Override
	public IPresenter<?, ? extends IBaseEventBus> convert(AbstractComponent componentModel) {
		IComponentModelConverter converter = converterContributionsManager.lookup(componentModel);
		IPresenter<?, ? extends IBaseEventBus> pres = converter.convert(componentModel);
		return pres;
	}

}
