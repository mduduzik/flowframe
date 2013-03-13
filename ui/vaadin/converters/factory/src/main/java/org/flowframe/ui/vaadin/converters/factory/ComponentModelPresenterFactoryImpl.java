package org.flowframe.ui.vaadin.converters.factory;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.vaadin.converters.services.IComponentModelConverter;
import org.flowframe.ui.vaadin.converters.services.IComponentModelConverterContributionsManager;
import org.flowframe.ui.vaadin.converters.services.IComponentModelPresenterFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.presenter.Presenter;
import com.mvplite.view.View;

public class ComponentModelPresenterFactoryImpl implements IComponentModelPresenterFactory {

	@Autowired
	protected IComponentModelConverterContributionsManager converterContributionsManager;
	
	@Override
	public Presenter<? extends View> convert(AbstractComponent componentModel) {
		IComponentModelConverter converter = converterContributionsManager.lookup(componentModel);
		Presenter<? extends View> res = converter.convert(componentModel);
		return res;
	}

}
