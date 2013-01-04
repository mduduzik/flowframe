package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.detail.header;

import java.util.HashMap;

import javax.persistence.EntityManager;

import org.vaadin.mvp.eventbus.annotation.Event;

import com.conx.logistics.kernel.ui.components.domain.AbstractComponent;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.AbstractEntityEditorEventBus;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.data.Item;

public interface EntityFormHeaderEventBus extends AbstractEntityEditorEventBus {
	@Event(handlers = { EntityFormHeaderPresenter.class })
	public void start(AbstractEntityEditorEventBus entityEditorEventListener,  AbstractComponent aec, EntityManager em, HashMap<String,Object> extraParams);
	@SuppressWarnings("rawtypes")
	@Event(handlers = { EntityFormHeaderPresenter.class })
	public void entityItemEdit(EntityItem item);
	@SuppressWarnings("rawtypes")
	@Event(handlers = { EntityFormHeaderPresenter.class })
	public void entityItemAdded(EntityItem item);
	@Event(handlers = { EntityFormHeaderPresenter.class })
	public void setItemDataSource(Item item);
}
