package org.flowframe.ui.vaadin.common.mvp;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.flowframe.bpm.jbpm.ui.pageflow.services.IPageFlowManager;
import org.flowframe.kernel.common.mdm.domain.user.User;
import org.flowframe.kernel.common.utils.Validator;
import org.flowframe.kernel.jpa.container.services.IDAOProvider;
import org.flowframe.kernel.jpa.container.services.IEntityContainerProvider;
import org.flowframe.kernel.jpa.container.services.IEntityManagerFactoryManager;
import org.flowframe.ui.services.IUIContributionManager;
import org.flowframe.ui.services.contribution.IActionContribution;
import org.flowframe.ui.services.contribution.IApplicationContribution;
import org.flowframe.ui.services.contribution.IMainApplication;
import org.flowframe.ui.services.contribution.IViewContribution;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.services.transaction.ITransactionCompletionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.IPresenterFactory;
import org.vaadin.mvp.presenter.PresenterFactory;

import com.vaadin.Application;
import com.vaadin.addon.jpacontainer.util.EntityManagerPerRequestHelper;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

@SuppressWarnings("serial")
public abstract class AbstractMainApplication extends Application implements IMainApplication, HttpServletRequestListener {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected PresenterFactory presenterFactory = null;
	private IPresenter<?, ? extends EventBus> mainPresenter;
	private User currentUser = null;
	private EntityManagerPerRequestHelper entityManagerPerRequestHelper;

	@Autowired
	protected IUIContributionManager contributionManager;
	@Autowired
	protected IPageFlowManager pageFlowEngine;
	@Autowired
	protected IComponentFactory componentFactory;
	@Autowired
	protected IEntityManagerFactoryManager emfManager;
	@Autowired
	protected PlatformTransactionManager transactionManager;

	@Override
	public void init() {
		setTheme("conx");

		this.presenterFactory = new PresenterFactory(new EventBusManager(), getLocale());
		this.presenterFactory.setApplication(this);

		// Create container manager/helper
		this.entityManagerPerRequestHelper = new EntityManagerPerRequestHelper();

		this.mainPresenter = this.presenterFactory.createPresenter(MainPresenter.class);
		((MainEventBus) mainPresenter.getEventBus()).start(this);
		assert (this.mainPresenter.getView() instanceof Window) : "The main presenter view must extend com.vaadin.ui.Window.";
		this.setMainWindow((Window) this.mainPresenter.getView());
	}

	public abstract Map<String, Object> getApplicationConfiguration();

	public abstract User getUser(String emailAddress);

	public abstract IEntityContainerProvider getContainerProvider();
	
	public abstract IDAOProvider getDAOProvider();
	
	public abstract String getReportingUrl();
	
	@Override
	public void onRequestStart(HttpServletRequest request, HttpServletResponse response) {
		Map<?, ?> pns = request.getParameterMap();
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
				currentUser = getUser(email);
			} else {
				email = "test@liferay.com";
				screenName = "test";
				currentUser = new User();
				currentUser.setEmailAddress(email);
				currentUser.setScreenName(screenName);
			}
		}

		if (this.entityManagerPerRequestHelper != null) {
			this.entityManagerPerRequestHelper.requestStart();
		}
	}

	@Override
	public void onRequestEnd(HttpServletRequest request, HttpServletResponse response) {
		if (this.entityManagerPerRequestHelper != null) {
			this.entityManagerPerRequestHelper.requestEnd();
		}
	}
	
	@Override
	public <T> T findDAOByClass(Class<T> daoClass) {
		return this.getDAOProvider().provideByDAOClass(daoClass);
	}

	@Override
	public Object createPersistenceContainer(Class<?> entityClass) {
		return this.getContainerProvider().createNonCachingPersistenceContainer(entityClass);
	}
	
	@Override
	public Object createCachedPersistenceContainer(Class<?> entityClass) {
		return this.getContainerProvider().createPersistenceContainer(entityClass);
	}

	public IPageFlowManager getPageFlowEngine() {
		return pageFlowEngine;
	}

	public void setPageFlowEngine(IPageFlowManager pageFlowEngine) {
		this.pageFlowEngine = pageFlowEngine;
	}

	public void setContributionManager(IUIContributionManager contributionManager) {
		this.contributionManager = contributionManager;
	}

	@Override
	public Collection<IApplicationContribution> getAllApplicationContributions() {
		return this.contributionManager.getAllApplicationContributions();
	}

	@Override
	public IApplicationContribution getApplicationContributionByCode(String code) {
		return this.contributionManager.getApplicationContributionByCode(code);
	}

	@Override
	public Collection<IViewContribution> getAllViewContributions() {
		return this.contributionManager.getAllViewContributions();
	}

	@Override
	public IViewContribution getViewContributionByCode(String code) {
		return this.contributionManager.getViewContributionByCode(code);
	}

	@Override
	public Collection<IActionContribution> getAllActionContributions() {
		return this.contributionManager.getAllActionContributions();
	}

	@Override
	public IActionContribution getActionContributionByCode(String code) {
		return this.contributionManager.getActionContributionByCode(code);
	}

	@Override
	public IComponentFactory getComponentFactory() {
		return this.componentFactory;
	}

	@Override
	public IPresenterFactory getPresenterFactory() {
		return this.presenterFactory;
	}
	
	@Override
	public IPresenter<?, ? extends EventBus> getMainPresenter() {
		return this.mainPresenter;
	}

	@Override
	public void runInTransaction(String name, Runnable runnable) throws Exception {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(name);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = this.transactionManager.getTransaction(def);
		try {
			runnable.run();
		} catch (Exception e) {
			this.transactionManager.rollback(status);
			throw e;
		} catch (Error e) {
			e.printStackTrace();
			this.transactionManager.rollback(status);
			throw new RuntimeException("An Internal Error occurred: " + e.getMessage());
		}
		this.transactionManager.commit(status);
	}
	
	@Override
	public void runInTransaction(String name, Runnable runnable, final ITransactionCompletionListener completionListener) throws Exception {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(name);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = this.transactionManager.getTransaction(def);
		try {
			runnable.run();
		} catch (Exception e) {
			this.transactionManager.rollback(status);
			throw e;
		} catch (Error e) {
			e.printStackTrace();
			this.transactionManager.rollback(status);
			throw new RuntimeException("An Internal Error occurred: " + e.getMessage());
		}
		this.transactionManager.commit(status);
		completionListener.onTransactionCompleted();
	}
	
	@Override
	public void showAlert(String caption, String message) {
		if (this.getMainWindow() != null) {
			this.getMainWindow().showNotification(caption, message, Notification.TYPE_WARNING_MESSAGE);
		}
	}
	
	@Override
	public void showNotification(String caption, String message) {
		if (this.getMainWindow() != null) {
			this.getMainWindow().showNotification(caption, message, Notification.TYPE_TRAY_NOTIFICATION);
		}
	}
	
	@Override
	public void showError(String caption, String message, String stackTrace) {
		if (this.getMainWindow() != null) {
			this.getMainWindow().showNotification(caption, message + "<br/>" + stackTrace, Notification.TYPE_ERROR_MESSAGE);
		}
	}
}