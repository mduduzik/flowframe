package org.flowframe.ui.vaadin.addons.common;

import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;

public class FlowFrameEntityEditorFormSection extends Form {
	private static final long serialVersionUID = 100000002L;

	private GridLayout layout;
	
	public FlowFrameEntityEditorFormSection() {
		layout = new GridLayout(4, 1);
		layout.setWidth("100%");
		layout.setSpacing(true);
		layout.setMargin(true, false, false, true);
		layout.setStyleName("conx-entity-editor-form");
		setLayout(layout);
		setWriteThrough(false); // False so that commit() must be called explicitly
		setInvalidCommitted(false);
		setFormFieldFactory(new FlowFrameEntityEditorFormFieldFactory());
	}
}
