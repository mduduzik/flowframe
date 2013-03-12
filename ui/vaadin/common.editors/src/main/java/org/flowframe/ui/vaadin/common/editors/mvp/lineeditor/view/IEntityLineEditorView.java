package org.flowframe.ui.vaadin.common.editors.mvp.lineeditor.view;

import org.vaadin.mvp.uibinder.IUiBindable;

import com.vaadin.ui.Component;

public interface IEntityLineEditorView extends IUiBindable {
	public void removeTab(Component content);
	public void removeAllTabs();
	public void addTab(Component content, String title);
	public void init();
}