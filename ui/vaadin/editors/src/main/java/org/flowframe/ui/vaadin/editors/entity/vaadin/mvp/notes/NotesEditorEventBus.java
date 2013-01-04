package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.notes;

import org.vaadin.mvp.eventbus.annotation.Event;

import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.AbstractEntityEditorEventBus;
import com.conx.logistics.mdm.domain.note.NoteItem;
import com.vaadin.addon.jpacontainer.EntityItem;

public interface NotesEditorEventBus extends AbstractEntityEditorEventBus {
	@SuppressWarnings("rawtypes")
	@Event(handlers = { NotesEditorPresenter.class })
	public void entityItemEdit(EntityItem item);
	
	@SuppressWarnings("rawtypes")
	@Event(handlers = { NotesEditorPresenter.class })
	public void entityItemAdded(EntityItem item);		
	
	@Event(handlers = { NotesEditorPresenter.class })
	public void noteItemAdded(NoteItem ni);
}
