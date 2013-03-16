package org.flowframe.ui.vaadin.converters.main.editor;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.masterdetail.MasterDetailComponent;
import org.flowframe.ui.vaadin.converters.common.BaseComponentModelConverter;
import org.flowframe.ui.vaadin.mvp.editors.EntityEditorPresenter;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.PresenterFactory;

public class EntityEditorConverterImpl extends BaseComponentModelConverter {

	@Override
	public IPresenter<?, ? extends EventBus> convert(AbstractComponent componentModel, PresenterFactory presenterFactory) {
		if (presenterFactory == null) throw new IllegalArgumentException("This converter requires a non-null presenterFactory.");
		if (componentModel == null) throw new IllegalArgumentException("This converter requires a non-null componentModel.");
		if (!(componentModel instanceof MasterDetailComponent)) throw new IllegalArgumentException("The only type of component model supported by this converter is " + MasterDetailComponent.class);
		
		MasterDetailComponent masterDetailComponent = (MasterDetailComponent) componentModel;
		EntityEditorPresenter presenter = (EntityEditorPresenter) presenterFactory.createPresenter(EntityEditorPresenter.class);
		presenter.setMasterPresenter(this.componentModelFactory.convert(masterDetailComponent.getMasterComponent(), presenterFactory));
		presenter.setDetailPresenter(this.componentModelFactory.convert(masterDetailComponent.getLineEditorPanel(), presenterFactory));
		presenter.setComponentModel(componentModel);
		return presenter;
	}

}
