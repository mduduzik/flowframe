package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.search.grid;

import java.util.HashMap;

import javax.persistence.EntityManager;

import org.vaadin.mvp.eventbus.annotation.Event;

import com.conx.logistics.kernel.ui.components.domain.AbstractComponent;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.AbstractEntityEditorEventBus;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.data.Item;

public interface EntityGridEventBus extends AbstractEntityEditorEventBus {
	@Event(handlers = { EntityGridPresenter.class })
	public void start(AbstractEntityEditorEventBus entityEditorEventListener,  AbstractComponent aec, EntityManager em, HashMap<String,Object> extraParams);
	@SuppressWarnings("rawtypes")
	@Event(handlers = { EntityGridPresenter.class })
	public void entityItemEdit(EntityItem item);
	@SuppressWarnings("rawtypes")
	@Event(handlers = { EntityGridPresenter.class })
	public void entityItemAdded(EntityItem item);
	@Event(handlers = { EntityGridPresenter.class })
	public void delete(Item item);
}
