package org.flowframe.ui.vaadin.common.editors.mvp.lineeditor.section.refnum;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import com.vaadin.data.Container;
import com.vaadin.data.Item;

public interface ReferenceNumberEditorEventBus extends EventBus {
	@Event(handlers = { ReferenceNumberEditorPresenter.class })
	public void setItemDataSource(Item item, Container... container);
}
