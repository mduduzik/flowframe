package org.flowframe.ui.vaadin.editors.entity.vaadin.ext.header;

import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.header.EntityEditorToolStripButton;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;

public class EntityEditorToolStrip extends HorizontalLayout {
	private static final long serialVersionUID = 2452919399202554279L;

	private static final String TOOLSTRIP_IMG_DELETE_PNG = "toolstrip/img/delete.png";
	private static final String TOOLSTRIP_IMG_SAVE_PNG = "toolstrip/img/save.png";
	private static final String TOOLSTRIP_IMG_EDIT_PNG = "toolstrip/img/edit.png";
	private static final String TOOLSTRIP_IMG_NEW_PNG = "toolstrip/img/new.png";
	private static final String TOOLSTRIP_IMG_PRINT_PNG = "toolstrip/img/print.png";

	private HorizontalLayout leftLayout;
	private HorizontalLayout rightLayout;
	
	private EntityEditorToolStripButton newButton;
	private EntityEditorToolStripButton editButton;
	private EntityEditorToolStripButton saveButton;
	private EntityEditorToolStripButton deleteButton;
	private EntityEditorToolStripButton printButton;

	public EntityEditorToolStrip() {
		setStyleName("conx-entity-toolstrip");
		setHeight("40px");
		setWidth("100%");
		
		newButton = new EntityEditorToolStripButton(TOOLSTRIP_IMG_NEW_PNG);
		editButton = new EntityEditorToolStripButton(TOOLSTRIP_IMG_EDIT_PNG);
		saveButton = new EntityEditorToolStripButton(TOOLSTRIP_IMG_SAVE_PNG);
		deleteButton = new EntityEditorToolStripButton(TOOLSTRIP_IMG_DELETE_PNG);
		printButton = new EntityEditorToolStripButton(TOOLSTRIP_IMG_PRINT_PNG);		
		
		leftLayout = new HorizontalLayout();
		leftLayout.setHeight("28px");
		leftLayout.setStyleName("conx-entity-toolstrip-left");
		leftLayout.setSpacing(true);
		leftLayout.addComponent(newButton);
		leftLayout.addComponent(editButton);
		leftLayout.addComponent(saveButton);
		leftLayout.addComponent(deleteButton);
		leftLayout.addComponent(printButton);
		
		rightLayout = new HorizontalLayout();
		rightLayout.setHeight("28px");
		rightLayout.setStyleName("conx-entity-toolstrip-right");
		
		addComponent(leftLayout);
		addComponent(rightLayout);
		
		setComponentAlignment(leftLayout, Alignment.MIDDLE_LEFT);
		setComponentAlignment(rightLayout, Alignment.MIDDLE_RIGHT);
	}

	public EntityEditorToolStripButton getPrintButton() {
		return printButton;
	}

	public void setPrintButton(EntityEditorToolStripButton printButton) {
		this.printButton = printButton;
	}
}
