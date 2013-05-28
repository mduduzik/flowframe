package org.flowframe.ui.vaadin.common.editors.pageflow.mvp.editor.grid.header.view;

import org.vaadin.mvp.uibinder.IUiBindable;

import com.vaadin.ui.Button.ClickListener;

public interface IEditorGridHeaderView  extends IUiBindable {
	public void addCreateListener(ClickListener listener);
	public void setCreateEnabled(boolean isEnabled);
	public boolean isCreateEnabled();
	public void addEditListener(ClickListener listener);
	public void setEditEnabled(boolean isEnabled);
	public boolean isEditEnabled();
	public void addDeleteListener(ClickListener listener);
	public void setDeleteEnabled(boolean isEnabled);
	public boolean isDeleteEnabled();
	public void addPrintListener(ClickListener listener);
	public void setPrintEnabled(boolean isEnabled);
	public boolean isPrintEnabled();
	public void init();
}