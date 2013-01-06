package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.detail.form;

import org.vaadin.mvp.eventbus.annotation.Event;

import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.AbstractEntityEditorEventBus;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.data.Item;

public interface EntityFormEventBus extends AbstractEntityEditorEventBus {
	@SuppressWarnings("rawtypes")
	@Event(handlers = { EntityFormPresenter.class })
	public void entityItemEdit(EntityItem item);
	
	@SuppressWarnings("rawtypes")
	@Event(handlers = { EntityFormPresenter.class })
	public void entityItemAdded(EntityItem item);
	
	@Event(handlers = { EntityFormPresenter.class })
	public void setItemDataSource(Item item);
}
