package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.refnum;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.conx.logistics.kernel.ui.components.domain.referencenumber.ReferenceNumberEditorComponent;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.ConfigurableBasePresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.refnum.view.IReferenceNumberEditorView;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.refnum.view.ReferenceNumberEditorView;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.refnum.view.ReferenceNumberEditorView.ICreateReferenceNumberListener;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.refnum.view.ReferenceNumberEditorView.ISaveReferenceNumberListener;
import com.conx.logistics.kernel.ui.factory.services.IEntityEditorFactory;
import com.conx.logistics.kernel.ui.forms.vaadin.FormMode;
import com.conx.logistics.kernel.ui.service.contribution.IMainApplication;
import com.conx.logistics.mdm.dao.services.IEntityMetadataDAOService;
import com.conx.logistics.mdm.domain.BaseEntity;
import com.conx.logistics.mdm.domain.metadata.DefaultEntityMetadata;
import com.conx.logistics.mdm.domain.referencenumber.ReferenceNumber;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;
import com.vaadin.data.Item;

@Presenter(view = ReferenceNumberEditorView.class)
public class ReferenceNumberEditorPresenter extends ConfigurableBasePresenter<IReferenceNumberEditorView, ReferenceNumberEditorEventBus> implements ICreateReferenceNumberListener, ISaveReferenceNumberListener {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private boolean initialized = false;
	// private JPAContainer<ReferenceNumber> entityContainer;
	private Set<String> visibleFieldNames;
	// private AbstractComponent refNumEditorComponent;
	// private EntityManager entityManager;
	private DefaultEntityMetadata defaultMetadata;
	// private ConfigurableBasePresenter<IMultiLevelEntityEditorView,
	// MultiLevelEntityEditorEventBus> mainEventBus;
	private List<String> formVisibleFieldNames;
	private Item newEntityItem;
	// private IEntityMetadataDAOService entityMetadataDAO;
	private BaseEntity entity;
	private IMainApplication mainApplication;
	private ReferenceNumberEditorComponent refNumComponent;
	@SuppressWarnings({ "unused", "rawtypes" })
	private ConfigurableBasePresenter multiLevelEntityEditorPresenter;
	private JPAContainer<ReferenceNumber> entityContainer;
	private IEntityMetadataDAOService entityMetadataDAO;

	@SuppressWarnings("unchecked")
	private void initialize() {
		this.getView().init();
		this.entityContainer = (JPAContainer<ReferenceNumber>) this.mainApplication.createPersistenceContainer(ReferenceNumber.class);
		this.visibleFieldNames = refNumComponent.getDataSource().getVisibleFieldNames();
		this.formVisibleFieldNames = Arrays.asList("value", "type");
		Set<String> nestedFieldNames = refNumComponent.getDataSource().getNestedFieldNames();
		for (String npp : nestedFieldNames) {
			entityContainer.addNestedContainerProperty(npp);
		}
		this.getView().addCreateReferenceNumberListener(this);
		this.getView().addSaveReferenceNumberListener(this);
		this.getView().setContainerDataSource(entityContainer, visibleFieldNames, formVisibleFieldNames);
		this.getView().showContent();
		this.setInitialized(true);
	}

	// MultiLevelEntityEditorEventBus implementation
	public void onEntityItemEdit(EntityItem<?> item) {
		this.entity = (BaseEntity) item.getEntity();
		try {
			this.defaultMetadata = entityMetadataDAO.provide(entity.getClass());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

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

	public void onEntityItemAdded(EntityItem<?> item) {
		this.entityContainer.refresh();
	}

	public void onNoteItemAdded(ReferenceNumber ni) {
		this.entityContainer.addEntity(ni);
	}

	@Override
	public void bind() {
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void configure() {
		// this.mainEventBus =
		// (ConfigurableBasePresenter<IMultiLevelEntityEditorView,
		// MultiLevelEntityEditorEventBus>)
		// getConfig().get("multiLevelEntityEditorPresenter");
		// this.refNumEditorComponent = (ReferenceNumberEditorComponent)
		// getConfig().get("componentModel");
		// this.entityManager = (EntityManager) getConfig().get("em");
		// this.entityMetadataDAO = (IEntityMetadataDAOService)
		// getConfig().get("ientityMetadataDAOService");
		// this.entityContainer =
		// JPAContainerFactory.make(ReferenceNumber.class, entityManager);
		this.multiLevelEntityEditorPresenter = (ConfigurableBasePresenter) getConfig().get(IEntityEditorFactory.FACTORY_PARAM_MVP_CURRENT_MLENTITY_EDITOR_PRESENTER);
		this.refNumComponent = (ReferenceNumberEditorComponent) getConfig().get(IEntityEditorFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL);
		this.mainApplication = (IMainApplication) getConfig().get(IEntityEditorFactory.FACTORY_PARAM_MAIN_APP);
		this.entityMetadataDAO = (IEntityMetadataDAOService) getConfig().get(IEntityEditorFactory.FACTORY_PARAM_IENTITY_METADATA_SERVICE);
	}

	private void updateQueryFilter() {
		this.entityContainer.getEntityProvider().setQueryModifierDelegate(new DefaultQueryModifierDelegate() {
			@Override
			public void filtersWillBeAdded(CriteriaBuilder criteriaBuilder, CriteriaQuery<?> query, List<Predicate> predicates) {
				Root<?> referenceNumberRoot = query.getRoots().iterator().next();

				Path<DefaultEntityMetadata> metaData = referenceNumberRoot.<DefaultEntityMetadata> get("entityMetadata");
				Path<Long> metaDataId = metaData.get("id");
				Path<Long> pk = referenceNumberRoot.<Long> get("entityPK");
				Predicate predicate = criteriaBuilder.and(criteriaBuilder.equal(metaDataId, ReferenceNumberEditorPresenter.this.defaultMetadata.getId()),
						criteriaBuilder.equal(pk, ReferenceNumberEditorPresenter.this.entity.getId()));
				predicates.add(predicate);
			}
		});
		this.entityContainer.applyFilters();
	}

	@Override
	public void onSaveReferenceNumber(Item item) {
		this.newEntityItem = null;
		this.getView().hideDetail();
		
	}

	@Override
	public void onCreateReferenceNumber() {
		this.newEntityItem = entityContainer.createEntityItem(new ReferenceNumber());
		this.getView().setItemDataSource(newEntityItem, FormMode.CREATING);
		this.getView().showDetail();
	}
}
