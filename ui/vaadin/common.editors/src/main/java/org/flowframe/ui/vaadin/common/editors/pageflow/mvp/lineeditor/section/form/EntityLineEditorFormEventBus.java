package org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.form;

import java.util.Map;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import com.vaadin.data.Item;

public interface EntityLineEditorFormEventBus extends EventBus {
	@Event(handlers = { EntityLineEditorFormPresenter.class })
	public void configure(Map<String, Object> params);
	
	@Event(handlers = { EntityLineEditorFormPresenter.class })
	public void validate();
	
	@Event(handlers = { EntityLineEditorFormPresenter.class })
	public void save();
	
	@Event(handlers = { EntityLineEditorFormPresenter.class })
	public void reset();
	
	@Event(handlers = { EntityLineEditorFormPresenter.class })
	public void setItemDataSource(Item item);
}
