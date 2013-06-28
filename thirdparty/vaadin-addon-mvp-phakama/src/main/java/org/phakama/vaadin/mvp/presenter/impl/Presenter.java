package org.phakama.vaadin.mvp.presenter.impl;

import org.phakama.vaadin.mvp.annotation.field.EventBus;
import org.phakama.vaadin.mvp.annotation.field.Factory;
import org.phakama.vaadin.mvp.event.EventScope;
import org.phakama.vaadin.mvp.event.IEvent;
import org.phakama.vaadin.mvp.event.IEventBus;
import org.phakama.vaadin.mvp.presenter.IPresenter;
import org.phakama.vaadin.mvp.presenter.IPresenterFactory;
import org.phakama.vaadin.mvp.view.IView;

public class Presenter<T extends IView> implements IPresenter<T> {
	private static final long serialVersionUID = 5131211825391491296L;

	private T view;
	@EventBus
	private IEventBus eventBus;
	@Factory
	private IPresenterFactory factory;

	@Override
	public T getView() {
		return view;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getName());
		builder.append(':');
		builder.append('{');
		if (view == null) {
			builder.append("<null view>, ");
		} else {
			builder.append(view.getClass().getSimpleName());
			builder.append(", ");
		}
		if (eventBus == null) {
			builder.append("<null event bus>");
		} else {
			builder.append(eventBus.getClass().getSimpleName());
		}
		builder.append('}');

		return builder.toString();
	}

	@Override
	public int dispatch(IEvent event) {
		return dispatch(event, EventScope.APPLICATION);
	}

	@Override
	public int dispatch(IEvent event, EventScope scope) {
		if (event == null) {
			throw new IllegalArgumentException("The event parameter was null.");
		}
		if (scope == null) {
			throw new IllegalArgumentException("The scope parameter was null.");
		}

		event.setSource(this);
		return this.eventBus.propagate(event, scope);
	}

	@Override
	public <E extends IPresenter<? extends IView>> E createChild(Class<E> presenterClass) {
		E child = this.factory.create(presenterClass, this);
		return child;
	}

	@Override
	public void onBind(T view) {
		if (view == null) {
			throw new IllegalArgumentException("The view parameter cannot be null.");
		}
		// Check that we don't have an existing view already
		if (this.view != null) {
			// We're killing our existing view and getting a new one >:-D
			this.view.onUnbind();
		}
		// Bend the new view to this presenter
		this.view = view;
		this.view.onBind();
	}

	@Override
	public void onUnbind() {
		// Kill the view first
		this.view.onUnbind();
		// Get rid of instance variables
		this.eventBus = null;
		this.factory = null;
		this.view = null;
		// Request that this class be garbage collected
		try {
			finalize();
		} catch (Throwable e) {
			// Doesn't matter if finalize failed
		}
	}

	@Override
	public void onReady() {
		// This method is intended to be overridden
		// by subclasses which want some sort of initialization
	}

}
