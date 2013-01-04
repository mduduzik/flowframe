package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.footer;

import java.util.HashMap;

import javax.persistence.EntityManager;

import org.vaadin.mvp.eventbus.annotation.Event;

import com.conx.logistics.kernel.ui.components.domain.AbstractComponent;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.AbstractEntityEditorEventBus;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.EntityLineEditorPresenter;
import com.vaadin.addon.jpacontainer.EntityItem;

public interface EntityTableFooterEventBus extends AbstractEntityEditorEventBus {
	@Event(handlers = { EntityTableFooterPresenter.class })
	public void start(AbstractEntityEditorEventBus entityEditorEventListener,  AbstractComponent aec, EntityManager em, HashMap<String,Object> extraParams);
	@Event(handlers = { EntityTableFooterPresenter.class })
	public void entityItemEdit(EntityItem item);
	@Event(handlers = { EntityTableFooterPresenter.class })
	public void entityItemAdded(EntityItem item);		
}
