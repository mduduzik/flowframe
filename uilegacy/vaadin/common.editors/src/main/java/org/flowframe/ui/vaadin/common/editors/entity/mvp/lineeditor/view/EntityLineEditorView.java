package org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor.view;

import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class EntityLineEditorView extends VerticalLayout implements IEntityLineEditorView {
	private static final long serialVersionUID = 1L;
	
	@UiField
	TabSheet mainLayout;
	
	public EntityLineEditorView() {
		setSizeFull();
	}

	@Override
	public void init() {
	}

	public TabSheet getMainLayout() {
		return mainLayout;
	}
}
