package org.flowframe.ui.vaadin.common.editors.mvp.editor.form.header.view;

import org.vaadin.mvp.uibinder.IUiBindable;

import com.vaadin.ui.Button.ClickListener;

public interface IEditorFormHeaderView  extends IUiBindable {
	public void addVerifyListener(ClickListener listener);
	public void setVerifyEnabled(boolean isEnabled);
	public boolean isVerifyEnabled();
	public void addSaveListener(ClickListener listener);
	public void setSaveEnabled(boolean isEnabled);
	public boolean isSaveEnabled();
	public void addResetListener(ClickListener listener);
	public void setResetEnabled(boolean isEnabled);
	public boolean isResetEnabled();
	public void init();
}