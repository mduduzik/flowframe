package org.flowframe.ui.pageflow.vaadin.mvp.lineeditor.section.attachment;

import java.util.Map;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import com.vaadin.data.Item;

public interface AttachmentEditorEventBus extends EventBus {
	@Event(handlers = { AttachmentEditorPresenter.class })
	public void configure(Map<String, Object> params);
	@Event(handlers = { AttachmentEditorPresenter.class })
	public void setItemDataSource(Item item);
}
