package org.flowframe.ui.vaadin.mvp.app.events;

import org.flowframe.ui.services.contribution.IMainMVPApplication;
import org.flowframe.ui.vaadin.mvp.core.events.application.BaseApplicationEvent;

public class StartApplicationEvent extends BaseApplicationEvent {
	private IMainMVPApplication application;
	
	public StartApplicationEvent() {
	}	

	public StartApplicationEvent(IMainMVPApplication app) {
		this.application = app;
	}

	public IMainMVPApplication getApplication() {
		return this.application;
	}

	public void setApplication(IMainMVPApplication application) {
		this.application = application;
	}
}
