package org.phakama.vaadin.mvp.event;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.Assert;

import org.junit.Test;
import org.phakama.vaadin.mvp.event.IEventDelegate;
import org.phakama.vaadin.mvp.event.IEventHandler;
import org.phakama.vaadin.mvp.event.IEventHandlerRegistry;
import org.phakama.vaadin.mvp.event.impl.EventHandlerRegistry;
import org.phakama.vaadin.mvp.event.one.AnotherOneTestEvent;
import org.phakama.vaadin.mvp.event.one.FinallyAnotherOneTestEvent;
import org.phakama.vaadin.mvp.event.one.OneEventHandler;
import org.phakama.vaadin.mvp.event.one.YetAnotherOneTestEvent;

public class EventHandlerRegistryTest {

	@Test
	public void testRegister() {
		IEventHandlerRegistry delegateRegistry = new EventHandlerRegistry();
		Assert.assertTrue(delegateRegistry.size() == 0);
		delegateRegistry.register(new OneEventHandler());
		Assert.assertTrue(delegateRegistry.size() == 1);
		delegateRegistry.register(new OneEventHandler());
		Assert.assertTrue(delegateRegistry.size() == 2);
		delegateRegistry.register(new OneEventHandler());
		Assert.assertTrue(delegateRegistry.size() == 3);
	}

	@Test
	public void testUnregister() {
		IEventHandlerRegistry delegateRegistry = new EventHandlerRegistry();

		OneEventHandler oneHandler1 = new OneEventHandler();
		OneEventHandler oneHandler2 = new OneEventHandler();
		OneEventHandler oneHandler3 = new OneEventHandler();

		Assert.assertTrue(delegateRegistry.size() == 0);
		delegateRegistry.register(oneHandler1);
		Assert.assertTrue(delegateRegistry.size() == 1);
		delegateRegistry.register(oneHandler2);
		Assert.assertTrue(delegateRegistry.size() == 2);
		delegateRegistry.register(oneHandler3);
		Assert.assertTrue(delegateRegistry.size() == 3);
		delegateRegistry.unregister(oneHandler3);
		Assert.assertTrue(delegateRegistry.size() == 2);
		delegateRegistry.unregister(oneHandler2);
		Assert.assertTrue(delegateRegistry.size() == 1);
		delegateRegistry.unregister(oneHandler1);
		Assert.assertTrue(delegateRegistry.size() == 0);
	}

	@Test
	public void testFind() {
		IEventHandlerRegistry delegateRegistry = new EventHandlerRegistry();

		OneEventHandler oneHandler1 = new OneEventHandler();

		delegateRegistry.register(oneHandler1);
		
		Collection<IEventDelegate> anotherOneDelegates = delegateRegistry.find(AnotherOneTestEvent.class);
		Assert.assertTrue(anotherOneDelegates.size() == 1);
		Collection<IEventDelegate> yetAnotherOneDelegates = delegateRegistry.find(YetAnotherOneTestEvent.class);
		Assert.assertTrue(yetAnotherOneDelegates.size() == 2);
		Collection<IEventDelegate> finallyAnotherOneDelegates = delegateRegistry.find(FinallyAnotherOneTestEvent.class);
		Assert.assertTrue(finallyAnotherOneDelegates.size() == 3);
	}

	@Test
	public void testFindWithHandlers() {
		IEventHandlerRegistry delegateRegistry = new EventHandlerRegistry();

		OneEventHandler oneHandler1 = new OneEventHandler();
		OneEventHandler oneHandler2 = new OneEventHandler();
		OneEventHandler oneHandler3 = new OneEventHandler();

		delegateRegistry.register(oneHandler1);
		delegateRegistry.register(oneHandler2);
		delegateRegistry.register(oneHandler3);
		
		ArrayList<IEventHandler> filteredHandlersBig = new ArrayList<IEventHandler>();
		filteredHandlersBig.add(oneHandler1);
		filteredHandlersBig.add(oneHandler2);
		
		ArrayList<IEventHandler> filteredHandlersSmall = new ArrayList<IEventHandler>();
		filteredHandlersSmall.add(oneHandler3);
		
		Collection<IEventDelegate> finallyAnotherOneDelegatesBig = delegateRegistry.find(FinallyAnotherOneTestEvent.class, filteredHandlersBig);
		Assert.assertTrue(finallyAnotherOneDelegatesBig.size() == 6);
		
		Collection<IEventDelegate> finallyAnotherOneDelegatesSmall = delegateRegistry.find(FinallyAnotherOneTestEvent.class, filteredHandlersSmall);
		Assert.assertTrue(finallyAnotherOneDelegatesSmall.size() == 3);
	}
}
