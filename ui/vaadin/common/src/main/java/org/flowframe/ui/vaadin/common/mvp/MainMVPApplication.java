package org.flowframe.ui.vaadin.common.mvp;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import org.flowframe.bpm.jbpm.ui.pageflow.services.IPageFlowManager;
import org.flowframe.documentlibrary.remote.services.IRemoteDocumentRepository;
import org.flowframe.kernel.common.mdm.dao.services.IEntityMetadataDAOService;
import org.flowframe.kernel.common.mdm.dao.services.documentlibrary.IFolderDAOService;
import org.flowframe.kernel.common.mdm.domain.user.User;
import org.flowframe.kernel.common.utils.Validator;
import org.flowframe.kernel.jpa.container.services.IDAOProvider;
import org.flowframe.kernel.jpa.container.services.IEntityManagerFactoryManager;
import org.flowframe.portal.remote.services.IPortalOrganizationService;
import org.flowframe.portal.remote.services.IPortalRoleService;
import org.flowframe.portal.remote.services.IPortalUserService;
import org.flowframe.reporting.remote.services.IReportGenerator;
import org.flowframe.ui.services.IUIContributionManager;
import org.flowframe.ui.services.contribution.IActionContribution;
import org.flowframe.ui.services.contribution.IApplicationContribution;
import org.flowframe.ui.services.contribution.IMainApplication;
import org.flowframe.ui.services.contribution.IViewContribution;
import org.flowframe.ui.services.factory.IEntityEditorFactory;
import org.flowframe.ui.vaadin.common.entityprovider.jta.CustomNonCachingMutableLocalEntityProvider;
import org.flowframe.ui.vaadin.common.ui.menu.app.AppMenuEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.IPresenterFactory;
import org.vaadin.mvp.presenter.PresenterFactory;
import org.vaadin.mvp.uibinder.UiBinderException;

import com.vaadin.Application;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.util.EntityManagerPerRequestHelper;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;

@SuppressWarnings("serial")
public class MainMVPApplication extends Application implements IMainApplication, HttpServletRequestListener {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private PresenterFactory presenterFactory = null;
	private IPresenter<?, ? extends EventBus> mainPresenter;
	private User currentUser = null;
	private EntityManagerPerRequestHelper entityManagerPerRequestHelper;
	private HashMap<String, Object> entityEditorFactoryParams;

	@Autowired
	private IUIContributionManager contributionManager;
	@Autowired
	private IPageFlowManager pageFlowEngine;
	@Autowired
	private IDAOProvider daoProvider;
	@Autowired
	private IPortalUserService portalUserService;
	@Autowired
	private IPortalRoleService portalRoleService;
	@Autowired
	private IPortalOrganizationService portalOrganizationService;
	// @Autowired
	private IEntityEditorFactory entityEditorFactory;
	@Autowired
	private IEntityManagerFactoryManager entityManagerFactoryManager;
	@Autowired
	private UserTransaction userTransaction;
	@Autowired
	private IReportGenerator reportingGenerator;

	@Override
	public void init() {
		try {
			setTheme("conx");

			// Presenter factory
			this.presenterFactory = new PresenterFactory(new EventBusManager(), getLocale());
			this.presenterFactory.setApplication(this);

			// Create container manager/helper
			this.entityManagerPerRequestHelper = new EntityManagerPerRequestHelper();

			// request an instance of MainPresenter
			mainPresenter = this.presenterFactory.createPresenter(MainPresenter.class);

			// Create EntityFactory Presenter params
			((MainEventBus) mainPresenter.getEventBus()).start(this);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}

	}

	public Map<String, Object> provideEntityEditorFactoryParams() {
		if (this.entityEditorFactoryParams == null) {
			this.entityEditorFactoryParams = new HashMap<String, Object>();
			this.entityEditorFactoryParams = new HashMap<String, Object>();
			this.entityEditorFactoryParams.put(IEntityEditorFactory.FACTORY_PARAM_MVP_EVENTBUS_MANAGER, this.presenterFactory.getEventBusManager());
			this.entityEditorFactoryParams.put(IEntityEditorFactory.FACTORY_PARAM_MVP_LOCALE, getLocale());
			this.entityEditorFactoryParams.put(IEntityEditorFactory.FACTORY_PARAM_MVP_ENTITYMANAGERPERREQUESTHELPER,
					this.entityManagerPerRequestHelper);

			this.entityEditorFactoryParams.put(IEntityEditorFactory.FACTORY_PARAM_MVP_ENTITY_MANAGER_FACTORY,
					this.entityManagerFactoryManager.getKernelSystemEmf());
			this.entityEditorFactoryParams.put(IEntityEditorFactory.FACTORY_PARAM_IDOCLIB_REPO_SERVICE,
					this.daoProvider.provideByDAOClass(IRemoteDocumentRepository.class));
			this.entityEditorFactoryParams.put(IEntityEditorFactory.FACTORY_PARAM_IFOLDER_SERVICE,
					this.daoProvider.provideByDAOClass(IFolderDAOService.class));
			this.entityEditorFactoryParams.put(IEntityEditorFactory.FACTORY_PARAM_MAIN_APP, this);
			this.entityEditorFactoryParams.put(IEntityEditorFactory.FACTORY_PARAM_IENTITY_METADATA_SERVICE,
					this.daoProvider.provideByDAOClass(IEntityMetadataDAOService.class));
		}

		return this.entityEditorFactoryParams;
	}

	public Map<String, Object> getApplicationConfiguration() {
		return provideEntityEditorFactoryParams();
	}

	/**
	 * 
	 * HttpServletRequestListener
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void onRequestStart(HttpServletRequest request, HttpServletResponse response) {
		// authenticate(request);
		Map pns = request.getParameterMap();// email,pwd
		String email = null;

		if (pns.containsKey("email")) {
			String[] emailArray = (String[]) pns.get("email");
			email = emailArray[0];
		}

		if (pns.containsKey("pwd")) {
			String[] emailArray = (String[]) pns.get("pwd");
			email = emailArray[0];
		}

		String screenName = null;

		if (Validator.isNull(currentUser)) {
			if (Validator.isNotNull(email)) {
				try {
					currentUser = portalUserService.provideUserByEmailAddress(email);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				email = "test@liferay.com";
				screenName = "test";
				currentUser = new User();
				currentUser.setEmailAddress(email);
				currentUser.setScreenName(screenName);
			}
		}

		// Start request helper
		if (this.entityManagerPerRequestHelper != null)// Init called already
			this.entityManagerPerRequestHelper.requestStart();
	}

	// private boolean authenticate(HttpServletRequest req) {
	// String authhead = req.getHeader("Authorization");
	//
	// if (authhead != null) {
	// // *****Decode the authorisation String*****
	// String usernpass = Base64.decode(authhead.substring(6));
	// // *****Split the username from the password*****
	// String user = usernpass.substring(0, usernpass.indexOf(":"));
	// String password = usernpass.substring(usernpass.indexOf(":") + 1);
	//
	// if (user.equals("user") && password.equals("pass"))
	// return true;
	// }
	//
	// return false;
	// }

	@Override
	public void onRequestEnd(HttpServletRequest request, HttpServletResponse response) {
		if (this.entityManagerPerRequestHelper != null)// Init called already
			this.entityManagerPerRequestHelper.requestEnd();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object createPersistenceContainer(Class entityClass) {
		CustomNonCachingMutableLocalEntityProvider provider = new CustomNonCachingMutableLocalEntityProvider(entityClass,
				this.entityManagerFactoryManager.getKernelSystemEmf(), this.userTransaction);
		JPAContainer<?> container = new JPAContainer(entityClass);
		container.setEntityProvider(provider);
		return container;
	}

	public IPresenterFactory getPresenterFactory() {
		return this.presenterFactory;
	}

	public AppMenuEntry[] createAppMenuEntries() throws UiBinderException {
		// TODO Why are we not using collections here?
		IApplicationContribution[] appContributions = this.contributionManager.getCurrentApplicationContributions();
		if (appContributions.length == 0) {
			return new AppMenuEntry[] {};
		} else {
			ArrayList<AppMenuEntry> entries = new ArrayList<AppMenuEntry>();
			for (IApplicationContribution ac : appContributions) {
				logger.debug("[APP MENU ENTRY] Adding app with 'presenterClass' = " + ac.getPresenterClass());
				entries.add(new AppMenuEntry(ac.getCode(), ac.getName(), ac.getIcon(), ac.getPresenterClass()));
			}
			return entries.toArray(new AppMenuEntry[] {});
		}
	}

	public EntityManagerPerRequestHelper getEntityManagerPerRequestHelper() {
		return entityManagerPerRequestHelper;
	}

	public void setEntityManagerPerRequestHelper(EntityManagerPerRequestHelper entityManagerPerRequestHelper) {
		this.entityManagerPerRequestHelper = entityManagerPerRequestHelper;
	}

	public IPortalUserService getPortalUserService() {
		return portalUserService;
	}

	public void setPortalUserService(IPortalUserService portalUserService) {
		this.portalUserService = portalUserService;
	}

	public IPortalOrganizationService getPortalOrganizationService() {
		return portalOrganizationService;
	}

	public void setPortalOrganizationService(IPortalOrganizationService portalOrganizationService) {
		this.portalOrganizationService = portalOrganizationService;
	}

	public IPortalRoleService getPortalRoleService() {
		return portalRoleService;
	}

	public void setPortalRoleService(IPortalRoleService portalRoleService) {
		this.portalRoleService = portalRoleService;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public IDAOProvider getDaoProvider() {
		return daoProvider;
	}

	public void setDaoProvider(IDAOProvider daoProvider) {
		this.daoProvider = daoProvider;
	}

	public IPageFlowManager getPageFlowEngine() {
		return pageFlowEngine;
	}

	public void setPageFlowEngine(IPageFlowManager pageFlowEngine) {
		this.pageFlowEngine = pageFlowEngine;
	}

	@SuppressWarnings("rawtypes")
	public void bindDAOProvider(IDAOProvider daoProvider, Map properties) {
		this.daoProvider = daoProvider;
	}

	@SuppressWarnings("rawtypes")
	public void unbindDAOProvider(IDAOProvider daoProvider, Map properties) {
		this.daoProvider = null;
	}

	public void setContributionManager(IUIContributionManager contributionManager) {
		this.contributionManager = contributionManager;
	}

	@Override
	public IUIContributionManager getUiContributionManager() {
		return this.contributionManager;
	}

	@Override
	public IApplicationContribution getApplicationContributionByCode(String code) {
		return this.contributionManager.getApplicationContributionByCode(this, code);
	}

	@Override
	public IViewContribution getViewContributionByCode(String code) {
		return this.contributionManager.getViewContributionByCode(this, code);
	}

	public IActionContribution getActionContributionByCode(String code) {
		return this.contributionManager.getActionContributionByCode(this, code);
	}

	public IEntityEditorFactory getEntityEditorFactory() {
		return entityEditorFactory;
	}

	public IEntityManagerFactoryManager getEntityManagerFactoryManager() {
		return entityManagerFactoryManager;
	}

	public IPresenter<?, ? extends EventBus> getMainPresenter() {
		return this.mainPresenter;
	}

	public UserTransaction getUserTransaction() {
		return userTransaction;
	}

	public void setUserTransaction(UserTransaction userTransaction) {
		this.userTransaction = userTransaction;
	}

	public IReportGenerator getReportingGenerator() {
		return reportingGenerator;
	}

	public void setReportingGenerator(IReportGenerator reportingGenerator) {
		this.reportingGenerator = reportingGenerator;
	}

	@Override
	public String getReportingUrl() {
		assert (this.reportingGenerator != null) : "The reporting service was null";
		String baseUrl = getURL().getProtocol() + "://" + getURL().getHost();
		if (getURL().getPort() != -1) {
			baseUrl += ":" + getURL().getPort();
		}

		return this.reportingGenerator.getUrlPathForPDFGenerator(baseUrl);
	}
}