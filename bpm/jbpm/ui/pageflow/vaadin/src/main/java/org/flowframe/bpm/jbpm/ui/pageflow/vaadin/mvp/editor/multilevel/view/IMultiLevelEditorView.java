package org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.editor.multilevel.view;

import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.ext.mvp.IVaadinPageComponentView;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.editor.multilevel.MultiLevelEditorPresenter;
import org.flowframe.ui.component.domain.masterdetail.MasterDetailComponent;
import org.vaadin.mvp.uibinder.IUiBindable;

import com.vaadin.ui.Component;

public interface IMultiLevelEditorView extends IUiBindable, IVaadinPageComponentView {
	public void setContent(Component component);
	public Component getContent();
	public void setOwner(MultiLevelEditorPresenter presenter);
	public void updateBreadCrumb(MasterDetailComponent[] componentModelEnum);
	public void init();
}
