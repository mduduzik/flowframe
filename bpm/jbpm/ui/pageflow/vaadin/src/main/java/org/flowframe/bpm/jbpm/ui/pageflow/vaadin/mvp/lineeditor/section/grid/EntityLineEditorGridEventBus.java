package org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.section.grid;

import java.util.Map;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import com.vaadin.data.Item;

public interface EntityLineEditorGridEventBus extends EventBus {
	@Event(handlers = { EntityLineEditorGridPresenter.class })
	public void configure(Map<String, Object> params);
	
	@Event(handlers = { EntityLineEditorGridPresenter.class })
	public void create();
	
	@Event(handlers = { EntityLineEditorGridPresenter.class })
	public void edit();
	
	@Event(handlers = { EntityLineEditorGridPresenter.class })
	public void delete();
	
	@Event(handlers = { EntityLineEditorGridPresenter.class })
	public void print();
	
	@Event(handlers = { EntityLineEditorGridPresenter.class })
	public void report();
	
	@Event(handlers = { EntityLineEditorGridPresenter.class })
	public void setItemDataSource(Item item);
}
