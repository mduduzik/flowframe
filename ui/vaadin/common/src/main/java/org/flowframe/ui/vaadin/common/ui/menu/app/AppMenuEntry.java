package org.flowframe.ui.vaadin.common.ui.menu.app;

import org.flowframe.ui.vaadin.common.ui.menu.MenuEntry;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;

public class AppMenuEntry extends MenuEntry {
	private Class<? extends BasePresenter<?, ? extends EventBus>> presenterClass;

	public AppMenuEntry(String caption, String iconPath) {
		super(caption, iconPath);
	}

	public AppMenuEntry(String code, String caption, String iconPath) {
		super(code, caption, iconPath);
	}

	public AppMenuEntry(String code, String caption, String iconPath,
			Class<? extends BasePresenter<?, ? extends EventBus>> presenterClass) {
		super(code, caption, iconPath);
		this.presenterClass = presenterClass;
	}

	public Class<? extends BasePresenter<?, ? extends EventBus>> getAppPresenterClass() {
		return presenterClass;
	}
}
