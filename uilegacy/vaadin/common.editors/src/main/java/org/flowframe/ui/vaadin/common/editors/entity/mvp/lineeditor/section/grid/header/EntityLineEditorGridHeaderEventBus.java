package org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor.section.grid.header;

import java.util.HashMap;

import javax.persistence.EntityManager;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.AbstractEntityEditorEventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import com.vaadin.addon.jpacontainer.EntityItem;

public interface EntityLineEditorGridHeaderEventBus extends AbstractEntityEditorEventBus {
	@Event(handlers = { EntityLineEditorGridHeaderPresenter.class })
	public void start(AbstractEntityEditorEventBus entityEditorEventListener,  AbstractComponent aec, EntityManager em, HashMap<String,Object> extraParams);
	@Event(handlers = { EntityLineEditorGridHeaderPresenter.class })
	public void entityItemEdit(EntityItem item);
	@Event(handlers = { EntityLineEditorGridHeaderPresenter.class })
	public void entityItemAdded(EntityItem item);		
}
