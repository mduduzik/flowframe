package org.flowframe.ui.services.contribution;

import java.util.Collection;
import java.util.Map;

import org.flowframe.kernel.common.mdm.domain.user.User;
import org.flowframe.kernel.jpa.container.services.IEntityContainerProvider;
import org.flowframe.ui.services.transaction.ITransactionCompletionListener;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.IPresenterFactory;

public interface IMainMVPApplication {
	public IEntityContainerProvider getContainerProvider();
	public IPresenterFactory getPresenterFactory();
	public IPresenter<?, ? extends EventBus> getMainPresenter();
	
	public Collection<IViewContribution> getAllViewContributions();
	public IViewContribution getViewContributionByCode(String code);
	public Collection<IActionContribution> getAllActionContributions();
	public IActionContribution getActionContributionByCode(String code);
	public Collection<IApplicationContribution> getAllApplicationContributions();
	public IApplicationContribution getApplicationContributionByCode(String code);
	
	public Object createPersistenceContainer(Class<?> entityClass);
	public Object createCachedPersistenceContainer(Class<?> entityClass);
	public void runInTransaction(String name, Runnable runnable) throws Exception;
	public void runInTransaction(String name, Runnable runnable, ITransactionCompletionListener completionListener) throws Exception;
	
	public User getCurrentUser();
	public String getReportingUrl();
	
	public void showNotification(String caption, String message);
	public void showAlert(String caption, String message);
	public void showError(String caption, String message, String stackTrace);
}
