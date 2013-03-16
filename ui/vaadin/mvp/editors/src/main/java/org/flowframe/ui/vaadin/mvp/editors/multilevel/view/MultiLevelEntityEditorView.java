package org.flowframe.ui.vaadin.mvp.editors.multilevel.view;

import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class MultiLevelEntityEditorView extends VerticalLayout implements IMultiLevelEntityEditorView {
	private static final long serialVersionUID = 5314558683156673387L;
	
	@UiField
	VerticalLayout mainLayout;
	
	public MultiLevelEntityEditorView() {
		setSizeFull();
		addComponent(new Label("This is an MLEE"));
	}
}
