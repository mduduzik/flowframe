package org.flowframe.ui.vaadin.mvp.editors.multilevel;

import org.flowframe.ui.component.domain.editor.MultiLevelEntityEditorComponent;
import org.flowframe.ui.vaadin.common.mvp.FlowFramePresenter;
import org.flowframe.ui.component.domain.masterdetail.MasterDetailComponent;
import org.flowframe.ui.vaadin.mvp.editors.multilevel.event.IMultiLevelEntityEditorEventBus;
import org.flowframe.ui.vaadin.mvp.editors.multilevel.view.IMultiLevelEntityEditorView;
import org.flowframe.ui.vaadin.mvp.editors.multilevel.view.MultiLevelEntityEditorView;
import org.vaadin.mvp.presenter.annotation.Presenter;

@Presenter(view = MultiLevelEntityEditorView.class)
public class MultiLevelEntityEditorPresenter extends FlowFramePresenter<IMultiLevelEntityEditorView, IMultiLevelEntityEditorEventBus>{
	@Override
	public void bind() {
		// TODO: Add logic for using factory to create child presenters
		MasterDetailComponent originEditorComponent = ((MultiLevelEntityEditorComponent) this.componentModel).getContent();
		// this.app.getComponentFactory().create(originEditorComponent, null)
		// TODO: the getComponentFactory() method must return the new factory implementation
	}
}
