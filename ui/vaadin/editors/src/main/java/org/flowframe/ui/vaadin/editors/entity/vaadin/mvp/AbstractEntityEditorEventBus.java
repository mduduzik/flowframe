package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp;

import java.util.HashMap;

import javax.persistence.EntityManager;

import org.vaadin.mvp.eventbus.EventBus;

import com.conx.logistics.kernel.ui.components.domain.AbstractComponent;
import com.conx.logistics.mdm.domain.documentlibrary.FileEntry;
import com.vaadin.addon.jpacontainer.EntityItem;

public abstract interface AbstractEntityEditorEventBus extends EventBus {
	public abstract void start(AbstractEntityEditorEventBus entityEditorEventListener,  AbstractComponent lec, EntityManager em, HashMap<String,Object> extraParams);	
	public abstract void entityItemEdit(EntityItem item);
	public abstract void entityItemAdded(EntityItem item);
}
