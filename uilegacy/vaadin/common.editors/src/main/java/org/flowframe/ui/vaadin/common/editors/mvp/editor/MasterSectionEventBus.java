package org.flowframe.ui.vaadin.common.editors.mvp.editor;

import java.util.Map;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import com.vaadin.data.Item;

public interface MasterSectionEventBus extends EventBus {
	@Event(handlers = { MasterSectionPresenter.class })
	public void configure(Map<String, Object> params);
	@Event(handlers = { MasterSectionPresenter.class })
	public void setItemDataSource(Item item);
	@Event(handlers = { MasterSectionPresenter.class })
	public void addNewBeanItem(Object newBean);
}
