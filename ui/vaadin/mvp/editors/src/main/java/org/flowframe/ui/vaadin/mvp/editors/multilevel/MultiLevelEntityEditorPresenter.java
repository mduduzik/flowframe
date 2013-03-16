package org.flowframe.ui.vaadin.mvp.editors.multilevel;

import org.flowframe.ui.vaadin.common.mvp.FlowFramePresenter;
import org.flowframe.ui.vaadin.mvp.editors.multilevel.event.IMultiLevelEntityEditorEventBus;
import org.flowframe.ui.vaadin.mvp.editors.multilevel.view.IMultiLevelEntityEditorView;
import org.flowframe.ui.vaadin.mvp.editors.multilevel.view.MultiLevelEntityEditorView;
import org.vaadin.mvp.presenter.annotation.Presenter;

@Presenter(view = MultiLevelEntityEditorView.class)
public class MultiLevelEntityEditorPresenter extends FlowFramePresenter<IMultiLevelEntityEditorView, IMultiLevelEntityEditorEventBus>{
}
