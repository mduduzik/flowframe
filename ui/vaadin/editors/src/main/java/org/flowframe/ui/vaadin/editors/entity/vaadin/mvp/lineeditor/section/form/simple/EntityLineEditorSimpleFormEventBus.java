package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.form.simple;

import org.vaadin.mvp.eventbus.annotation.Event;

import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.AbstractEntityEditorEventBus;
import com.vaadin.addon.jpacontainer.EntityItem;

public interface EntityLineEditorSimpleFormEventBus extends AbstractEntityEditorEventBus {
	@SuppressWarnings("rawtypes")
	@Event(handlers = { EntityLineEditorSimpleFormPresenter.class })
	public void entityItemEdit(EntityItem item);

	@SuppressWarnings("rawtypes")
	@Event(handlers = { EntityLineEditorSimpleFormPresenter.class })
	public void entityItemAdded(EntityItem item);

	@Event(handlers = { EntityLineEditorSimpleFormPresenter.class })
	public void saveForm();

	@Event(handlers = { EntityLineEditorSimpleFormPresenter.class })
	public void validateForm();

	@Event(handlers = { EntityLineEditorSimpleFormPresenter.class })
	public void resetForm();

	@Event(handlers = { EntityLineEditorSimpleFormPresenter.class })
	public void resizeForm(int newHeight);
}
