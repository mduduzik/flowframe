package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.footer;

import java.util.HashMap;

import javax.persistence.EntityManager;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.AbstractEntityEditorEventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import com.vaadin.addon.jpacontainer.EntityItem;

public interface EntityTableFooterEventBus extends AbstractEntityEditorEventBus {
	@Event(handlers = { EntityTableFooterPresenter.class })
	public void start(AbstractEntityEditorEventBus entityEditorEventListener,  AbstractComponent aec, EntityManager em, HashMap<String,Object> extraParams);
	@Event(handlers = { EntityTableFooterPresenter.class })
	public void entityItemEdit(EntityItem item);
	@Event(handlers = { EntityTableFooterPresenter.class })
	public void entityItemAdded(EntityItem item);		
}
