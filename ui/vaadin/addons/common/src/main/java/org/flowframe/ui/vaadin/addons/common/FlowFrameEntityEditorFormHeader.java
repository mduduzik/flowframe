package org.flowframe.ui.vaadin.addons.common;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class FlowFrameEntityEditorFormHeader extends HorizontalLayout {
	private static final long serialVersionUID = 5540232112L;
	private Label action;
	private Label title;
	private Button next;
	private Button previous;

	public FlowFrameEntityEditorFormHeader() {
		setHeight("38px");
		setWidth("100%");
		setStyleName("conx-entity-editor-form-header");
		setSpacing(true);
		
		initialize();
	}

	private void initialize() {
		action = new Label();
		action.setCaption("Editing");
		action.setStyleName("conx-entity-editor-form-header-action");
		
		title = new Label();
		title.setCaption("Detail Category 1");
		title.setStyleName("conx-entity-editor-form-header-title");
		
		HorizontalLayout leftPanel = new HorizontalLayout();
		leftPanel.setHeight("38px");
		leftPanel.setSpacing(true);
		leftPanel.setStyleName("conx-entity-editor-form-header-left");
		leftPanel.setMargin(false, false, false, true);
		leftPanel.addComponent(action);
		leftPanel.addComponent(title);
		leftPanel.setComponentAlignment(action, Alignment.MIDDLE_LEFT);
		leftPanel.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
		
		addComponent(leftPanel);
		setComponentAlignment(leftPanel, Alignment.TOP_LEFT);
		
		next = new Button();
		next.setStyleName("conx-entity-editor-form-header-pager");
		next.setIcon(new ThemeResource("formlayout/img/custom/conx-form-header-arrow-right.png"));
		
		previous = new Button();
		previous.setStyleName("conx-entity-editor-form-header-pager");
		previous.setIcon(new ThemeResource("formlayout/img/custom/conx-form-header-arrow-left.png"));
		
		HorizontalLayout rightPanel = new HorizontalLayout();
		rightPanel.setSpacing(true);
		rightPanel.addComponent(previous);
		rightPanel.addComponent(next);
		rightPanel.setComponentAlignment(next, Alignment.MIDDLE_RIGHT);
		rightPanel.setComponentAlignment(previous, Alignment.MIDDLE_RIGHT);
		rightPanel.setMargin(false, true, false, false);
		
		addComponent(rightPanel);
		setComponentAlignment(rightPanel, Alignment.MIDDLE_RIGHT);
	}
}
