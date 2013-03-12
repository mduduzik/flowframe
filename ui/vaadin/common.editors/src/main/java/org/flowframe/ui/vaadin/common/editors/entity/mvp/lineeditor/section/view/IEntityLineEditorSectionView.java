package org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor.section.view;

import org.flowframe.ui.vaadin.common.editors.entity.mvp.IEntityEditorComponentView;
import com.vaadin.ui.Component;

public interface IEntityLineEditorSectionView extends IEntityEditorComponentView {
	public void setHeader(Component component);
	public void setContent(Component component);
}