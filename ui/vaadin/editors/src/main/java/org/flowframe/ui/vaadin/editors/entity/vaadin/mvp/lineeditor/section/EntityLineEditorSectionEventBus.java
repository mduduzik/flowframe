package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section;

import java.util.HashMap;

import javax.persistence.EntityManager;

import org.vaadin.mvp.eventbus.annotation.Event;

import com.conx.logistics.kernel.ui.components.domain.AbstractComponent;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.AbstractEntityEditorEventBus;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.MultiLevelEntityEditorPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.form.header.EntityLineEditorFormHeaderPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.grid.EntityLineEditorGridPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.grid.header.EntityLineEditorGridHeaderPresenter;
import com.vaadin.addon.jpacontainer.EntityItem;

public interface EntityLineEditorSectionEventBus extends AbstractEntityEditorEventBus {
	@Event(handlers = { EntityLineEditorSectionPresenter.class })
	public void start(MultiLevelEntityEditorPresenter parentPresenter, AbstractEntityEditorEventBus entityEditorEventListener, AbstractComponent aec, EntityManager em,
			HashMap<String, Object> extraParams);

	@SuppressWarnings("rawtypes")
	@Event(handlers = { EntityLineEditorSectionPresenter.class })
	public void entityItemEdit(EntityItem item);

	@SuppressWarnings("rawtypes")
	@Event(handlers = { EntityLineEditorSectionPresenter.class })
	public void entityItemAdded(EntityItem item);
	
	@Event(handlers = { EntityLineEditorGridPresenter.class })
	public void createItem();
	@Event(handlers = { EntityLineEditorGridPresenter.class })
	public void editItem();
	@Event(handlers = { EntityLineEditorGridPresenter.class })
	public void deleteItem();
	@Event(handlers = { EntityLineEditorGridPresenter.class })
	public void reportItem();
	@Event(handlers = { EntityLineEditorGridPresenter.class })
	public void printGrid();
	
	@Event(handlers = { EntityLineEditorGridHeaderPresenter.class })
	public void itemSelected();
	@Event(handlers = { EntityLineEditorGridHeaderPresenter.class })
	public void itemsDepleted();
	
	@Event(handlers = { EntityLineEditorSectionPresenter.class })
	public void saveForm();
	@Event(handlers = { EntityLineEditorSectionPresenter.class })
	public void validateForm();
	@Event(handlers = { EntityLineEditorSectionPresenter.class })
	public void resetForm();
	@Event(handlers = { EntityLineEditorSectionPresenter.class })
	public void resizeForm(int newHeight);
	
	@Event(handlers = { EntityLineEditorFormHeaderPresenter.class })
	public void formChanged();
	@Event(handlers = { EntityLineEditorFormHeaderPresenter.class })
	public void formValidated();
}
