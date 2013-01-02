package org.flowframe.ui.vaadin.addons.common;

import com.vaadin.data.Item;
import com.vaadin.ui.VerticalLayout;

public class FlowFrameEntityEditorFormLayout extends VerticalLayout {
	private static final long serialVersionUID = 141500988980L;
	
	private FlowFrameEntityEditorFormHeader header;
	private FlowFrameEntityEditorForm form;
	private FlowFrameEntityEditorFormSection formSection;
	
	public FlowFrameEntityEditorFormLayout(Item item) {
		setSizeFull();
		setStyleName("conx-entity-editor-form");
		initialize(item);
	}

	private void initialize(Item item) {
		this.header = new FlowFrameEntityEditorFormHeader();
		this.form = new FlowFrameEntityEditorForm();
		addComponent(header);
		this.formSection = new FlowFrameEntityEditorFormSection();
		formSection.setItemDataSource(item);
		formSection.setWidth("100%");
		form.addFormSection("That Form Swag", formSection);
		addComponent(form);
		setExpandRatio(form, 1.0f);
	}
}
