package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.grid.header;

import java.util.HashMap;

import javax.persistence.EntityManager;

import org.vaadin.mvp.eventbus.annotation.Event;

import com.conx.logistics.kernel.ui.components.domain.AbstractComponent;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.AbstractEntityEditorEventBus;
import com.vaadin.addon.jpacontainer.EntityItem;

public interface EntityLineEditorGridHeaderEventBus extends AbstractEntityEditorEventBus {
	@Event(handlers = { EntityLineEditorGridHeaderPresenter.class })
	public void start(AbstractEntityEditorEventBus entityEditorEventListener,  AbstractComponent aec, EntityManager em, HashMap<String,Object> extraParams);
	@Event(handlers = { EntityLineEditorGridHeaderPresenter.class })
	public void entityItemEdit(EntityItem item);
	@Event(handlers = { EntityLineEditorGridHeaderPresenter.class })
	public void entityItemAdded(EntityItem item);		
}
