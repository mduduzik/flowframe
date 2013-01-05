package org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.editor.form;

import java.util.Map;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import com.vaadin.data.Item;

public interface EditorFormEventBus extends EventBus {
	@Event(handlers = { EditorFormPresenter.class })
	public void configure(Map<String, Object> params);
	
	@Event(handlers = { EditorFormPresenter.class })
	public void validate();
	
	@Event(handlers = { EditorFormPresenter.class })
	public void save();
	
	@Event(handlers = { EditorFormPresenter.class })
	public void reset();
	
	@Event(handlers = { EditorFormPresenter.class })
	public void setItemDataSource(Item item);
}
