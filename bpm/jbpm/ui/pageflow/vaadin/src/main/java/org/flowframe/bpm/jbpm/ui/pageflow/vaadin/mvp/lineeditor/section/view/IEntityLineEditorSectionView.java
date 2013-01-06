package org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.section.view;

import org.vaadin.mvp.uibinder.IUiBindable;

import com.vaadin.ui.Component;

public interface IEntityLineEditorSectionView extends IUiBindable {
	public void setHeader(Component component);
	public void setContent(Component component);
}