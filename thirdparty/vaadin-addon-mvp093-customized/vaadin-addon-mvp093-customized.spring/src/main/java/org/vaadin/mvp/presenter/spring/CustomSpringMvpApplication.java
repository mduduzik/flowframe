package org.vaadin.mvp.presenter.spring;

import org.vaadin.mvp.eventbus.CustomEventBusManager;

/**
 * Custom Vaadin base application class for spring/mvp based applications.
 * 
 * @author tam
 * 
 */
public abstract class CustomSpringMvpApplication extends SpringMvpApplication {

	public CustomSpringMvpApplication() {
		super();
		super.eventBusManager = new CustomEventBusManager();
	}
	
}
