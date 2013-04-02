package org.flowframe.ui.vaadin.converters.main.editor;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.masterdetail.MasterDetailComponent;
import org.flowframe.ui.vaadin.converters.common.BaseComponentModelConverter;
import org.flowframe.ui.vaadin.mvp.core.eventbus.IBaseEventBus;
import org.flowframe.ui.vaadin.mvp.editors.EntityEditorPresenter;
import org.vaadin.mvp.presenter.IPresenter;

public class EntityEditorConverterImpl extends BaseComponentModelConverter {

	@Override
	public IPresenter<?, ? extends IBaseEventBus> convert(AbstractComponent componentModel) {
		if (componentModel == null) throw new IllegalArgumentException("This converter requires a non-null componentModel.");
		if (!(componentModel instanceof MasterDetailComponent)) throw new IllegalArgumentException("The only type of component model supported by this converter is " + MasterDetailComponent.class);
		
		MasterDetailComponent masterDetailComponent = (MasterDetailComponent) componentModel;
		EntityEditorPresenter presenter = (EntityEditorPresenter) presenterFactory.createPresenter(EntityEditorPresenter.class);
		presenter.setMasterPresenter(this.componentModelFactory.convert(masterDetailComponent.getMasterComponent()));
		presenter.setDetailPresenter(this.componentModelFactory.convert(masterDetailComponent.getLineEditorPanel()));
		presenter.setComponentModel(componentModel);
		return presenter;
	}

}
