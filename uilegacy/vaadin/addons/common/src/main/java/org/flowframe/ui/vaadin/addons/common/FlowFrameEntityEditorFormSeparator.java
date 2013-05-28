package org.flowframe.ui.vaadin.addons.common;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class FlowFrameEntityEditorFormSeparator extends HorizontalLayout {
	private static final long serialVersionUID = 1010002929298L;
	
	private Embedded arrow;
	private String title;
	private FlowFrameEntityEditorFormSection section;
	private Label label;
	private boolean expanded;

	public FlowFrameEntityEditorFormSeparator(String title, FlowFrameEntityEditorFormSection section) {
		this.title = title;
		this.section = section;
		this.expanded = false;
		
		setWidth("100%");
		setHeight("25px");
		setStyleName("conx-entity-editor-form-separator");
		
		initialize();
	}

	private void initialize() {
		HorizontalLayout leftPanel = new HorizontalLayout();
		leftPanel.setHeight("25px");
		leftPanel.setSpacing(true);
		
		arrow = new Embedded();
		arrow.setStyleName("conx-entity-editor-form-separator-arrow");
		arrow.setWidth("18px");
		arrow.setHeight("10px");
		
		label = new Label();
		label.setStyleName("conx-entity-editor-form-separator-caption");
		label.setHeight("15px");
		label.setCaption(title);
		
		leftPanel.addComponent(arrow);
		leftPanel.addComponent(label);
		addComponent(leftPanel);
		setComponentAlignment(leftPanel, Alignment.TOP_LEFT);
		
		this.addListener(new LayoutClickListener() {
			private static final long serialVersionUID = 1L;

			public void layoutClick(LayoutClickEvent event) {
				toggle();
			}
		});
	}
	
	private void toggle() {
		if (expanded) {
			arrow.removeStyleName("conx-entity-editor-form-separator-arrow-expanded");
		} else {
			arrow.addStyleName("conx-entity-editor-form-separator-arrow-expanded");
		}
		section.setVisible(!section.isVisible());
		expanded = !expanded;
	}
}
