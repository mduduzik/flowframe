package org.flowframe.ui.vaadin.converters.main.editor.multilevel;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.editor.MultiLevelEntityEditorComponent;
import org.flowframe.ui.vaadin.converters.common.BaseComponentModelConverter;
import org.flowframe.ui.vaadin.mvp.editors.multilevel.MultiLevelEntityEditorPresenter;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.PresenterFactory;

public class MultiLevelEntityEditorConverterImpl extends BaseComponentModelConverter {

	@Override
	public IPresenter<?, ? extends EventBus> convert(AbstractComponent componentModel, PresenterFactory presenterFactory) {
		if (presenterFactory == null) throw new IllegalArgumentException("This converter requires a non-null presenterFactory.");
		if (componentModel == null) throw new IllegalArgumentException("This converter requires a non-null componentModel.");
		if (!(componentModel instanceof MultiLevelEntityEditorComponent)) throw new IllegalArgumentException("The only type of component model supported by this converter it " + MultiLevelEntityEditorComponent.class);
		
		MultiLevelEntityEditorComponent multiLevelEntityEditorComponent = (MultiLevelEntityEditorComponent) componentModel;
		MultiLevelEntityEditorPresenter presenter = (MultiLevelEntityEditorPresenter) presenterFactory.createPresenter(MultiLevelEntityEditorPresenter.class);
		presenter.setOriginEditor(this.componentModelFactory.convert(multiLevelEntityEditorComponent.getContent(), presenterFactory));
		presenter.setComponentModel(componentModel);
		return presenter;
	}

}
