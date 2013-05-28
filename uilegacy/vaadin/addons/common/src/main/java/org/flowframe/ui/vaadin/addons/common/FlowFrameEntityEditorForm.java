package org.flowframe.ui.vaadin.addons.common;

import java.util.ArrayList;

import com.vaadin.ui.VerticalLayout;

public class FlowFrameEntityEditorForm extends VerticalLayout {
	private static final long serialVersionUID = 2980988998881L;

	private ArrayList<FlowFrameEntityEditorFormSeparator> separators;
	private ArrayList<FlowFrameEntityEditorFormSection> sections;
	
	public FlowFrameEntityEditorForm() {
		separators = new ArrayList<FlowFrameEntityEditorFormSeparator>();
		sections = new ArrayList<FlowFrameEntityEditorFormSection>();
		setSizeFull();
		setSpacing(true);
	}
	
	public void addFormSection(String title, FlowFrameEntityEditorFormSection section) {
		FlowFrameEntityEditorFormSeparator separator = new FlowFrameEntityEditorFormSeparator(title, section);
		addComponent(separator);
		addComponent(section);
		
		separators.add(separator);
		sections.add(section);
	}
}
