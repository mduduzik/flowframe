package org.flowframe.ui.vaadin.converters.main.editor.multilevel;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.editor.MultiLevelEntityEditorComponent;
import org.flowframe.ui.vaadin.converters.common.BaseComponentModelConverter;
import org.flowframe.ui.vaadin.mvp.editors.multilevel.MultiLevelEntityEditorPresenter;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;

public class MultiLevelEditorConverterImpl extends BaseComponentModelConverter {

	@Override
	public IPresenter<?, ? extends EventBus> convert(AbstractComponent componentModel) {
		if (!(componentModel instanceof MultiLevelEntityEditorComponent)) throw new IllegalArgumentException("The only type of component model supported by this converter it " + MultiLevelEntityEditorComponent.class);
		IPresenter<?, ? extends EventBus> presenter = this.application.getPresenterFactory().createPresenter(MultiLevelEntityEditorPresenter.class);
		return presenter;
	}

}
