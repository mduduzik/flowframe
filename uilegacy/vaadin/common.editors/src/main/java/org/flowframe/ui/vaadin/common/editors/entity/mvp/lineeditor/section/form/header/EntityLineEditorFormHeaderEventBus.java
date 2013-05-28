package org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor.section.form.header;

import java.util.HashMap;

import javax.persistence.EntityManager;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.AbstractEntityEditorEventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.data.Item;

public interface EntityLineEditorFormHeaderEventBus extends AbstractEntityEditorEventBus {
	@Event(handlers = { EntityLineEditorFormHeaderPresenter.class })
	public void start(AbstractEntityEditorEventBus entityEditorEventListener, AbstractComponent aec, EntityManager em, HashMap<String, Object> extraParams);

	@SuppressWarnings("rawtypes")
	@Event(handlers = { EntityLineEditorFormHeaderPresenter.class })
	public void entityItemEdit(EntityItem item);

	@SuppressWarnings("rawtypes")
	@Event(handlers = { EntityLineEditorFormHeaderPresenter.class })
	public void entityItemAdded(EntityItem item);

	@Event(handlers = { EntityLineEditorFormHeaderPresenter.class })
	public void setItemDataSource(Item item);
}
