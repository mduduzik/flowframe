package org.flowframe.ui.vaadin.converters.main.editor.multilevel;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.editor.MultiLevelEntityEditorComponent;
import org.flowframe.ui.vaadin.converters.common.BaseComponentModelConverter;
import org.flowframe.ui.vaadin.mvp.core.eventbus.IBaseEventBus;
import org.flowframe.ui.vaadin.mvp.editors.multilevel.MultiLevelEntityEditorPresenter;
import org.vaadin.mvp.presenter.IPresenter;

public class MultiLevelEntityEditorConverterImpl extends BaseComponentModelConverter {

	@Override
	public IPresenter<?, ? extends IBaseEventBus> convert(AbstractComponent componentModel) {
		if (componentModel == null) throw new IllegalArgumentException("This converter requires a non-null componentModel.");
		if (!(componentModel instanceof MultiLevelEntityEditorComponent)) throw new IllegalArgumentException("The only type of component model supported by this converter it " + MultiLevelEntityEditorComponent.class);
		
		MultiLevelEntityEditorComponent multiLevelEntityEditorComponent = (MultiLevelEntityEditorComponent) componentModel;
		MultiLevelEntityEditorPresenter presenter = (MultiLevelEntityEditorPresenter) presenterFactory.createPresenter(MultiLevelEntityEditorPresenter.class);
		presenter.setOriginEditor(this.componentModelFactory.convert(multiLevelEntityEditorComponent.getContent()));
		presenter.setComponentModel(componentModel);
		return presenter;
	}

}
