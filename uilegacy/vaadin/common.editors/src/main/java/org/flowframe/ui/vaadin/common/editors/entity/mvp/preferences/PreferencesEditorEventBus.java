package org.flowframe.ui.vaadin.common.editors.entity.mvp.preferences;

import org.flowframe.kernel.common.mdm.domain.note.NoteItem;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.AbstractEntityEditorEventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import com.vaadin.addon.jpacontainer.EntityItem;

public interface PreferencesEditorEventBus extends AbstractEntityEditorEventBus {
	@SuppressWarnings("rawtypes")
	@Event(handlers = { PreferencesEditorPresenter.class })
	public void entityItemEdit(EntityItem item);
	
	@SuppressWarnings("rawtypes")
	@Event(handlers = { PreferencesEditorPresenter.class })
	public void entityItemAdded(EntityItem item);		
	
	@Event(handlers = { PreferencesEditorPresenter.class })
	public void noteItemAdded(NoteItem ni);
}
