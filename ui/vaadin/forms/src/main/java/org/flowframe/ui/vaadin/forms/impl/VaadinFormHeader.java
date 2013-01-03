package org.flowframe.ui.vaadin.forms.impl;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class VaadinFormHeader extends HorizontalLayout {
	private static final long serialVersionUID = 5540232112L;
	private Label action;
	private Label title;
	private HorizontalLayout rightPanel;

	public VaadinFormHeader() {
		setHeight("33px");
		setWidth("100%");
		setStyleName("conx-entity-editor-form-header");
		
		initialize();
	}

	private void initialize() {
		action = new Label();
		action.setStyleName("conx-entity-editor-form-header-action");
		
		title = new Label();
		title.setStyleName("conx-entity-editor-form-header-title");
		
		HorizontalLayout leftPanel = new HorizontalLayout();
		leftPanel.setHeight("33px");
		leftPanel.setSpacing(true);
		leftPanel.setStyleName("conx-entity-editor-form-header-left");
		leftPanel.setMargin(false, false, false, true);
		leftPanel.addComponent(action);
		leftPanel.addComponent(title);
		leftPanel.setComponentAlignment(action, Alignment.MIDDLE_LEFT);
		leftPanel.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
		
		rightPanel = new HorizontalLayout();
		rightPanel.setHeight("33px");
		rightPanel.setSpacing(true);
		rightPanel.setStyleName("conx-entity-editor-form-header-right");
		rightPanel.setMargin(false, true, false, false);
		
		addComponent(leftPanel);
		addComponent(rightPanel);
		setComponentAlignment(leftPanel, Alignment.TOP_LEFT);
		setComponentAlignment(rightPanel, Alignment.BOTTOM_RIGHT);
	}

	public Label getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action.setCaption(action);
	}

	public String getTitle() {
		return title.getCaption();
	}

	public void setTitle(String title) {
		this.title.setCaption(title);
	}
	
	public void addExtraComponent(Component component) {
		rightPanel.addComponent(component);
		rightPanel.setComponentAlignment(component, Alignment.MIDDLE_RIGHT);
	}
}
