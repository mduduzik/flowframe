package org.phakama.vaadin.mvp.ui;

import org.phakama.vaadin.mvp.event.IEventBus;
import org.phakama.vaadin.mvp.event.IEventHandler;
import org.phakama.vaadin.mvp.event.IEventHandlerRegistry;
import org.phakama.vaadin.mvp.event.IUniversalEventBus;
import org.phakama.vaadin.mvp.event.impl.EventBus;
import org.phakama.vaadin.mvp.event.impl.EventHandlerRegistry;
import org.phakama.vaadin.mvp.event.impl.UniversalEventBus;
import org.phakama.vaadin.mvp.presenter.IPresenter;
import org.phakama.vaadin.mvp.presenter.IPresenterFactory;
import org.phakama.vaadin.mvp.presenter.IPresenterRegistry;
import org.phakama.vaadin.mvp.presenter.impl.PresenterFactory;
import org.phakama.vaadin.mvp.presenter.impl.registry.PresenterRegistry;
import org.phakama.vaadin.mvp.view.IView;
import org.phakama.vaadin.mvp.view.IViewFactory;
import org.phakama.vaadin.mvp.view.IViewRegistry;
import org.phakama.vaadin.mvp.view.impl.ViewFactory;
import org.phakama.vaadin.mvp.view.impl.ViewRegistry;

import com.vaadin.ui.UI;

public abstract class MVPUI extends UI {
	private static final long serialVersionUID = 867774269253987232L;
	// Create instance variables with multi-session scope as static
	private static final IUniversalEventBus universalEventBus = new UniversalEventBus();
	private static final IViewRegistry viewRegistry = new ViewRegistry();
	// Critical instance variables
	private final IEventHandlerRegistry eventHandlerRegistry;
	private final IPresenterRegistry presenterRegistry;
	private final IViewFactory viewFactory;
	private final IPresenterFactory presenterFactory;
	// Event Bus should be visible from sub-classes
	protected final IEventBus eventBus;
	
	public MVPUI() {
		super();
		// Initialize critical instance variables
		this.eventHandlerRegistry = new EventHandlerRegistry();
		this.presenterRegistry = new PresenterRegistry();
		this.viewFactory = new ViewFactory(MVPUI.viewRegistry);
		this.eventBus = new EventBus(this.eventHandlerRegistry, this.presenterRegistry);
		this.presenterFactory = new PresenterFactory(this.eventBus, this.viewFactory, this.presenterRegistry);
		// Register our event bus under the universal event bus
		MVPUI.universalEventBus.register(this.eventBus);
	}
	
	@Override
	public void detach() {
		super.detach();
		// Unregister our event bus from the universal event bus
		MVPUI.universalEventBus.unregister(this.eventBus);
		// TODO Clear away session-specific registry contents here
	}

	protected <T extends IPresenter<? extends IView>> T createPresenter(Class<T> presenterClass) {
		return this.presenterFactory.create(presenterClass);
	}
	
	protected void registerView(Class<? extends IView> viewImplClass) {
		MVPUI.viewRegistry.register(viewImplClass);
	}
	
	protected void unregisterView(Class<? extends IView> viewImplClass) {
		MVPUI.viewRegistry.unregister(viewImplClass);
	}
	
	protected void registerEventHandler(IEventHandler eventHandler) {
		this.eventHandlerRegistry.register(eventHandler);
	}
	
	protected void unregisterEventHandler(IEventHandler eventHandler) {
		this.eventHandlerRegistry.unregister(eventHandler);
	}
}
