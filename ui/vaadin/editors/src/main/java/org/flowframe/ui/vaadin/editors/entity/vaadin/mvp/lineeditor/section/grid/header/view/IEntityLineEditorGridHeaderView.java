package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.grid.header.view;

import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.IEntityEditorComponentView;
import com.vaadin.ui.Button.ClickListener;

public interface IEntityLineEditorGridHeaderView  extends IEntityEditorComponentView {
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
	public void addReportListener(ClickListener listener);
	public void setReportEnabled(boolean isEnabled);
	public boolean isReportEnabled();
}