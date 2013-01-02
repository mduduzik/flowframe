package org.flowframe.ui.vaadin.common.ui.menu.app;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.IPresenter;

import org.flowframe.ui.vaadin.common.ui.menu.MenuEntry;
import com.vaadin.ui.Component;

public class AppMenuEntry extends MenuEntry {
	private Component launchableAppComponent;
	private Class<? extends BasePresenter<?, ? extends EventBus>> presenterClass;
	
	public AppMenuEntry(String caption, String iconPath, Component launchableAppComponent)
	{
		super(caption,iconPath);
		this.launchableAppComponent = launchableAppComponent;
	}
	
	public AppMenuEntry(String code, String caption, String iconPath, Component launchableAppComponent)
	{
		super(code,caption,iconPath);
		this.launchableAppComponent = launchableAppComponent;
	}
	
	public AppMenuEntry(String code, String caption, String iconPath, Component launchableAppComponent,Class<? extends BasePresenter<?, ? extends EventBus>> presenterClass)
	{
		super(code,caption,iconPath);
		this.launchableAppComponent = launchableAppComponent;
		this.presenterClass = presenterClass;
	}	
	
	public Component getLaunchableAppComponent() {
		return launchableAppComponent;
	}

	public Class<? extends BasePresenter<?, ? extends EventBus>> getAppPresenterClass() {
		return presenterClass;
	}
}
