package org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.attachment;

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
import org.flowframe.ui.vaadin.common.editors.pageflow.builder.VaadinPageDataBuilder;
import org.flowframe.ui.vaadin.common.editors.pageflow.ext.mvp.IConfigurablePresenter;
import org.flowframe.ui.vaadin.common.editors.pageflow.ext.mvp.lineeditor.section.ILineEditorSectionContentPresenter;
import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.attachment.view.AttachmentEditorView;
import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.attachment.view.AttachmentEditorView.ICreateAttachmentListener;
import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.attachment.view.AttachmentEditorView.IInspectAttachmentListener;
import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.attachment.view.AttachmentEditorView.ISaveAttachmentListener;
import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.attachment.view.IAttachmentEditorView;
import org.flowframe.documentlibrary.remote.services.IRemoteDocumentRepository;
import org.flowframe.kernel.common.mdm.dao.services.documentlibrary.IFolderDAOService;
import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.DocType;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;
import org.flowframe.kernel.jpa.container.services.IDAOProvider;
import org.flowframe.kernel.jpa.container.services.IEntityContainerProvider;
import org.flowframe.ui.component.domain.attachment.AttachmentEditorComponent;
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
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@Presenter(view = AttachmentEditorView.class)
public class AttachmentEditorPresenter extends BasePresenter<IAttachmentEditorView, AttachmentEditorEventBus> implements ICreateAttachmentListener,
		ISaveAttachmentListener, IInspectAttachmentListener, ILineEditorSectionContentPresenter, IConfigurablePresenter {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private boolean initialized = false;
	private JPAContainer<FileEntry> entityContainer;
	private Collection<String> visibleFieldNames;
	private Folder docFolder;
	private List<String> formVisibleFieldNames;
	private EntityItem<FileEntry> newEntityItem;
	private AttachmentEditorComponent attachmentComponent;
	private IEntityContainerProvider entityContainerProvider;
	private IDAOProvider daoProvider;
	private EventBusManager sectionEventBusManager;
	private VaadinPageDataBuilder pageDataBuilder;

	@SuppressWarnings("unchecked")
	private void initialize() {
		this.entityContainer = (JPAContainer<FileEntry>) this.entityContainerProvider.createNonCachingPersistenceContainer(FileEntry.class);
		this.visibleFieldNames = attachmentComponent.getDataSource().getVisibleFieldNames();
		this.formVisibleFieldNames = Arrays.asList("docType");
		Set<String> nestedFieldNames = attachmentComponent.getDataSource().getNestedFieldNames();
		for (String npp : nestedFieldNames) {
			entityContainer.addNestedContainerProperty(npp);
		}
		this.getView().init();
		this.getView().addCreateAttachmentListener(this);
		this.getView().addSaveAttachmentListener(this);
		this.getView().addInspectAttachmentListener(this);
		this.getView().setContainerDataSource(entityContainer, visibleFieldNames, formVisibleFieldNames);
		this.getView().showContent();
		
		this.pageDataBuilder = new VaadinPageDataBuilder();
		
		this.setInitialized(true);
	}

	@Override
	public void onSetItemDataSource(Item item, Container... container) throws Exception {
		BaseEntity entity = null;
		if (item instanceof BeanItem) {
			entity = (BaseEntity) ((BeanItem<?>) item).getBean();
		} else if (item instanceof EntityItem) {
			entity = (BaseEntity) ((EntityItem<?>) item).getEntity();
		}
		
		assert (entity != null) : "The entity of the item data source was null.";

		if (entity.getDocFolder() == null) {
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setName("pageflow.ui.data");
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			TransactionStatus status = this.daoProvider.provideByDAOClass(PlatformTransactionManager.class).getTransaction(def);
			
			try {
				entity = this.daoProvider.provideByDAOClass(IRemoteDocumentRepository.class).provideFolderForEntity(entity);
				this.docFolder = entity.getDocFolder();
				this.daoProvider.provideByDAOClass(PlatformTransactionManager.class).commit(status);
			} catch (Exception e) {
				e.printStackTrace();
				this.daoProvider.provideByDAOClass(PlatformTransactionManager.class).rollback(status);
			}
		} else {
			this.docFolder = entity.getDocFolder();
		}
		
		if (!initialized && this.docFolder != null) {
			initialize();
		}
		
		updateQueryFilter();
	}

	private void updateQueryFilter() {
		this.entityContainer.removeAllContainerFilters();
		this.entityContainer.getEntityProvider().setQueryModifierDelegate(new DefaultQueryModifierDelegate() {
			@Override
			public void filtersWillBeAdded(CriteriaBuilder criteriaBuilder, CriteriaQuery<?> query, List<Predicate> predicates) {
				// FIXME get rid of AttachmentEditorPresenter.this.docFolder !=
				// null placeholder code
				if (criteriaBuilder != null && query != null && predicates != null && AttachmentEditorPresenter.this.docFolder != null) {
					Root<?> fromFileEntry = query.getRoots().iterator().next();
					Path<Folder> parentFolder = fromFileEntry.<Folder> get("folder");
					Path<Long> pathId = parentFolder.get("id");
					predicates.add(criteriaBuilder.equal(pathId, AttachmentEditorPresenter.this.docFolder.getId()));
				} else if (AttachmentEditorPresenter.this.docFolder == null) {
					predicates.add(criteriaBuilder.equal(query.getRoots().iterator().next().get("id"), -1L));
				}
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
		this.daoProvider = (IDAOProvider) params.get(IPageComponent.DAO_PROVIDER);
		this.attachmentComponent = (AttachmentEditorComponent) params.get(IComponentFactory.COMPONENT_MODEL);
		this.entityContainerProvider = (IEntityContainerProvider) params.get(IComponentFactory.CONTAINER_PROVIDER);

		getView().addItemClickListener(new ItemClickListener() {
			private static final long serialVersionUID = -7680485120452162721L;

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					EntityItem<?> jpaItem = (EntityItem<?>) event.getItem();
					onInspectAttachment((FileEntry) jpaItem.getEntity());
				} else {
					getView().entityItemSingleClicked((EntityItem<?>) event.getItem());
				}
			}
		});

		getView().addNewItemListener(new ClickListener() {
			private static final long serialVersionUID = -60083075517936436L;

			@Override
			public void buttonClick(ClickEvent event) {
				getView().newEntityItemActioned();
			}
		});
		this.getView().setComponentModel(this.attachmentComponent);
	}

	public void onSaveForm(DocType attachmentType, String sourceFileName, String mimeType, String title, String description) throws Exception {
		this.daoProvider.provideByDAOClass(IFolderDAOService.class).addorUpdateFileEntry(this.docFolder, attachmentType, sourceFileName,
				mimeType, title, description);
		this.entityContainer.refresh();
	}

	@Override
	public void onCreateAttachment() {
		this.newEntityItem = this.entityContainer.createEntityItem(new FileEntry());
		this.getView().setItemDataSource(this.newEntityItem, FormMode.CREATING);
		this.getView().showDetail();
	}

	@Override
	public boolean onSaveAttachment(Item item, DocType attachmentType, String sourceFileName, String mimeType, String title, String description) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName("pageflow.ui.data");
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = this.daoProvider.provideByDAOClass(PlatformTransactionManager.class).getTransaction(def);
		try {
			if (description == null) {
				description = "";
			}
			if (mimeType == null) {
				mimeType = "";
			}

			this.daoProvider.provideByDAOClass(IFolderDAOService.class).addorUpdateFileEntry(this.docFolder, attachmentType, sourceFileName,
					mimeType, title, description);
			this.entityContainer.refresh();
			// this.onEntityItemAdded((EntityItem<?>) item);
			this.getView().hideDetail();
			this.daoProvider.provideByDAOClass(PlatformTransactionManager.class).commit(status);
			return true;
		} catch (Exception e) {
			this.daoProvider.provideByDAOClass(PlatformTransactionManager.class).rollback(status);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void onInspectAttachment(FileEntry fileEntry) {
		this.sectionEventBusManager.fireAnonymousEvent("viewDocument", new Object[] {fileEntry});
	}

	@Override
	public void subscribe(EventBusManager eventBusManager) {
		this.sectionEventBusManager = eventBusManager;
		this.sectionEventBusManager.register(AttachmentEditorEventBus.class, getEventBus());
	}
}
