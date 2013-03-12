package org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.refnum.view;

import java.util.Collection;

import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.refnum.view.ReferenceNumberEditorView.ICreateReferenceNumberListener;
import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.refnum.view.ReferenceNumberEditorView.ISaveReferenceNumberListener;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.IEntityEditorComponentView;
import org.flowframe.ui.vaadin.forms.FormMode;
import org.flowframe.ui.vaadin.forms.listeners.IFormChangeListener;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.ui.Layout;

public interface IReferenceNumberEditorView extends IEntityEditorComponentView {
	public Layout getMainLayout();
	public void setItemDataSource(Item item, FormMode mode);
	public void setContainerDataSource(Container container, Collection<?> visibleGridColumns, Collection<?> visibleFormFields);
	public void showContent();
	public void hideContent();
	public void showDetail();
	public void hideDetail();
	public void addCreateReferenceNumberListener(ICreateReferenceNumberListener listener);
	public void addSaveReferenceNumberListener(ISaveReferenceNumberListener listener);
	public void addFormChangeListener(IFormChangeListener listener);
}