package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.search.header;

import java.util.HashMap;

import javax.persistence.EntityManager;

import org.vaadin.mvp.eventbus.annotation.Event;

import com.conx.logistics.kernel.ui.components.domain.AbstractComponent;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.AbstractEntityEditorEventBus;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.footer.EntityTableFooterPresenter;
import com.vaadin.addon.jpacontainer.EntityItem;

public interface EntityGridHeaderEventBus extends AbstractEntityEditorEventBus {
	@Event(handlers = { EntityGridHeaderPresenter.class })
	public void start(AbstractEntityEditorEventBus entityEditorEventListener,  AbstractComponent aec, EntityManager em, HashMap<String,Object> extraParams);
	@Event(handlers = { EntityGridHeaderPresenter.class })
	public void entityItemEdit(EntityItem item);
	@Event(handlers = { EntityGridHeaderPresenter.class })
	public void entityItemAdded(EntityItem item);		
}
