package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.search.grid;

import java.util.Set;

import org.flowframe.ui.component.domain.table.GridComponent;
import org.flowframe.ui.services.contribution.IMainApplication;
import org.flowframe.ui.services.factory.IEntityEditorFactory;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.table.EntityEditorGrid.IDepletedListener;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.table.EntityEditorGrid.IEditListener;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.table.EntityEditorGrid.ISelectListener;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.ConfigurableBasePresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.MultiLevelEntityEditorEventBus;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.MultiLevelEntityEditorPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.search.grid.view.EntityGridView;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.search.grid.view.IEntityGridView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;

@Presenter(view = EntityGridView.class)
public class EntityGridPresenter extends ConfigurableBasePresenter<IEntityGridView, EntityGridEventBus> implements IEditListener, ISelectListener, IDepletedListener {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private boolean initialized = false;
	private MultiLevelEntityEditorPresenter parentPresenter;
	private JPAContainer<?> entityContainer;
	private MultiLevelEntityEditorEventBus entityEditorEventListener;
	private IMainApplication mainApplication;
	private GridComponent tableComponent;
	private MultiLevelEntityEditorPresenter multiLevelEntityEditorPresenter;
	private Class<?> entityClass;

	public EntityGridPresenter() {
	}

	public MultiLevelEntityEditorPresenter getParentPresenter() {
		return parentPresenter;
	}

	public void setParentPresenter(MultiLevelEntityEditorPresenter parentPresenter) {
		this.parentPresenter = parentPresenter;
	}

	private void initialize() {
		String[] visibleFieldNames = this.tableComponent.getDataSource().getVisibleFieldNames().toArray(new String[0]);
		String[] visibleFieldTitles = this.tableComponent.getDataSource().getVisibleFieldTitles().toArray(new String[0]);
		
		this.getView().init();
		this.getView().setContainerDataSource(this.entityContainer);
		this.getView().setVisibleColumns(visibleFieldNames);
		this.getView().setVisibleColumnNames(visibleFieldTitles);
		this.getView().addEditListener(this);
		this.getView().addSelectListener(this);
		this.getView().addDepletedListener(this);

		// -- Done
		this.setInitialized(true);
	}

	@Override
	public void bind() {
		initialize();
	}

	@SuppressWarnings("rawtypes")
	public void onEntityItemAdded(EntityItem item) {
		// this.entityContainer.refresh();
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	@Override
	public void configure() {
		this.tableComponent = (GridComponent) getConfig().get(IEntityEditorFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL);
		this.multiLevelEntityEditorPresenter = (MultiLevelEntityEditorPresenter) getConfig().get(IEntityEditorFactory.FACTORY_PARAM_MVP_CURRENT_MLENTITY_EDITOR_PRESENTER);
		this.mainApplication = (IMainApplication) getConfig().get(IEntityEditorFactory.FACTORY_PARAM_MAIN_APP);
		this.entityEditorEventListener = multiLevelEntityEditorPresenter.getEventBus();
		try {
			this.entityClass = this.tableComponent.getDataSource().getEntityType().getJavaType();
			this.entityContainer = (JPAContainer<?>) mainApplication.createPersistenceContainer(this.entityClass);
			Set<String> nestedFieldNames = this.tableComponent.getDataSource().getNestedFieldNames();
			for (String nestedFieldName : nestedFieldNames) {
				this.entityContainer.addNestedContainerProperty(nestedFieldName);
			}
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onSelect(Item item) {
		entityEditorEventListener.entityItemEdit((JPAContainerItem<?>) item);
		entityEditorEventListener.itemSelected();
	}

	@Override
	public void onEdit(Item item) {
		if (this.tableComponent != null && this.tableComponent.getRecordEditor() != null) {
			entityEditorEventListener.editItem(item, this.tableComponent.getRecordEditor());
		}
	}

	public void onDelete(Item item) throws Exception {
		this.getView().deleteItem(item);
	}

	public void onCreateItem() {

	}

	public void onEditItem() {
		Item item = this.getView().getSelectedItem();
		if (this.tableComponent != null && this.tableComponent.getRecordEditor() != null && item != null) {
			entityEditorEventListener.editItem(item, this.tableComponent.getRecordEditor());
		}
	}
	
	public void onReportItem() {
		Item item = this.getView().getSelectedItem();
		if (this.tableComponent != null && item != null) {
			assert (item instanceof BeanItem || item instanceof JPAContainerItem) : "The item was not compatible.";
			if (item instanceof BeanItem) {
				entityEditorEventListener.reportItem(((BeanItem<?>) item).getBean());
			} else if (item instanceof JPAContainerItem) {
				entityEditorEventListener.reportItem(((JPAContainerItem<?>) item).getEntity());
			}
		}
	}

	public void onDeleteItem() {
		Item item = this.getView().getSelectedItem();
		if (item != null) {
			try {
				this.getView().deleteItem(item);
			} catch (Exception e) {
				//TODO Handle Exception
				e.printStackTrace();
			}
		}
	}

	public void onPrintGrid() {
		this.getView().printGrid();
	}

	@Override
	public void onDepleted() {
		entityEditorEventListener.itemsDepleted();
	}

}
