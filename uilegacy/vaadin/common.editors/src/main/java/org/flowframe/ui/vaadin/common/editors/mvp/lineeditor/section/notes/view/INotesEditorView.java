package org.flowframe.ui.vaadin.common.editors.mvp.lineeditor.section.notes.view;

import java.util.Collection;

import org.flowframe.ui.vaadin.common.editors.mvp.lineeditor.section.notes.view.NotesEditorView.ICreateNotesListener;
import org.flowframe.ui.vaadin.common.editors.mvp.lineeditor.section.notes.view.NotesEditorView.ISaveNotesListener;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.IEntityEditorComponentView;
import org.flowframe.ui.vaadin.forms.FormMode;
import org.flowframe.ui.vaadin.forms.listeners.IFormChangeListener;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.ui.Layout;

public interface INotesEditorView extends IEntityEditorComponentView {
	public Layout getMainLayout();
	public void setItemDataSource(Item item, FormMode mode);
	public void setContainerDataSource(Container container, Object[] visibleGridColumns, String[] visibleGridColumnTitles, Collection<?> visibleFormFields);
	public void showContent();
	public void hideContent();
	public void showDetail();
	public void hideDetail();
	public void addCreateNotesListener(ICreateNotesListener listener);
	public void addSaveNotesListener(ISaveNotesListener listener);
	public void addFormChangeListener(IFormChangeListener listener);
}