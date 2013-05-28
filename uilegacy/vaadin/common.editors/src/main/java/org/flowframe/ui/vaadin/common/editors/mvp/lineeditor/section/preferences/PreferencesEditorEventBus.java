package org.flowframe.ui.vaadin.common.editors.mvp.lineeditor.section.preferences;

import org.flowframe.ui.vaadin.common.editors.mvp.lineeditor.section.notes.NotesEditorPresenter;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import com.vaadin.data.Container;
import com.vaadin.data.Item;

public interface PreferencesEditorEventBus extends EventBus {
	@Event(handlers = { NotesEditorPresenter.class })
	public void setItemDataSource(Item item, Container... container);
}
