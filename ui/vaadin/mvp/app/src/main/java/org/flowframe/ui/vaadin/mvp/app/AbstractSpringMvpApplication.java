package org.flowframe.ui.vaadin.mvp.app;

import java.util.Collection;

import org.flowframe.kernel.common.mdm.domain.user.User;
import org.flowframe.kernel.jpa.container.services.IEntityManagerFactoryManager;
import org.flowframe.ui.services.IUIContributionManager;
import org.flowframe.ui.services.contribution.IActionContribution;
import org.flowframe.ui.services.contribution.IApplicationContribution;
import org.flowframe.ui.services.contribution.IMainMVPApplication;
import org.flowframe.ui.services.contribution.IViewContribution;
import org.flowframe.ui.services.transaction.ITransactionCompletionListener;
import org.flowframe.ui.vaadin.converters.services.IComponentModelPresenterFactory;
import org.flowframe.ui.vaadin.mvp.IMainEventBus;
import org.flowframe.ui.vaadin.mvp.MainPresenter;
import org.flowframe.ui.vaadin.mvp.app.events.StartApplicationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.vaadin.mvp.eventbus.CustomEventBusManager;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.IPresenterFactory;
import org.vaadin.mvp.presenter.spring.CustomSpringMvpApplication;
import org.vaadin.mvp.presenter.spring.CustomSpringPresenterFactory;
import org.vaadin.mvp.uibinder.IUiMessageSource;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

public abstract class AbstractSpringMvpApplication extends CustomSpringMvpApplication implements IMainMVPApplication,HttpServletRequestListener {
	protected IPresenter<?, ? extends EventBus> mainPresenter;
	protected User currentUser = null;
	
	@Autowired(required = false)
	protected IUIContributionManager contributionManager;	
	
	@Autowired
	protected PlatformTransactionManager transactionManager;	

	protected CustomSpringPresenterFactory presenterFactory;
	
	@Autowired
	protected IComponentModelPresenterFactory componentModelPresenterFactory;
	
	@Autowired
	protected IEntityManagerFactoryManager mainEmfManager;	
	
	@Autowired
	protected IEntityManagerFactoryManager cacheEmfManager;	

	protected IUiMessageSource messageSource;

	protected CustomEventBusManager eventBusManager = new CustomEventBusManager();
	
	public AbstractSpringMvpApplication() {
		super();
	}
	
	
	@Autowired(required = true)
	public void setPresenterFactory(CustomSpringPresenterFactory presenterFactory) {
		this.presenterFactory = presenterFactory;
		this.presenterFactory.setApplication(this);
	}

	public abstract void preInit();

	public void postInit() {
		setTheme("conx");

		// Create container manager/helper
		//this.entityManagerPerRequestHelper = new EntityManagerPerRequestHelper();
		StartApplicationEvent ase = new StartApplicationEvent(this);
		this.mainPresenter = this.presenterFactory.createPresenter(MainPresenter.class);
		((IMainEventBus) mainPresenter.getEventBus()).fireApplicationEvent(ase);
		assert (this.mainPresenter.getView() instanceof Window) : "The main presenter view must extend com.vaadin.ui.Window.";
		this.setMainWindow((Window) this.mainPresenter.getView());
	}

	@Override
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	public IPresenterFactory getPresenterFactory() {
		return presenterFactory;
	}

	protected IUiMessageSource getMessageSource() {
		return messageSource;
	}

	protected void setMessageSource(IUiMessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@Override
	public IPresenter<?, ? extends EventBus> getMainPresenter() {
		return this.mainPresenter;
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
	public Object createPersistenceContainer(Class<?> entityClass) {
		return this.getContainerProvider().createNonCachingPersistenceContainer(entityClass);
	}

	@Override
	public Object createCachedPersistenceContainer(Class<?> entityClass) {
		return this.getContainerProvider().createPersistenceContainer(entityClass);
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
