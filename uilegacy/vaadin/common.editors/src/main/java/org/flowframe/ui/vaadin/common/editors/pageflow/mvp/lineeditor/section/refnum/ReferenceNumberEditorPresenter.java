package org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.refnum;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.flowframe.bpm.jbpm.ui.pageflow.services.IPageComponent;
import org.flowframe.ui.vaadin.common.editors.pageflow.ext.mvp.IConfigurablePresenter;
import org.flowframe.ui.vaadin.common.editors.pageflow.ext.mvp.lineeditor.section.ILineEditorSectionContentPresenter;
import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.refnum.view.IReferenceNumberEditorView;
import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.refnum.view.ReferenceNumberEditorView;
import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.refnum.view.ReferenceNumberEditorView.ICreateReferenceNumberListener;
import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.refnum.view.ReferenceNumberEditorView.ISaveReferenceNumberListener;
import org.flowframe.kernel.common.mdm.dao.services.IEntityMetadataDAOService;
import org.flowframe.kernel.common.mdm.dao.services.referencenumber.IReferenceNumberDAOService;
import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.mdm.domain.metadata.DefaultEntityMetadata;
import org.flowframe.kernel.common.mdm.domain.referencenumber.ReferenceNumber;
import org.flowframe.kernel.jpa.container.services.IDAOProvider;
import org.flowframe.kernel.jpa.container.services.IEntityContainerProvider;
import org.flowframe.ui.component.domain.referencenumber.ReferenceNumberEditorComponent;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.vaadin.forms.FormMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;

@Presenter(view = ReferenceNumberEditorView.class)
public class ReferenceNumberEditorPresenter extends BasePresenter<IReferenceNumberEditorView, ReferenceNumberEditorEventBus> implements
		ICreateReferenceNumberListener, ISaveReferenceNumberListener, ILineEditorSectionContentPresenter, IConfigurablePresenter {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private boolean initialized = false;
	private Collection<String> visibleFieldNames;
	private DefaultEntityMetadata defaultMetadata;
	private List<String> formVisibleFieldNames;
	private Item newEntityItem;
	private BaseEntity entity;
	private ReferenceNumberEditorComponent refNumComponent;
	private JPAContainer<ReferenceNumber> entityContainer;
	private IEntityContainerProvider entityContainerProvider;
	private IDAOProvider daoProvider;

	@SuppressWarnings("unchecked")
	private void initialize() {
		this.entityContainer = (JPAContainer<ReferenceNumber>) this.entityContainerProvider.createNonCachingPersistenceContainer(ReferenceNumber.class);
		this.visibleFieldNames = this.refNumComponent.getDataSource().getVisibleFieldNames();
		this.formVisibleFieldNames = Arrays.asList("value", "type");
		Set<String> nestedFieldNames = refNumComponent.getDataSource().getNestedFieldNames();
		for (String npp : nestedFieldNames) {
			entityContainer.addNestedContainerProperty(npp);
		}

		this.getView().init();
		this.getView().addCreateReferenceNumberListener(this);
		this.getView().addSaveReferenceNumberListener(this);
		this.getView().setContainerDataSource(entityContainer, visibleFieldNames, formVisibleFieldNames);
		this.getView().showContent();
		this.setInitialized(true);
	}

	@SuppressWarnings("rawtypes")
	private Object getBean(Item item) {
		if (item instanceof EntityItem) {
			return ((EntityItem) item).getEntity();
		} else if (item instanceof BeanItem) {
			return ((BeanItem) item).getBean();
		} else {
			return null;
		}
	}

	@Override
	public void onSetItemDataSource(Item item, Container... container) throws Exception {
		Object bean = getBean(item);
		if (bean instanceof BaseEntity) {
			this.entity = (BaseEntity) bean;
			assert (this.entity.getId() != null) : "The item data source entity id was null";
			
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setName("pageflow.ui.data");
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			TransactionStatus status = this.daoProvider.provideByDAOClass(PlatformTransactionManager.class).getTransaction(def);
			
			this.defaultMetadata = null;
			try {
				this.defaultMetadata = this.daoProvider.provideByDAOClass(IEntityMetadataDAOService.class).provide(this.entity.getClass());
				this.daoProvider.provideByDAOClass(PlatformTransactionManager.class).commit(status);
			} catch (Exception e) {
				e.printStackTrace();
				this.daoProvider.provideByDAOClass(PlatformTransactionManager.class).rollback(status);
			}
			assert (this.defaultMetadata != null) : "The item data source entity metadata was null";
			
			if (!isInitialized()) {
				initialize();
			}
			
			updateQueryFilter();
		}
	}

	private void updateQueryFilter() {
		this.entityContainer.getEntityProvider().setQueryModifierDelegate(new DefaultQueryModifierDelegate() {
			@Override
			public void filtersWillBeAdded(CriteriaBuilder criteriaBuilder, CriteriaQuery<?> query, List<Predicate> predicates) {
				Root<?> referenceNumberRoot = query.getRoots().iterator().next();

				Path<Long> metaDataId = referenceNumberRoot.<DefaultEntityMetadata> get("entityMetadata").get("id");
				Path<Long> pk = referenceNumberRoot.<Long> get("entityPK");
				Predicate predicate = criteriaBuilder.and(
						criteriaBuilder.equal(metaDataId, ReferenceNumberEditorPresenter.this.defaultMetadata.getId()),
						criteriaBuilder.equal(pk, ReferenceNumberEditorPresenter.this.entity.getId()));
				predicates.add(predicate);
			}
		});
		this.entityContainer.applyFilters();
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	@Override
	public void onConfigure(Map<String, Object> params) {
		this.refNumComponent = (ReferenceNumberEditorComponent) params.get(IComponentFactory.COMPONENT_MODEL);
		this.entityContainerProvider = (IEntityContainerProvider) params.get(IComponentFactory.CONTAINER_PROVIDER);
		this.daoProvider = (IDAOProvider) params.get(IPageComponent.DAO_PROVIDER);
	}

	@Override
	public void onSaveReferenceNumber(Item item) {
		this.newEntityItem = null;
		this.entityContainer.refresh();
		this.getView().hideDetail();
	}

	@Override
	public void onCreateReferenceNumber() {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName("pageflow.ui.data");
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = this.daoProvider.provideByDAOClass(PlatformTransactionManager.class).getTransaction(def);
		
		ReferenceNumber referenceNumber = null;
		try {
			referenceNumber = this.daoProvider.provideByDAOClass(IReferenceNumberDAOService.class).add(this.entity.getId(), this.entity.getClass());
			this.daoProvider.provideByDAOClass(PlatformTransactionManager.class).commit(status);
		} catch (Exception e) {
			e.printStackTrace();
			this.daoProvider.provideByDAOClass(PlatformTransactionManager.class).rollback(status);
		}
		
		assert (referenceNumber != null) : "The new reference number was null";
		
		this.newEntityItem = this.entityContainer.getItem(referenceNumber.getId());
		this.getView().setItemDataSource(newEntityItem, FormMode.CREATING);
		this.getView().showDetail();
	}

	@Override
	public void subscribe(EventBusManager eventBusManager) {
	}
}
