package org.flowframe.ui.vaadin.common.editors.entity.mvp.attachment;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.flowframe.documentlibrary.remote.services.IRemoteDocumentRepository;
import org.flowframe.kernel.common.mdm.dao.services.documentlibrary.IFolderDAOService;
import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.DocType;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;
import org.flowframe.ui.component.domain.attachment.AttachmentEditorComponent;
import org.flowframe.ui.pageflow.services.IMainApplication;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.ConfigurableBasePresenter;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.MultiLevelEntityEditorEventBus;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.attachment.view.AttachmentEditorView;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.attachment.view.AttachmentEditorView.ICreateAttachmentListener;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.attachment.view.AttachmentEditorView.IInspectAttachmentListener;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.attachment.view.AttachmentEditorView.ISaveAttachmentListener;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.attachment.view.IAttachmentEditorView;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.view.IMultiLevelEntityEditorView;
import org.flowframe.ui.vaadin.forms.FormMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;
import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@Presenter(view = AttachmentEditorView.class)
public class AttachmentEditorPresenter extends ConfigurableBasePresenter<IAttachmentEditorView, AttachmentEditorEventBus> implements ICreateAttachmentListener, ISaveAttachmentListener, IInspectAttachmentListener {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private boolean initialized = false;
	private JPAContainer<FileEntry> entityContainer;
	private String[] visibleFieldNames;
	private IRemoteDocumentRepository docRepo;
	private Folder docFolder;
	private List<String> formVisibleFieldNames;
	private EntityItem<FileEntry> newEntityItem;
	private ConfigurableBasePresenter<IMultiLevelEntityEditorView, MultiLevelEntityEditorEventBus> mlEntityPresenter;
	@SuppressWarnings("unused")
	private IFolderDAOService docFolderDAOService;
	private AttachmentEditorComponent attachmentComponent;
	private IMainApplication mainApplication;

	@SuppressWarnings("unchecked")
	private void initialize() {
		this.getView().init();
		// - Create FileEntry container
		this.entityContainer = (JPAContainer<FileEntry>) this.mainApplication.createPersistenceContainer(FileEntry.class);
		this.visibleFieldNames = new String[] { "name", "mimeType", "createDate", "description", "size" };
		this.formVisibleFieldNames = Arrays.asList("docType");
//		Set<String> nestedFieldNames = attachmentComponent.getDataSource().getNestedFieldNames();
//		for (String npp : nestedFieldNames) {
//			entityContainer.addNestedContainerProperty(npp);
//		}
		this.getView().addCreateAttachmentListener(this);
		this.getView().addSaveAttachmentListener(this);
		this.getView().addInspectAttachmentListener(this);
		this.getView().setContainerDataSource(entityContainer, visibleFieldNames, formVisibleFieldNames);
		this.getView().showContent();
		this.setInitialized(true);
	}

	// MultiLevelEntityEditorEventBus implementation
	@SuppressWarnings("rawtypes")
	public void onEntityItemEdit(EntityItem item) {
		BaseEntity entity = (BaseEntity) item.getEntity();
		this.docFolder = entity.getDocFolder();
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
		this.entityContainer.getEntityProvider().setQueryModifierDelegate(new DefaultQueryModifierDelegate() {
			@Override
			public void filtersWillBeAdded(CriteriaBuilder criteriaBuilder, CriteriaQuery<?> query, List<Predicate> predicates) {
				Root<?> fromFileEntry = query.getRoots().iterator().next();

				// Add a "WHERE age > 116" expression
				Path<Folder> parentFolder = fromFileEntry.<Folder> get("folder");
				Path<Long> pathId = parentFolder.get("id");
				predicates.add(criteriaBuilder.equal(pathId, AttachmentEditorPresenter.this.docFolder.getId()));
			}
		});
		this.entityContainer.applyFilters();
	}

	@SuppressWarnings("rawtypes")
	public void onEntityItemAdded(EntityItem item) {
		this.entityContainer.refresh();
	}

	@Override
	public void bind() {
		getView().addItemClickListener(new ItemClickListener() {
			private static final long serialVersionUID = -7680485120452162721L;

			@SuppressWarnings("unchecked")
			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					JPAContainerItem<FileEntry> jpaItem = (JPAContainerItem<FileEntry>) event.getItem();
					onInspectAttachment(jpaItem.getEntity());
				} else {
					getView().entityItemSingleClicked((EntityItem<?>) event.getItem());
				}
			}
		});
		
		getView().addNewItemListener( new ClickListener() {
			private static final long serialVersionUID = -60083075517936436L;

			@Override
			public void buttonClick(ClickEvent event) {
				getView().newEntityItemActioned();
			}
		});
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void onConfigure(Map<String, Object> config) {
		super.setConfig(config);
		this.mlEntityPresenter = (ConfigurableBasePresenter) getConfig().get(IComponentFactory.FACTORY_PARAM_MVP_CURRENT_MLENTITY_EDITOR_PRESENTER);

		this.docRepo = (IRemoteDocumentRepository) getConfig().get(IComponentFactory.FACTORY_PARAM_IDOCLIB_REPO_SERVICE);
		this.docFolderDAOService = (IFolderDAOService) getConfig().get(IComponentFactory.FACTORY_PARAM_IFOLDER_SERVICE);

		this.attachmentComponent = (AttachmentEditorComponent) getConfig().get(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL);

		this.mainApplication = (IMainApplication) getConfig().get(IComponentFactory.FACTORY_PARAM_MAIN_APP);
	}

	public void onSaveForm(DocType attachmentType, String sourceFileName, String mimeType, String title, String description) throws Exception {
		//this.docRepo.addorUpdateFileEntry(this.docFolder, attachmentType, sourceFileName, mimeType, title, description);
		this.onEntityItemAdded(null);
	}
	
	@Override
	public void onCreateAttachment() {
		this.newEntityItem = this.entityContainer.createEntityItem(new FileEntry());
		this.getView().setItemDataSource(this.newEntityItem, FormMode.CREATING);
		this.getView().showDetail();
	}

	@Override
	public boolean onSaveAttachment(Item item, DocType attachmentType, String sourceFileName, String mimeType, String title, String description) {
		try {
			//this.docRepo.addorUpdateFileEntry(Long.toString(this.docFolder.getFolderId()), attachmentType, sourceFileName, mimeType, title, description);
			this.onEntityItemAdded((EntityItem<?>) item);
			this.getView().hideDetail();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void onInspectAttachment(FileEntry fileEntry) {
		mlEntityPresenter.getEventBus().viewDocument(fileEntry);
	}
}
