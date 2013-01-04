package org.flowframe.ui.vaadin.editors.entity.vaadin.ext.notes;

import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.header.EntityEditorToolStripButton;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;

public class NotesEditorToolStrip extends HorizontalLayout {
	private static final long serialVersionUID = 3227270532238666469L;

	private static final String TOOLSTRIP_IMG_DELETE_PNG = "toolstrip/img/delete.png";
	private static final String TOOLSTRIP_IMG_EDIT_PNG = "toolstrip/img/edit.png";
	private static final String TOOLSTRIP_IMG_NEW_PNG = "toolstrip/img/new.png";

	private HorizontalLayout leftLayout;
	private HorizontalLayout rightLayout;
	
	private EntityEditorToolStripButton newButton;
	private EntityEditorToolStripButton editButton;
	private EntityEditorToolStripButton deleteButton;

	public NotesEditorToolStrip() {
		setStyleName("conx-entity-toolstrip");
		setHeight("40px");
		setWidth("100%");
		
		newButton = new EntityEditorToolStripButton(TOOLSTRIP_IMG_NEW_PNG);
		editButton = new EntityEditorToolStripButton(TOOLSTRIP_IMG_EDIT_PNG);
		deleteButton = new EntityEditorToolStripButton(TOOLSTRIP_IMG_DELETE_PNG);
		
		leftLayout = new HorizontalLayout();
		leftLayout.setHeight("28px");
		leftLayout.setStyleName("conx-entity-toolstrip-left");
		leftLayout.setSpacing(true);
		leftLayout.addComponent(newButton);
		leftLayout.addComponent(editButton);
		leftLayout.addComponent(deleteButton);
		
		rightLayout = new HorizontalLayout();
		rightLayout.setHeight("28px");
		rightLayout.setStyleName("conx-entity-toolstrip-right");
		
		addComponent(leftLayout);
		addComponent(rightLayout);
		
		setComponentAlignment(leftLayout, Alignment.MIDDLE_LEFT);
		setComponentAlignment(rightLayout, Alignment.MIDDLE_RIGHT);
	}
	
	public void setNewButtonEnabled(boolean isEnabled) {
		this.newButton.setEnabled(isEnabled);
	}
	
	public boolean isNewButtonEnabled() {
		return this.newButton.isEnabled();
	}
	
	public void addNewButtonClickListener(ClickListener listener) {
		this.newButton.addListener(listener);
	}
	
	public void setEditButtonEnabled(boolean isEnabled) {
		this.editButton.setEnabled(isEnabled);
	}
	
	public boolean isEditButtonEnabled() {
		return this.editButton.isEnabled();
	}
	
	public void addEditButtonClickListener(ClickListener listener) {
		this.editButton.addListener(listener);
	}
	
	public void setDeleteButtonEnabled(boolean isEnabled) {
		this.deleteButton.setEnabled(isEnabled);
	}
	
	public boolean isDeleteButtonEnabled() {
		return this.deleteButton.isEnabled();
	}

	public void addDeleteButtonClickListener(ClickListener listener) {
		this.deleteButton.addListener(listener);
	}
}
