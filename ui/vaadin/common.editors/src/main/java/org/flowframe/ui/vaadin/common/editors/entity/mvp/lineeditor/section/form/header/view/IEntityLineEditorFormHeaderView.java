package org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor.section.form.header.view;

import org.flowframe.ui.vaadin.common.editors.entity.mvp.IEntityEditorComponentView;
import com.vaadin.ui.Button.ClickListener;

public interface IEntityLineEditorFormHeaderView extends IEntityEditorComponentView {
	public void addVerifyListener(ClickListener listener);

	public void setVerifyEnabled(boolean isEnabled);

	public boolean isVerifyEnabled();

	public void addSaveListener(ClickListener listener);

	public void setSaveEnabled(boolean isEnabled);

	public boolean isSaveEnabled();

	public void addResetListener(ClickListener listener);

	public void setResetEnabled(boolean isEnabled);

	public boolean isResetEnabled();
}