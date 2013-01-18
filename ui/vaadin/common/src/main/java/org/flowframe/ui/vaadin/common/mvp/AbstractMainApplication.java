package org.flowframe.ui.vaadin.common.mvp;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.flowframe.bpm.jbpm.ui.pageflow.services.IPageFlowManager;
import org.flowframe.kernel.common.mdm.domain.user.User;
import org.flowframe.kernel.common.utils.Validator;
import org.flowframe.kernel.jpa.container.services.IEntityContainerProvider;
import org.flowframe.kernel.jpa.container.services.IEntityManagerFactoryManager;
import org.flowframe.ui.services.IUIContributionManager;
import org.flowframe.ui.services.contribution.IActionContribution;
import org.flowframe.ui.services.contribution.IApplicationContribution;
import org.flowframe.ui.services.contribution.IMainApplication;
import org.flowframe.ui.services.contribution.IViewContribution;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.IPresenterFactory;
import org.vaadin.mvp.presenter.PresenterFactory;

import com.vaadin.Application;
import com.vaadin.addon.jpacontainer.util.EntityManagerPerRequestHelper;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.Window;

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
	public Object createPersistenceContainer(Class<?> entityClass) {
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
}