package org.flowframe.ui.vaadin.common.editors.entity.mvp;

import java.util.HashMap;

import javax.persistence.EntityManager;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.vaadin.mvp.eventbus.EventBus;

import com.vaadin.addon.jpacontainer.EntityItem;

public abstract interface AbstractEntityEditorEventBus extends EventBus {
	public abstract void start(AbstractEntityEditorEventBus entityEditorEventListener,  AbstractComponent lec, EntityManager em, HashMap<String,Object> extraParams);	
	public abstract void entityItemEdit(EntityItem item);
	public abstract void entityItemAdded(EntityItem item);
}
