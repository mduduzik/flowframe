package org.phakama.vaadin.mvp.presenter;

import junit.framework.Assert;

import org.junit.Test;
import org.phakama.vaadin.mvp.event.IEventBus;
import org.phakama.vaadin.mvp.event.IEventHandlerRegistry;
import org.phakama.vaadin.mvp.event.impl.EventBus;
import org.phakama.vaadin.mvp.event.impl.EventHandlerRegistry;
import org.phakama.vaadin.mvp.event.one.OneTestEvent;
import org.phakama.vaadin.mvp.presenter.IPresenterFactory;
import org.phakama.vaadin.mvp.presenter.IPresenterRegistry;
import org.phakama.vaadin.mvp.presenter.impl.PresenterFactory;
import org.phakama.vaadin.mvp.presenter.impl.registry.PresenterRegistry;
import org.phakama.vaadin.mvp.presenter.one.AnotherOnePresenter;
import org.phakama.vaadin.mvp.presenter.one.OnePresenter;
import org.phakama.vaadin.mvp.view.IViewFactory;
import org.phakama.vaadin.mvp.view.IViewRegistry;
import org.phakama.vaadin.mvp.view.impl.ViewFactory;
import org.phakama.vaadin.mvp.view.impl.ViewRegistry;

public class PresenterFactoryTest {

	@Test
	public void testCreate() {
		IViewRegistry viewRegistry = new ViewRegistry();
		IViewFactory viewFactory = new ViewFactory(viewRegistry);
		IPresenterRegistry presenterRegistry = new PresenterRegistry();
		IEventHandlerRegistry delegateRegistry = new EventHandlerRegistry();
		IEventBus eventBus = new EventBus(delegateRegistry, presenterRegistry);
		IPresenterFactory factory = new PresenterFactory(eventBus, viewFactory, presenterRegistry);
		OnePresenter presenter = factory.create(OnePresenter.class);
		Assert.assertNotNull(presenter);
		Assert.assertNotNull(presenter.getView());
		
		try {
			OnePresenter child = presenter.createChild(OnePresenter.class);
			Assert.assertNotNull(child);
		} catch (NullPointerException e) {
			Assert.fail("The factory was null.");
		}
		
		try {
			presenter.dispatch(new OneTestEvent());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCreateWithParent() {
		IViewRegistry viewRegistry = new ViewRegistry();
		IViewFactory viewFactory = new ViewFactory(viewRegistry);
		IPresenterRegistry presenterRegistry = new PresenterRegistry();
		IEventHandlerRegistry delegateRegistry = new EventHandlerRegistry();
		IEventBus eventBus = new EventBus(delegateRegistry, presenterRegistry);
		IPresenterFactory factory = new PresenterFactory(eventBus, viewFactory, presenterRegistry);
		OnePresenter presenter = factory.create(OnePresenter.class);
		AnotherOnePresenter childPresenter = factory.create(AnotherOnePresenter.class, presenter);
		Assert.assertNotNull(childPresenter);
		Assert.assertNotNull(childPresenter.getView());
		
		try {
			childPresenter.dispatch(new OneTestEvent());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		
		Assert.assertEquals(presenter, presenterRegistry.parentOf(childPresenter));
	}

}
