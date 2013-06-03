package org.flowframe.ui.pageflow.vaadin.mvp.editor.grid;

import java.util.Map;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import com.vaadin.data.Container;
import com.vaadin.data.Item;

public interface EditorGridEventBus extends EventBus {
	@Event(handlers = { EditorGridPresenter.class })
	public void configure(Map<String, Object> params);
	
	@Event(handlers = { EditorGridPresenter.class })
	public void create();
	
	@Event(handlers = { EditorGridPresenter.class })
	public void edit();
	
	@Event(handlers = { EditorGridPresenter.class })
	public void delete();
	
	@Event(handlers = { EditorGridPresenter.class })
	public void print();
	
	@Event(handlers = { EditorGridPresenter.class })
	public void setItemDataSource(Item item);
	
	@Event(handlers = { EditorGridPresenter.class })
	public void setContainerItemDataSource(Container container);
}
