package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp;

import java.util.HashMap;

import javax.persistence.EntityManager;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.eventbus.annotation.Event;
import org.vaadin.mvp.presenter.PresenterFactory;

import com.conx.logistics.kernel.ui.components.domain.masterdetail.LineEditorComponent;
import com.conx.logistics.kernel.ui.components.domain.masterdetail.MasterDetailComponent;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.IAbstractFormParentEventBus;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.IAbstractGridParentEventBus;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.detail.form.EntityFormPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.detail.header.EntityFormHeaderPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.search.grid.EntityGridPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.search.header.EntityGridHeaderPresenter;
import com.conx.logistics.mdm.domain.documentlibrary.FileEntry;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.data.Item;

public interface MultiLevelEntityEditorEventBus extends AbstractEntityEditorEventBus, IAbstractFormParentEventBus, IAbstractGridParentEventBus {
	@Event(handlers = { MultiLevelEntityEditorPresenter.class })
	public void init(EventBusManager ebm, PresenterFactory presenterFactory,MasterDetailComponent md, EntityManager em, HashMap<String,Object> extraParams);
	
	@Event(handlers = { MultiLevelEntityEditorPresenter.class })
	public void start(AbstractEntityEditorEventBus entityEditorEventListener,  LineEditorComponent aec, EntityManager em, HashMap<String,Object> extraParams);
	@SuppressWarnings("rawtypes")
	@Event(handlers = { MultiLevelEntityEditorPresenter.class })
	public void entityItemEdit(EntityItem item);
	@SuppressWarnings("rawtypes")
	@Event(handlers = { MultiLevelEntityEditorPresenter.class })
	public void entityItemAdded(EntityItem item);
	@Event(handlers = { MultiLevelEntityEditorPresenter.class })
	public void viewDocument(FileEntry fileEntry);
	@Event(handlers = { MultiLevelEntityEditorPresenter.class })
	public void editItem(Item item, MasterDetailComponent componentModel);
	@Event(handlers = { MultiLevelEntityEditorPresenter.class })
	public void reportItem(Object itemEntity);
	@Event(handlers = { MultiLevelEntityEditorPresenter.class })
	public void showPresenter(ConfigurableBasePresenter<?, ? extends EventBus> presenter);
	@Event(handlers = { MultiLevelEntityEditorPresenter.class })
	public void setItemDataSource(Item item);
	
	@Event(handlers = { EntityGridPresenter.class })
	public void createItem();
	@Event(handlers = { EntityGridPresenter.class })
	public void editItem();
	@Event(handlers = { EntityGridPresenter.class })
	public void deleteItem();
	@Event(handlers = { EntityGridPresenter.class })
	public void reportItem();
	@Event(handlers = { EntityGridPresenter.class })
	public void printGrid();
	
	@Event(handlers = { EntityGridHeaderPresenter.class })
	public void itemSelected();
	@Event(handlers = { EntityGridHeaderPresenter.class })
	public void itemsDepleted();
	
	@Event(handlers = { EntityFormPresenter.class })
	public void saveForm();
	@Event(handlers = { EntityFormPresenter.class })
	public void validateForm();
	@Event(handlers = { EntityFormPresenter.class })
	public void resetForm();
	@Event(handlers = { EntityFormPresenter.class })
	public void resizeForm(int newHeight);
	
	@Event(handlers = { EntityFormHeaderPresenter.class })
	public void formChanged();
	@Event(handlers = { EntityFormHeaderPresenter.class })
	public void formValidated();
}
