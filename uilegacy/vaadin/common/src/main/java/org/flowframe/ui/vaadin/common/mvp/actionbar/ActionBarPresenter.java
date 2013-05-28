package org.flowframe.ui.vaadin.common.mvp.actionbar;

import java.util.Collection;

import org.flowframe.ui.services.contribution.IApplicationContribution;
import org.flowframe.ui.services.contribution.IMainApplication;
import org.flowframe.ui.vaadin.common.mvp.actionbar.view.ActionBarView;
import org.flowframe.ui.vaadin.common.mvp.actionbar.view.ActionBarView.IActionBarNavigationListener;
import org.flowframe.ui.vaadin.common.mvp.actionbar.view.IActionBarView;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

@Presenter(view = ActionBarView.class)
public class ActionBarPresenter extends BasePresenter<IActionBarView, ActionBarEventBus>{
	private static final String SHOW_APP_CONTRIBUTION_EVENT_NAME = "showAppContribution";
	
	private IMainApplication mainApp;
	
	public void onStart(IMainApplication mainApp) {
		assert (mainApp != null) : "The provided main application was null.";
		this.mainApp = mainApp;
		
		initNavigation();
		this.getView().addListener(new IActionBarNavigationListener() {
			
			@Override
			public void onNavigate(String code) {
				boolean wasSuccessful = ActionBarPresenter.this.mainApp.getPresenterFactory().getEventBusManager().fireAnonymousEvent(SHOW_APP_CONTRIBUTION_EVENT_NAME, new Object[] { code });
				if (!wasSuccessful) {
					ActionBarPresenter.this.mainApp.showError("Couldn't Show App Contribution", "The event of name " + SHOW_APP_CONTRIBUTION_EVENT_NAME + " could not be invoked.", "<i>There was no stack trace.</i>");
				}
			}
		});
	}
	
	private void initNavigation() {
		Collection<IApplicationContribution> appContributions = this.mainApp.getAllApplicationContributions();
		for (IApplicationContribution appContribution : appContributions) {
			int ordinal = (appContribution.getName() == null) ? 0 : 1;
			this.getView().addNavItem(ordinal, appContribution.getCode(), appContribution.getIcon(), appContribution.getName());
		}
	}
}
