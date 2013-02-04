package org.flowframe.bpm.jbpm.ui.pageflow.vaadin.builder;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.flowframe.bpm.jbpm.ui.pageflow.services.IPageComponent;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.ext.form.VaadinCollapsibleConfirmActualsForm;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.ext.form.VaadinConfirmActualsForm;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.ext.grid.VaadinMatchGrid;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.ext.grid.VaadinMatchGrid.IMatchListener;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.ext.mvp.IVaadinDataComponent;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.editor.MasterSectionEventBus;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.editor.multilevel.MultiLevelEditorPresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.editor.multilevel.view.MultiLevelEditorView;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.EntityLineEditorEventBus;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.view.EntityLineEditorView;
import org.flowframe.ds.domain.DataSource;
import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityType;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityTypeAttribute;
import org.flowframe.kernel.common.mdm.domain.metamodel.PluralAttribute;
import org.flowframe.kernel.jpa.container.services.IDAOProvider;
import org.flowframe.kernel.jpa.container.services.IEntityContainerProvider;
import org.flowframe.kernel.metamodel.dao.services.IEntityTypeDAOService;
import org.flowframe.ui.component.domain.search.SearchGridComponent;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.vaadin.addons.common.FlowFrameVerticalSplitPanel;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.search.EntitySearchGrid;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.table.EntityEditorGrid;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.table.EntityEditorGrid.ISelectListener;
import org.flowframe.ui.vaadin.forms.impl.VaadinCollapsibleSectionForm;
import org.flowframe.ui.vaadin.forms.impl.VaadinForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.presenter.PresenterFactory;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;

public class VaadinPageDataBuilder {
	private static Logger logger = LoggerFactory.getLogger(VaadinPageDataBuilder.class);
	
	public static Set<Object> buildResultData(Component component) {
		HashSet<Object> dataSet = new HashSet<Object>();
		if (component instanceof IVaadinDataComponent) {
			Object data = ((IVaadinDataComponent) component).getData();
			if (data instanceof Collection) {
				dataSet.addAll((Collection<?>) data);
			} else if (data instanceof Object) {
				dataSet.add(data);
			}
		} else if (component instanceof EntitySearchGrid) {
			dataSet.add(((EntitySearchGrid) component).getSelectedEntity());
		} else if (component instanceof FlowFrameVerticalSplitPanel) {
			FlowFrameVerticalSplitPanel splitPanel = (FlowFrameVerticalSplitPanel) component;
			dataSet.addAll(buildResultData(splitPanel.getFirstComponent()));
			dataSet.addAll(buildResultData(splitPanel.getSecondComponent()));
		} else if (component instanceof VaadinConfirmActualsForm) {
			VaadinConfirmActualsForm form = (VaadinConfirmActualsForm) component;
			dataSet.add(form.getItemEntity());
		} else if (component instanceof VaadinCollapsibleConfirmActualsForm) {
			VaadinCollapsibleConfirmActualsForm form = (VaadinCollapsibleConfirmActualsForm) component;
			dataSet.add(form.getItemEntity());
		} else {
			if (component instanceof AbstractOrderedLayout) {
				Iterator<Component> iterator = ((AbstractOrderedLayout) component).getComponentIterator();
				Component nextComponent = null;
				while (iterator.hasNext()) {
					nextComponent = iterator.next();
					dataSet.addAll(buildResultData(nextComponent));
				}
			}
		}

		return dataSet;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Object> buildResultDataMap(Map<String, Object> parameterData, Collection<?> data,
			Map<Class<?>, String> resultKeyMap) {
		Set<String> parameterDataKeySet = parameterData.keySet();
		for (String parameterDataKey : parameterDataKeySet) {
			((Collection) data).add(parameterData.get(parameterDataKey));
		}

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Collection<Class<?>> types = resultKeyMap.keySet();
		for (Object dataObj : data) {
			for (Class<?> type : types) {
				if (dataObj != null && type != null) {
					if (type.isAssignableFrom(dataObj.getClass())) {
						resultMap.put(resultKeyMap.get(type), dataObj);
					}
				}
			}
		}

		return resultMap;
	}

	public static void applyParamData(Map<String, Object> config, Component component, Map<String, Object> params,
			PresenterFactory presenterFactory) throws Exception {
		if (component instanceof EntitySearchGrid) {
			applyParamDataToEntitySearchGrid(config, params, (EntitySearchGrid) component, presenterFactory);
		} else if (component instanceof VaadinConfirmActualsForm) {
			applyParamDataToVaadinConfirmActualsForm(config, params, (VaadinConfirmActualsForm) component, presenterFactory);
		} else if (component instanceof VaadinCollapsibleConfirmActualsForm) {
			applyParamDataToVaadinCollapsibleConfirmActualsForm(config, params, (VaadinCollapsibleConfirmActualsForm) component,
					presenterFactory);
		} else if (component instanceof VaadinCollapsibleSectionForm) {
			applyParamDataToVaadinCollapsibleSectionForm(config, params, (VaadinCollapsibleSectionForm) component, presenterFactory);
		} else if (component instanceof MultiLevelEditorView) {
			applyParamDataToMultiLevelEditorView(config, params, (MultiLevelEditorView) component);
		} else {
			// Component containers should always be picked last
			if (component instanceof AbstractComponentContainer) {
				applyParamDataToAbstractComponentContainer(config, params, (AbstractComponentContainer) component, presenterFactory);
			} else if (component instanceof TabSheet) {
				applyParamDataToTabSheet(config, params, (TabSheet) component, presenterFactory);
			} else if (component instanceof FlowFrameVerticalSplitPanel) {
				applyParamDataToFlowFrameVerticalSplitPanel(config, params, (FlowFrameVerticalSplitPanel) component, presenterFactory);
			}
		}
	}

	public static void applyItemDataSource(Component component, Container itemContainer, Item item,
			final PresenterFactory presenterFactory, Map<String, Object> config) throws Exception {
		applyItemDataSource(true, component, itemContainer, item, presenterFactory, config);
	}

	public static void applyItemDataSource(boolean isEventDeclaritive, Component component, Container itemContainer, Item item,
			final PresenterFactory presenterFactory, Map<String, Object> config) throws Exception {
		if (component instanceof FlowFrameVerticalSplitPanel) {
			applyItemDataSource(isEventDeclaritive, ((FlowFrameVerticalSplitPanel) component).getFirstComponent(), itemContainer, item,
					presenterFactory, config);
			applyItemDataSource(isEventDeclaritive, ((FlowFrameVerticalSplitPanel) component).getSecondComponent(), itemContainer, item,
					presenterFactory, config);
		} else if (component instanceof VaadinForm) {
			if (item instanceof BeanItem && itemContainer instanceof BeanItemContainer) {
				IDAOProvider daoProvider = (IDAOProvider) config.get(IPageComponent.DAO_PROVIDER);
				IEntityContainerProvider containerProvider = (IEntityContainerProvider) config
						.get(IPageComponent.ENTITY_CONTAINER_PROVIDER);

				if (daoProvider == null) {
					logger.error("[[[<<<((()))>>>]]] " +config.toString());
					throw new Exception("IDAOProvider was not supplied by the config map.");
				} else if (containerProvider == null) {
					throw new Exception("IEntityContainerProvider was not supplied by the config map.");
				}

				IEntityTypeDAOService entityTypeDao = daoProvider.provideByDAOClass(IEntityTypeDAOService.class);

				if (entityTypeDao == null) {
					throw new Exception("IEntityTypeDAOService was not supplied by the DAO Provider.");
				}

				EventBusManager ebm = null;
				if (config.get(IPageComponent.EVENT_BUS_MANAGER) != null) {
					ebm = (EventBusManager) config.get(IPageComponent.EVENT_BUS_MANAGER);
				} else {
					ebm = presenterFactory.getEventBusManager();
				}

				applyItemDataSourceToVaadinForm(isEventDeclaritive, (VaadinForm) component, (BeanItem<?>) item,
						(BeanItemContainer<?>) itemContainer, config, ebm);
			} else {
				throw new Exception(
						"Could not apply item data source to VaadinForm since item and itemContainer were not of type BeanItem and BeanItemContainer.");
			}
		} else if (component instanceof EntityLineEditorView) {
			// Halt the tree at this point
		} else if (component instanceof VaadinMatchGrid) {
			IEntityContainerProvider provider = (IEntityContainerProvider) config.get(IPageComponent.ENTITY_CONTAINER_PROVIDER);
			IDAOProvider daoProvider = (IDAOProvider) config.get(IPageComponent.DAO_PROVIDER);

			if (provider == null) {
				throw new Exception("IEntityContainerProvider was not supplied by the config map.");
			} else if (daoProvider == null) {
				throw new Exception("IDAOProvider was not supplied by the config map.");
			}

			applyItemDataSourceToVaadinMatchGrid(item, component, presenterFactory, provider, daoProvider);
		} else if (component instanceof EntityEditorGrid) {
			IEntityContainerProvider provider = (IEntityContainerProvider) config.get(IPageComponent.ENTITY_CONTAINER_PROVIDER);
			IDAOProvider daoProvider = (IDAOProvider) config.get(IPageComponent.DAO_PROVIDER);

			if (provider == null) {
				throw new Exception("IEntityContainerProvider was not supplied by the config map.");
			} else if (daoProvider == null) {
				throw new Exception("IDAOProvider was not supplied by the config map.");
			}

			applyItemDataSourceToVaadinMatchGrid(item, component, presenterFactory, provider, daoProvider);
		} else {
			if (component instanceof TabSheet) {
				Iterator<Component> componentIterator = ((TabSheet) component).getComponentIterator();
				while (componentIterator.hasNext()) {
					applyItemDataSource(isEventDeclaritive, componentIterator.next(), itemContainer, item, presenterFactory, config);
				}
			} else if (component instanceof AbstractComponentContainer) {
				Iterator<Component> componentIterator = ((AbstractComponentContainer) component).getComponentIterator();
				while (componentIterator.hasNext()) {
					applyItemDataSource(isEventDeclaritive, componentIterator.next(), itemContainer, item, presenterFactory, config);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static void applyItemDataSourceToVaadinMatchGrid(Item item, Component component, final PresenterFactory presenterFactory,
			IEntityContainerProvider containerProvider, final IDAOProvider daoProvider) throws ClassNotFoundException {
		@SuppressWarnings({ "rawtypes" })
		final Object itemBean = (item instanceof JPAContainerItem<?>) ? ((JPAContainerItem) item).getEntity()
				: (item instanceof BeanItem<?>) ? ((BeanItem) item).getBean() : null;
		((VaadinMatchGrid) component).setItemBean(itemBean);
		((VaadinMatchGrid) component).setDaoProvider(daoProvider);
		((VaadinMatchGrid) component).setFactory(presenterFactory);

		final BeanItemContainer<BaseEntity> matchedContainer = (BeanItemContainer<BaseEntity>) containerProvider
				.createBeanContainer(((VaadinMatchGrid) component).getMatchedContainerType());
		for (String nestedFieldName : ((VaadinMatchGrid) component).getComponentModel().getMatchedDataSource().getNestedFieldNames()) {
			matchedContainer.addNestedContainerProperty(nestedFieldName);
		}

		@SuppressWarnings("rawtypes")
		JPAContainer unmatchedContainer = (JPAContainer) containerProvider.createPersistenceContainer(((VaadinMatchGrid) component)
				.getUnmatchedContainerType());
		for (String nestedFieldName : ((VaadinMatchGrid) component).getComponentModel().getUnmatchedDataSource().getNestedFieldNames()) {
			unmatchedContainer.addNestedContainerProperty(nestedFieldName);
		}

		((VaadinMatchGrid) component).setUnMatchedContainer(unmatchedContainer);
		((VaadinMatchGrid) component).setMatchedContainer(matchedContainer);
		((VaadinMatchGrid) component).addListener(new IMatchListener() {

			@Override
			public void onUnmatch(Item matchedItemId) {
				EntityLineEditorEventBus eventBus = presenterFactory.getEventBusManager().getEventBus(EntityLineEditorEventBus.class);
				if (eventBus != null) {
					eventBus.setItemDataSource(null);
				}
			}

			@Override
			public void onMatch(Item matchedItem, Item matchedItemParent) {
				EntityLineEditorEventBus eventBus = presenterFactory.getEventBusManager().getEventBus(EntityLineEditorEventBus.class);
				if (eventBus != null) {
					eventBus.setItemDataSource(matchedItem, matchedContainer);
				}
			}
		});
		((VaadinMatchGrid) component).addListener(new ISelectListener() {

			@Override
			public void onSelect(Item item) {
				EntityLineEditorEventBus eventBus = presenterFactory.getEventBusManager().getEventBus(EntityLineEditorEventBus.class);
				if (eventBus != null) {
					eventBus.setItemDataSource(item, matchedContainer);
				}
			}
		});

		if (((VaadinMatchGrid) component).isDynamic()) {
//			try {
//				((VaadinMatchGrid) component).addParentConsumptionFilter(Filters.eq("status", RECEIVELINESTATUS.ARRIVING));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		} else {
//			if (ReceiveLine.class.isAssignableFrom(((VaadinMatchGrid) component).getUnmatchedContainerType())) {
//				try {
//					((VaadinMatchGrid) component).addParentConsumptionFilter(Filters.not(Filters.eq("status", RECEIVELINESTATUS.ARRIVED)));
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
		}
	}

	private static void applyParamDataToMultiLevelEditorView(Map<String, Object> config, Map<String, Object> params,
			MultiLevelEditorView view) throws Exception {
		IEntityContainerProvider provider = (IEntityContainerProvider) config.get(IPageComponent.ENTITY_CONTAINER_PROVIDER);

		if (provider == null) {
			throw new Exception("IEntityContainerProvider was not supplied by the config map.");
		}

		DataSource ds = ((MultiLevelEditorPresenter) view.getOwner()).getCurrentEditorComponentModel().getMasterComponent().getDataSource();
		Class<?> type = ds.getEntityType().getJavaType();
		Object formItemEntity = getParameterByClass(params, type);
		if (formItemEntity != null) {
			@SuppressWarnings("unchecked")
			BeanItemContainer<BaseEntity> container = (BeanItemContainer<BaseEntity>) provider.createBeanContainer(type);
			for (String nestedFieldName : ds.getNestedFieldNames()) {
				container.addNestedContainerProperty(nestedFieldName);
			}
			BeanItem<BaseEntity> baseEntity = container.addBean((BaseEntity) formItemEntity);
			((MultiLevelEditorPresenter) view.getOwner()).onSetItemDataSource(baseEntity, container);
		}
	}

	private static void applyParamDataToVaadinCollapsibleSectionForm(Map<String, Object> config, Map<String, Object> params,
			VaadinCollapsibleSectionForm form, PresenterFactory presenterFactory) throws Exception {
		IEntityContainerProvider provider = (IEntityContainerProvider) config.get(IPageComponent.ENTITY_CONTAINER_PROVIDER);

		if (provider == null) {
			throw new Exception("IEntityContainerProvider was not supplied by the config map.");
		}

		Class<?> type = form.getComponentModel().getDataSource().getEntityType().getJavaType();
		Object formItemEntity = getParameterByClass(params, type);
		if (formItemEntity != null) {
			@SuppressWarnings("unchecked")
			BeanItemContainer<BaseEntity> container = (BeanItemContainer<BaseEntity>) provider.createBeanContainer(type);
			for (String nestedFieldName : form.getComponentModel().getDataSource().getNestedFieldNames()) {
				container.addNestedContainerProperty(nestedFieldName);
			}
			BeanItem<BaseEntity> baseEntity = container.addBean((BaseEntity) formItemEntity);
			applyItemDataSourceToVaadinForm(true, form, baseEntity, container, config, presenterFactory.getEventBusManager());
		}
	}

	private static void applyParamDataToAbstractComponentContainer(Map<String, Object> config, Map<String, Object> params,
			AbstractComponentContainer layout, PresenterFactory presenterFactory) throws Exception {
		Iterator<Component> componentIterator = layout.getComponentIterator();
		while (componentIterator.hasNext()) {
			try {
				applyParamData(config, componentIterator.next(), params, presenterFactory);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void applyParamDataToTabSheet(Map<String, Object> config, Map<String, Object> params, TabSheet tabSheet,
			PresenterFactory presenterFactory) throws Exception {
		Iterator<Component> componentIterator = tabSheet.getComponentIterator();
		while (componentIterator.hasNext()) {
			try {
				applyParamData(config, componentIterator.next(), params, presenterFactory);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void applyParamDataToEntitySearchGrid(Map<String, Object> config, Map<String, Object> params,
			EntitySearchGrid searchGrid, PresenterFactory presenterFactory) throws Exception {
		IEntityContainerProvider provider = (IEntityContainerProvider) config.get(IPageComponent.ENTITY_CONTAINER_PROVIDER);

		if (provider == null) {
			throw new Exception("IEntityContainerProvider was not supplied by the config map.");
		}

		SearchGridComponent componentModel = searchGrid.getComponentModel();
		Container container = (Container) provider.createPersistenceContainer(componentModel.getDataSource().getEntityType().getJavaType());
		searchGrid.setContainerDataSource(container);
	}

	private static void applyParamDataToFlowFrameVerticalSplitPanel(Map<String, Object> config, Map<String, Object> params,
			FlowFrameVerticalSplitPanel splitPanel, PresenterFactory presenterFactory) throws Exception {
		try {
			applyParamData(config, splitPanel.getFirstComponent(), params, presenterFactory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			applyParamData(config, splitPanel.getSecondComponent(), params, presenterFactory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void applyParamDataToVaadinConfirmActualsForm(Map<String, Object> config, Map<String, Object> params,
			VaadinConfirmActualsForm form, PresenterFactory presenterFactory) throws Exception {
		IEntityContainerProvider provider = (IEntityContainerProvider) config.get(IPageComponent.ENTITY_CONTAINER_PROVIDER);

		if (provider == null) {
			throw new Exception("IEntityContainerProvider was not supplied by the config map.");
		}

		Class<?> type = form.getComponentModel().getDataSource().getEntityType().getJavaType();
		Object formItemEntity = getParameterByClass(params, type);
		if (formItemEntity != null) {
			@SuppressWarnings("unchecked")
			BeanItemContainer<BaseEntity> container = (BeanItemContainer<BaseEntity>) provider.createBeanContainer(type);
			for (String nestedFieldName : form.getComponentModel().getDataSource().getNestedFieldNames()) {
				container.addNestedContainerProperty(nestedFieldName);
			}
			BeanItem<BaseEntity> baseEntity = container.addBean((BaseEntity) formItemEntity);
			applyItemDataSourceToVaadinForm(true, form, baseEntity, container, config, presenterFactory.getEventBusManager());
		}
	}

	private static void applyParamDataToVaadinCollapsibleConfirmActualsForm(Map<String, Object> config, Map<String, Object> params,
			VaadinCollapsibleConfirmActualsForm form, PresenterFactory presenterFactory) throws Exception {
		IEntityContainerProvider provider = (IEntityContainerProvider) config.get(IPageComponent.ENTITY_CONTAINER_PROVIDER);

		if (provider == null) {
			throw new Exception("IEntityContainerProvider was not supplied by the config map.");
		}

		Class<?> type = form.getComponentModel().getDataSource().getEntityType().getJavaType();
		Object formItemEntity = getParameterByClass(params, type);
		if (formItemEntity != null) {
			@SuppressWarnings("unchecked")
			BeanItemContainer<BaseEntity> container = (BeanItemContainer<BaseEntity>) provider.createBeanContainer(type);
			for (String nestedFieldName : form.getComponentModel().getDataSource().getNestedFieldNames()) {
				container.addNestedContainerProperty(nestedFieldName);
			}
			BeanItem<BaseEntity> baseEntity = container.addBean((BaseEntity) formItemEntity);
			applyItemDataSourceToVaadinForm(true, form, baseEntity, container, config, presenterFactory.getEventBusManager());
		}
	}

	/**************************************************************************/
	/**************************** UTILITY METHODS *****************************/
	/**************************************************************************/
	private static void applyItemDataSourceToVaadinForm(boolean isEventDeclaritive, VaadinForm form, BeanItem<?> item,
			BeanItemContainer<?> container, Map<String, Object> config, EventBusManager ebm) throws Exception {
		IDAOProvider daoProvider = (IDAOProvider) config.get(IPageComponent.DAO_PROVIDER);
		IEntityContainerProvider containerProvider = (IEntityContainerProvider) config.get(IPageComponent.ENTITY_CONTAINER_PROVIDER);

		if (daoProvider == null) {
			throw new Exception("IDAOProvider was not supplied by the config map.");
		} else if (containerProvider == null) {
			throw new Exception("IEntityContainerProvider was not supplied by the config map.");
		}

		IEntityTypeDAOService entityTypeDao = daoProvider.provideByDAOClass(IEntityTypeDAOService.class);

		if (entityTypeDao == null) {
			throw new Exception("IEntityTypeDAOService was not supplied by the DAO Provider.");
		}

		form.setItemDataSource(item, item.getItemPropertyIds(), entityTypeDao, container, containerProvider);

		String title = typeToTitle(item.getBean().getClass());
		if (item.getItemProperty("name") != null && item.getItemProperty("name").getValue() != null) {
			title += " (" + item.getItemProperty("name").getValue().toString() + ")";
		}
		form.setTitle(title);

		if (isEventDeclaritive) {
			EntityLineEditorEventBus entityLineEditorEventBus = ebm.getEventBus(EntityLineEditorEventBus.class);
			if (entityLineEditorEventBus != null) {
				entityLineEditorEventBus.setItemDataSource(item, container);
			}
		}
	}

	private static Object getParameterByClass(Map<String, Object> params, Class<?> type) {
		Collection<String> paramKeys = params.keySet();
		Object paramEntry = null;
		for (String paramKey : paramKeys) {
			paramEntry = params.get(paramKey);
			if (paramEntry != null) {
				if (type.isAssignableFrom(paramEntry.getClass())) {
					return paramEntry;
				}
			}
		}

		return null;
	}

	private static Map<Class<?>, Collection<Object>> buildParamInstanceMap(Object[] parentInstances) {
		HashMap<Class<?>, Collection<Object>> paramInstanceMap = new HashMap<Class<?>, Collection<Object>>();
		for (Object parentInstance : parentInstances) {
			if (parentInstance != null) {
				Collection<Object> collection = paramInstanceMap.get(parentInstance.getClass());
				if (collection == null) {
					collection = new LinkedList<Object>();
					paramInstanceMap.put(parentInstance.getClass(), collection);
				}

				if (!collection.contains(parentInstance)) {
					collection.add(parentInstance);
				}
			}
		}
		return paramInstanceMap;
	}

	// FIXME Add an applyItemDataSource impl for master section grids
	@SuppressWarnings("unused")
	private static PluralAttribute provideGridAttribute(Class<?> propertyType, Object bean, IDAOProvider daoProvider) throws Exception {
		PluralAttribute gridAttribute = null;
		EntityType beanEntityType = daoProvider.provideByDAOClass(IEntityTypeDAOService.class).provide(bean.getClass()), attributeType = null;
		Set<EntityTypeAttribute> beanAttributes = beanEntityType.getAllDeclaredAttributes();
		for (EntityTypeAttribute attribute : beanAttributes) {
			if (attribute.getAttribute() instanceof PluralAttribute) {
				attributeType = attribute.getAttribute().getEntityType();
				if (attributeType != null) {
					if (propertyType.isAssignableFrom(attributeType.getJavaType())) {
						gridAttribute = (PluralAttribute) attribute.getAttribute();
						return gridAttribute;
					}
				}
			}
		}
		return gridAttribute;
	}

	public static void saveNewInstance(Object instance, IDAOProvider daoProvider, EventBusManager eventBusManager,
			Map<String, Object> config, Object... parentInstances) throws Exception {
		if (instance instanceof BaseEntity && ((BaseEntity) instance).getId() == null) {
			MultiLevelEditorPresenter mlePresenter = (MultiLevelEditorPresenter) config
					.get(IComponentFactory.FACTORY_PARAM_MVP_CURRENT_MLENTITY_EDITOR_PRESENTER);
			if (mlePresenter == null) {
				throw new Exception("The MLE presenter was null, so the entity could not be saved.");
			}

			Item currentEditorItemDataSource = mlePresenter.getCurrentItemDataSource();
			Object bean = null;
			if (currentEditorItemDataSource instanceof BeanItem<?>) {
				bean = ((BeanItem<?>) currentEditorItemDataSource).getBean();
			} else if (currentEditorItemDataSource instanceof JPAContainerItem<?>) {
				bean = ((JPAContainerItem<?>) currentEditorItemDataSource).getEntity();
			} else {
				throw new Exception("The current MLE presenter's item datasource was the wrong type.");
			}

			if (bean == null) {
				throw new Exception("The bean of the MLE presenter's item datasource was null.");
			} else {
				instance = saveInstance(instance, daoProvider, bean);
			}

			MasterSectionEventBus masterSectionEventBus = eventBusManager.getEventBus(MasterSectionEventBus.class);
			if (masterSectionEventBus != null) {
				masterSectionEventBus.addNewBeanItem(instance);
			} else {
				throw new Exception("EntityLineEditorEventBus could not be fetched from the event bus manager.");
			}
		}
	}

	public static <T> T saveInstance(T instance, IDAOProvider daoProvider, Object... parentInstances) throws Exception {
		throw new UnsupportedOperationException("saveInstance has not been implemented for FlowFrame yet.");
	}

	private static String typeToTitle(Class<?> type) {
		String simpleName = type.getSimpleName(), title = "";
		String[] sections = simpleName.split("(?=\\p{Upper})");
		boolean isFirst = true;
		for (String section : sections) {
			if (!isFirst) {
				if (section.length() > 1) {
					title += " ";
				}
			}
			title += section;
			if (!"".equals(section)) {
				isFirst = false;
			}
		}
		return title;
	}
}
