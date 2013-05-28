package org.flowframe.ui.vaadin.common.editors.entity.mvp.notes.view;

import java.util.Collection;

import org.flowframe.ui.vaadin.common.editors.entity.mvp.IEntityEditorComponentView;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.notes.view.NotesEditorView.ICreateNoteListener;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.notes.view.NotesEditorView.ISaveNoteListener;
import org.flowframe.ui.vaadin.forms.FormMode;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.ui.Layout;

public interface INotesEditorView extends IEntityEditorComponentView {
	public Layout getMainLayout();
	public void setContainerDataSource(Container container, Collection<?> visibleGridColumns, Collection<?> visibleFormFields);
	public void setItemDataSource(Item item, FormMode mode);
	public void showContent();
	public void hideContent();
	public void showDetail();
	public void hideDetail();
	public void addCreateNoteListener(ICreateNoteListener listener);
	public void addSaveNoteListener(ISaveNoteListener listener);
}