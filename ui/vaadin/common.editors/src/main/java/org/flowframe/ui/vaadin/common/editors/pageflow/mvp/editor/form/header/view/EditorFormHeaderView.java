package org.flowframe.ui.vaadin.common.editors.pageflow.mvp.editor.form.header.view;

import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.EntityEditorToolStrip;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.EntityEditorToolStrip.EntityEditorToolStripButton;
import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

public class EditorFormHeaderView extends VerticalLayout implements IEditorFormHeaderView {
	private static final long serialVersionUID = -8556644797413509062L;
	
	private EntityEditorToolStrip toolStrip;
	private EntityEditorToolStripButton verifyButton;
	private EntityEditorToolStripButton saveButton;
	private EntityEditorToolStripButton resetButton;
	
	@UiField
	VerticalLayout mainLayout;

	public EditorFormHeaderView() {
		setWidth("100%");
		setHeight("40px");
		
		this.toolStrip = new EntityEditorToolStrip();
		
		this.verifyButton = this.toolStrip.addToolStripButton(EntityEditorToolStrip.TOOLSTRIP_IMG_VERIFY_PNG);
		this.verifyButton.setEnabled(false);
		
		this.saveButton = this.toolStrip.addToolStripButton(EntityEditorToolStrip.TOOLSTRIP_IMG_SAVE_PNG);
		this.saveButton.setEnabled(false);
		
		this.resetButton = this.toolStrip.addToolStripButton(EntityEditorToolStrip.TOOLSTRIP_IMG_RESET_PNG);
		this.resetButton.setEnabled(false);
	}
	
	@Override
	public void init() {
		this.mainLayout.addComponent(this.toolStrip);
	}

	@Override
	public void addVerifyListener(ClickListener listener) {
		this.verifyButton.addListener(listener);
	}

	@Override
	public void setVerifyEnabled(boolean isEnabled) {
		this.verifyButton.setEnabled(isEnabled);
	}

	@Override
	public boolean isVerifyEnabled() {
		return this.verifyButton.isEnabled();
	}

	@Override
	public void addSaveListener(ClickListener listener) {
		this.saveButton.addListener(listener);
	}

	@Override
	public void setSaveEnabled(boolean isEnabled) {
		this.saveButton.setEnabled(isEnabled);
	}

	@Override
	public boolean isSaveEnabled() {
		return this.saveButton.isEnabled();
	}

	@Override
	public void addResetListener(ClickListener listener) {
		this.resetButton.addListener(listener);
	}

	@Override
	public void setResetEnabled(boolean isEnabled) {
		this.resetButton.setEnabled(isEnabled);
	}

	@Override
	public boolean isResetEnabled() {
		return this.resetButton.isEnabled();
	}
	
}
