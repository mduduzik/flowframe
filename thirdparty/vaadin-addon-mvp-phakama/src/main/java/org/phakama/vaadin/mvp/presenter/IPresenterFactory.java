package org.phakama.vaadin.mvp.presenter;

import org.phakama.vaadin.mvp.ILogger;
import org.phakama.vaadin.mvp.view.IView;

public interface IPresenterFactory extends ILogger {
	<T extends IPresenter<? extends IView>> T create(Class<T> presenterClass);
	<T extends IPresenter<? extends IView>> T create(Class<T> presenterClass, IPresenter<? extends IView> parent);
	
	IPresenterRegistry getRegistry();
}
