package org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor.view;

import org.flowframe.ui.vaadin.common.editors.entity.mvp.IEntityEditorComponentView;
import com.vaadin.ui.TabSheet;

public interface IEntityLineEditorView extends IEntityEditorComponentView {
	public TabSheet getMainLayout();
}