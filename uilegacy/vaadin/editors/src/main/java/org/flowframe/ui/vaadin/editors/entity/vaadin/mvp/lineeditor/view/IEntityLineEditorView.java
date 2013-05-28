package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.view;

import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.IEntityEditorComponentView;
import com.vaadin.ui.TabSheet;

public interface IEntityLineEditorView extends IEntityEditorComponentView {
	public TabSheet getMainLayout();
}