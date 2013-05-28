package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.grid;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.ui.component.domain.table.GridComponent;
import org.flowframe.ui.services.contribution.IMainApplication;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.table.EntityEditorGrid.IEditListener;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.table.EntityEditorGrid.ISelectListener;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.ConfigurableBasePresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.MultiLevelEntityEditorEventBus;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.MultiLevelEntityEditorPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.grid.view.EntityLineEditorGridView;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.lineeditor.section.grid.view.IEntityLineEditorGridView;
import org.flowframe.ui.vaadin.expressions.utils.SPELUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;

@Presenter(view = EntityLineEditorGridView.class)
public class EntityLineEditorGridPresenter extends ConfigurableBasePresenter<IEntityLineEditorGridView, EntityLineEditorGridEventBus> implements IEditListener, ISelectListener {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private boolean initialized = false;
	private MultiLevelEntityEditorPresenter parentPresenter;
	private JPAContainer<?> entityContainer;
	private MultiLevelEntityEditorEventBus entityEditorEventListener;
	private IMainApplication mainApplication;
	private GridComponent tableComponent;
	private MultiLevelEntityEditorPresenter multiLevelEntityEditorPresenter;
	private Class<?> entityClass;
	private EntityItem<?> parentEntityItem;
	private Filter defaultFilter;

	public EntityLineEditorGridPresenter() {
	}

	public MultiLevelEntityEditorPresenter getParentPresenter() {
		return parentPresenter;
	}

	public void setParentPresenter(MultiLevelEntityEditorPresenter parentPresenter) {
		this.parentPresenter = parentPresenter;
	}

	private void initialize() {
		this.getView().init();

		String[] visibleFieldNames = this.tableComponent.getDataSource().getVisibleFieldNames().toArray(new String[0]);

		this.getView().init();
		this.getView().setContainerDataSource(this.entityContainer);
		this.getView().setVisibleColumns(visibleFieldNames);
		this.getView().addEditListener(this);
		this.getView().addSelectListener(this);

		// -- Done
		this.setInitialized(true);
	}

	@Override
	public void bind() {
		// initialize();
	}

	@SuppressWarnings("rawtypes")
	public void onEntityItemAdded(EntityItem item) {
	}

	@SuppressWarnings("rawtypes")
	public void onEntityItemEdit(EntityItem item) {
		this.parentEntityItem = item;
		if (!isInitialized()) {
			try {
				initialize();
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String stacktrace = sw.toString();
				logger.error(stacktrace);
			}
		}
		updateQueryFilter();
	}

	private void updateQueryFilter() {
		if (this.tableComponent.getDataSource().getDefaultFilterExpression() == null) {
			this.entityContainer.getEntityProvider().setQueryModifierDelegate(new DefaultQueryModifierDelegate() {
				@Override
				public void filtersWillBeAdded(CriteriaBuilder criteriaBuilder, CriteriaQuery<?> query, List<Predicate> predicates) {
					Root<?> lineEntityRoot = query.getRoots().iterator().next();
					Path<Object> ownerIdPath = lineEntityRoot.get("ownerEntityId");
					predicates.add(criteriaBuilder.equal(ownerIdPath, ((BaseEntity) EntityLineEditorGridPresenter.this.parentEntityItem.getEntity()).getId()));
				}
			});
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", ((BaseEntity) EntityLineEditorGridPresenter.this.parentEntityItem.getEntity()).getId());
			params.put("instance", EntityLineEditorGridPresenter.this.parentEntityItem.getEntity());
			
			this.defaultFilter = SPELUtil.toContainerFilter(this.tableComponent.getDataSource().getDefaultFilterExpression(), params);
			this.entityContainer.addContainerFilter(this.defaultFilter);
		}
		this.entityContainer.applyFilters();
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	@Override
	public void configure() {
		this.tableComponent = (GridComponent) getConfig().get(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL);
		this.multiLevelEntityEditorPresenter = (MultiLevelEntityEditorPresenter) getConfig().get(IComponentFactory.FACTORY_PARAM_MVP_CURRENT_MLENTITY_EDITOR_PRESENTER);
		this.mainApplication = (IMainApplication) getConfig().get(IComponentFactory.FACTORY_PARAM_MAIN_APP);
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
		entityEditorEventListener.editItem((JPAContainerItem<?>) item, this.tableComponent.getRecordEditor());
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
}
