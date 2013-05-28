package org.flowframe.ui.vaadin.common.editors.mvp.lineeditor.section;

import java.util.Map;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import com.vaadin.data.Item;

public interface EntityLineEditorSectionEventBus extends EventBus {
	@Event(handlers = { EntityLineEditorSectionPresenter.class })
	public void configure(Map<String, Object> params);
	@Event(handlers = { EntityLineEditorSectionPresenter.class })
	public void setItemDataSource(Item item);
}
