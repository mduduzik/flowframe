package org.flowframe.ui.pageflow.vaadin.mvp.lineeditor.section.notes;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import com.vaadin.data.Container;
import com.vaadin.data.Item;

public interface NotesEditorEventBus extends EventBus {
	@Event(handlers = { NotesEditorPresenter.class })
	public void setItemDataSource(Item item, Container... container);
}
