package org.flowframe.ui.vaadin.addons.common;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;

public class FlowFrameEntityToolStrip extends HorizontalLayout {
	private static final long serialVersionUID = -8851084906476092083L;
	
	private HorizontalLayout leftButtonStrip;
	private FlowFrameEntityToolStripButton newButton;
	private FlowFrameEntityToolStripButton editButton;
	private FlowFrameEntityToolStripButton saveButton;
	private FlowFrameEntityToolStripButton deleteButton;
	
	public FlowFrameEntityToolStrip() {
		setWidth("100%");
		setHeight("40px");
		setStyleName("conx-entity-toolstrip");
		initialize();
	}

	private void initialize() {
		leftButtonStrip = new HorizontalLayout();
		leftButtonStrip.setHeight("28px");
		leftButtonStrip.setSpacing(true);
		
		newButton = new FlowFrameEntityToolStripButton("toolstrip/img/new.png");
		newButton.setStyleName("conx-entity-toolstrip-button");
		newButton.setHeight("28px");
		leftButtonStrip.addComponent(newButton);
		
		editButton = new FlowFrameEntityToolStripButton("toolstrip/img/edit.png");
		editButton.setIcon(new ThemeResource("toolstrip/img/edit.png"));
		editButton.setStyleName("conx-entity-toolstrip-button");
		editButton.setHeight("28px");
		leftButtonStrip.addComponent(editButton);
		
		saveButton = new FlowFrameEntityToolStripButton("toolstrip/img/save.png");
		saveButton.setIcon(new ThemeResource("toolstrip/img/save.png"));
		saveButton.setStyleName("conx-entity-toolstrip-button");
		saveButton.setHeight("28px");
		leftButtonStrip.addComponent(saveButton);
		
		deleteButton = new FlowFrameEntityToolStripButton("toolstrip/img/delete.png");
		deleteButton.setIcon(new ThemeResource("toolstrip/img/delete.png"));
		deleteButton.setStyleName("conx-entity-toolstrip-button");
		deleteButton.setHeight("28px");
		leftButtonStrip.addComponent(deleteButton);
		
		addComponent(leftButtonStrip);
		setComponentAlignment(leftButtonStrip, Alignment.MIDDLE_LEFT);
	}
}
