package org.flowframe.ui.pageflow.vaadin.mvp;

import java.util.HashMap;
import java.util.Map;

import org.flowframe.ui.pageflow.services.IPageComponent;
import org.flowframe.ui.pageflow.services.IPageFlowPage;
import org.flowframe.ui.pageflow.services.ITaskWizard;
import org.flowframe.ui.pageflow.services.event.IPageFlowPageChangedEventHandler;
import org.flowframe.documentlibrary.remote.services.IRemoteDocumentRepository;
import org.flowframe.kernel.common.mdm.dao.services.documentlibrary.IFolderDAOService;
import org.flowframe.kernel.jpa.container.services.IEntityContainerProvider;
import org.flowframe.kernel.metamodel.dao.services.IEntityTypeDAOService;
import org.flowframe.ui.pageflow.vaadin.mvp.view.IPageView;
import org.flowframe.ui.pageflow.vaadin.mvp.view.PageView;
import org.springframework.transaction.PlatformTransactionManager;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.ui.Component;

@Presenter(view = PageView.class)
public class PagePresenter extends BasePresenter<IPageView, PageEventBus> implements IPageComponent {
	private boolean executed;
	private IPageFlowPage page;
	private IPageDataHandler dataHandler;

	private PlatformTransactionManager ptm;
	private IPageFlowPageChangedEventHandler pfpEventHandler;
	private ITaskWizard wizard;
	private IEntityContainerProvider containerProvider;
	private Map<String, Object> parameterData;
	
	private IEntityTypeDAOService entityTypeDAOService;
	private IFolderDAOService docFolderDAOService;
	private IRemoteDocumentRepository docRepo;
	private Map<String, Object> config;
	
	private IPresenter<?, ? extends EventBus> contentPresenter = null;
	
	public IPresenter<?, ? extends EventBus> getContentPresenter() {
		return contentPresenter;
	}

	public void setContentPresenter(IPresenter<?, ? extends EventBus> contentPresenter) {
		this.contentPresenter = contentPresenter;
	}

	public void setPageContent(Component content) {
		this.getView().setContent(content);
	}
	
	public Component getPageContent() {
		return this.getView().getContent();
	}

	public void setPage(IPageFlowPage page) {
		this.page = page;
	}

	public IPageFlowPage getPage() {
		return this.page;
	}

	@Override
	public String getCaption() {
		if (this.page != null) {
			return this.page.getTaskName();
		}
		return null;
	}

	@Override
	public Component getContent() {
		return (Component) this.getView();
	}

	@Override
	public boolean onAdvance() {
		return true;
	}

	@Override
	public boolean onBack() {
		return true;
	}

	@Override
	public void init(Map<String, Object> config) {
		this.setConfig(config);
		this.ptm = (PlatformTransactionManager) config.get(IPageComponent.JTA_GLOBAL_TRANSACTION_MANAGER);
		this.pfpEventHandler = (IPageFlowPageChangedEventHandler) config.get(IPageComponent.PAGE_FLOW_PAGE_CHANGE_EVENT_HANDLER);
		this.wizard = (ITaskWizard) config.get(IPageComponent.TASK_WIZARD);
		this.containerProvider = (IEntityContainerProvider) config.get(IPageComponent.ENTITY_CONTAINER_PROVIDER);
		this.entityTypeDAOService = (IEntityTypeDAOService) config.get(IPageComponent.ENTITY_TYPE_DAO_SERVICE);
		this.docFolderDAOService = (IFolderDAOService) config.get(IPageComponent.FOLDER_DAO_SERVICE);
		this.docRepo = (IRemoteDocumentRepository) config.get(IPageComponent.REMOTE_DOCUMENT_REPOSITORY);
	}

	@Override
	public void setParameterData(Map<String, Object> params) {
		this.parameterData = params;
		if (this.dataHandler != null) {
			this.dataHandler.setParameterData(this, params);
		}
	}

	public Map<String, Object> getParameterData() {
		return this.parameterData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getResultData() {
		if (this.dataHandler != null) {
			Map<String, Object> pageResultData = new HashMap<String, Object>(this.parameterData);
			pageResultData.putAll((Map<String, Object>) this.dataHandler.getResultData(this));
			return pageResultData;
		} else {
			return null;
		}
	}

	@Override
	public boolean isExecuted() {
		return this.executed;
	}

	@Override
	public void setExecuted(boolean executed) {
		this.executed = executed;
	}

	public PlatformTransactionManager getPlatformTransactionManager() {
		return ptm;
	}

	public IPageFlowPageChangedEventHandler getPageFlowPageEventHandler() {
		return pfpEventHandler;
	}

	public ITaskWizard getWizard() {
		return wizard;
	}

	public IEntityContainerProvider getContainerProvider() {
		return containerProvider;
	}

	public IPageDataHandler getDataHandler() {
		return dataHandler;
	}

	public void setDataHandler(IPageDataHandler dataHandler) {
		this.dataHandler = dataHandler;
	}

	public IEntityTypeDAOService getEntityTypeDAOService() {
		return entityTypeDAOService;
	}

	public IFolderDAOService getDocFolderDAOService() {
		return docFolderDAOService;
	}

	public IRemoteDocumentRepository getDocRepo() {
		return docRepo;
	}

	public Map<String, Object> getConfig() {
		return config;
	}

	public void setConfig(Map<String, Object> config) {
		this.config = config;
	}
}
