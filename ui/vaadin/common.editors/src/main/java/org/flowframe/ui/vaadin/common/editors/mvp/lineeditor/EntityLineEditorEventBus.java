package org.flowframe.ui.vaadin.common.editors.mvp.lineeditor;

import java.util.Map;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import com.vaadin.data.Container;
import com.vaadin.data.Item;

public interface EntityLineEditorEventBus extends EventBus {
	@Event(handlers = { EntityLineEditorPresenter.class })
	public void configure(Map<String, Object> params);
	
	@Event(handlers = { EntityLineEditorPresenter.class })
	public void setItemDataSource(Item item, Container...containers);
	
	@Event(handlers = { EntityLineEditorPresenter.class })
	public void setNewItemDataSource(Item item, Container...containers);
}
