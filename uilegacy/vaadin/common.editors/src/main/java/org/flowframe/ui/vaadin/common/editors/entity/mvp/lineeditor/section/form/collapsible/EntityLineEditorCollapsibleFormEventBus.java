package org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor.section.form.collapsible;

import org.vaadin.mvp.eventbus.annotation.Event;

import org.flowframe.ui.vaadin.common.editors.entity.mvp.AbstractEntityEditorEventBus;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.data.Item;

public interface EntityLineEditorCollapsibleFormEventBus extends AbstractEntityEditorEventBus {
	@SuppressWarnings("rawtypes")
	@Event(handlers = { EntityLineEditorCollapsibleFormPresenter.class })
	public void entityItemEdit(EntityItem item);

	@SuppressWarnings("rawtypes")
	@Event(handlers = { EntityLineEditorCollapsibleFormPresenter.class })
	public void entityItemAdded(EntityItem item);

	@Event(handlers = { EntityLineEditorCollapsibleFormPresenter.class })
	public void setItemDataSource(Item item);

	@Event(handlers = { EntityLineEditorCollapsibleFormPresenter.class })
	public void saveForm();

	@Event(handlers = { EntityLineEditorCollapsibleFormPresenter.class })
	public void validateForm();

	@Event(handlers = { EntityLineEditorCollapsibleFormPresenter.class })
	public void resetForm();

	@Event(handlers = { EntityLineEditorCollapsibleFormPresenter.class })
	public void resizeForm(int newHeight);
}
