package org.flowframe.ui.vaadin.mvp.app.events;

import org.flowframe.ui.vaadin.mvp.core.events.application.BaseApplicationEvent;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;

import com.vaadin.ui.Component;

public class OpenComponentEvent extends BaseApplicationEvent {
	
	private Component appComponent; 
	private String name; 
	String iconPath; 
	Class<? extends BasePresenter<?, ? extends EventBus>> presenterClass;
	boolean closable;
	
	
	public OpenComponentEvent() {
	}


	public OpenComponentEvent(
			Component appComponent,
			String name,
			String iconPath,
			Class<? extends BasePresenter<?, ? extends EventBus>> presenterClass,
			boolean closable) {
		super();
		this.appComponent = appComponent;
		this.name = name;
		this.iconPath = iconPath;
		this.presenterClass = presenterClass;
		this.closable = closable;
	}


	public Component getAppComponent() {
		return this.appComponent;
	}


	public void setAppComponent(Component appComponent) {
		this.appComponent = appComponent;
	}


	public String getName() {
		return this.name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getIconPath() {
		return this.iconPath;
	}


	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}


	public Class<? extends BasePresenter<?, ? extends EventBus>> getPresenterClass() {
		return this.presenterClass;
	}


	public void setPresenterClass(
			Class<? extends BasePresenter<?, ? extends EventBus>> presenterClass) {
		this.presenterClass = presenterClass;
	}


	public boolean isClosable() {
		return this.closable;
	}


	public void setClosable(boolean closable) {
		this.closable = closable;
	}
}
