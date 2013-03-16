package org.flowframe.ui.vaadin.mvp.editors.view;

import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class EntityEditorView extends VerticalLayout implements IEntityEditorView {
	private static final long serialVersionUID = 5314558683153373387L;
	
	@UiField
	VerticalLayout mainLayout;
	
	public EntityEditorView() {
		setSizeFull();
		addComponent(new Label("This is an EE"));
	}
}
