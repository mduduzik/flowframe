package org.flowframe.ui.vaadin.mvp.editors.multilevel;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.editor.MultiLevelEntityEditorComponent;
import org.flowframe.ui.vaadin.common.mvp.FlowFramePresenter;
import org.flowframe.ui.vaadin.mvp.editors.multilevel.event.IMultiLevelEntityEditorEventBus;
import org.flowframe.ui.vaadin.mvp.editors.multilevel.view.IMultiLevelEntityEditorView;
import org.flowframe.ui.vaadin.mvp.editors.multilevel.view.MultiLevelEntityEditorView;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

@Presenter(view = MultiLevelEntityEditorView.class)
public class MultiLevelEntityEditorPresenter extends FlowFramePresenter<IMultiLevelEntityEditorView, IMultiLevelEntityEditorEventBus>{
	@SuppressWarnings("unused")
	private MultiLevelEntityEditorComponent componentModel;
	
	public void setOriginEditor(IPresenter<?, ? extends EventBus> editor) {
		// TODO: Implement this
	}
	
	@Override
	public void setComponentModel(AbstractComponent componentModel) {
		this.componentModel = (MultiLevelEntityEditorComponent) componentModel;
	}
}
