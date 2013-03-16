package org.vaadin.mvp.eventbus;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Create new instances of a typed event bus.
 * 
 * FIXME: this needs cleanup.
 */
public class EventBusManager {

	// FIXME: check if needed, otherwise remove to not keep more references.
	// private Map<EventBus, EventBusHandler> busHandlers;

	private Map<Class<? extends EventBus>, EventBus> eventBusses;

	private CustomizedEventReceiverRegistry handlerRegistry = new CustomizedEventReceiverRegistry();

	private AnonymousEventRegistry eventRegistry = new AnonymousEventRegistry();

	public EventBusManager() {
		// busHandlers = new HashMap<EventBus, EventBusHandler>();
		eventBusses = new HashMap<Class<? extends EventBus>, EventBus>();
	}

	/**
	 * Create an event bus of given <code>busType</code> and register the
	 * <code>subscriber</code>.
	 * 
	 * @param <T>
	 *            Event bus type (a Java interface type)
	 * @param busType
	 *            Event bus interface class
	 * @param subscriber
	 *            Subsriber to register
	 * @return event bus instance
	 */
	@SuppressWarnings("unchecked")
	public <T extends EventBus> T register(Class<T> busType, Object subscriber) {
		if (!eventBusses.containsKey(busType)) {
			eventBusses.put(busType, create(busType));
		}
		this.handlerRegistry.newReceiver(subscriber);
		EventBus eventBus = eventBusses.get(busType);
		return (T) eventBus;
	}

	/**
	 * Create an event bus of given <code>busType</code> and register the
	 * existing <code>eventBus</code>.
	 * 
	 * @param <T>
	 *            Event bus type (a Java interface type)
	 * @param busType
	 *            Event bus interface class
	 * @param eventBus
	 *            Existing event bus from other Event Bus Manager
	 * @return event bus instance
	 */
	@SuppressWarnings("unchecked")
	public <T extends EventBus> T register(Class<T> busType, EventBus eventBus) {
		if (!eventBusses.containsKey(busType)) {
			eventBusses.put(busType, eventBus);
		}
		
		try {
			EventBusHandler handler = (EventBusHandler) Proxy.getInvocationHandler(eventBus);
			if (handler != null) {
				this.eventRegistry.registerEvents(busType, handler);
			}
		} catch (IllegalArgumentException e) {
		}
		
		return (T) eventBus;
	}

	/**
	 * Fire an event by name and parameter. If multiple events exist by this
	 * name and method signature, they will all be fired.
	 * 
	 * @param eventName
	 *            name of the eventbus method representing this event
	 * @param args
	 *            the arguments of the the eventbus method
	 * @return true if successful
	 */
	public boolean fireAnonymousEvent(String eventName, Object[] args) {
		return this.eventRegistry.fireAnonymousEvent(eventName, args);
	}
	
	/**
	 * Fire an event by name and parameter. If multiple events exist by this
	 * name and method signature, they will all be fired.
	 * 
	 * @param eventName
	 *            name of the eventbus method representing this event
	 * @return true if successful
	 */
	public boolean fireAnonymousEvent(String eventName) {
		return this.eventRegistry.fireAnonymousEvent(eventName, new Object[0]);
	}

	/**
	 * Add a subscriber.
	 * 
	 * @param subscriber
	 */
	public void addSubscriber(Object subscriber) {
		this.handlerRegistry.newReceiver(subscriber);
	}

	/**
	 * 
	 * @param <T>
	 * @param busType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends EventBus> T getEventBus(Class<? extends EventBus> busType) {
		assertEventBus(busType);
		return (T) eventBusses.get(busType);
	}

	/**
	 * Assert an event bus of type <code>busType</code> exists. If no event bus
	 * with required type is present, then an instance is created.
	 * 
	 * @param <T>
	 *            Event bus type (a Java interface type)
	 * @param busType
	 *            Event bus interface class
	 */
	private <T extends EventBus> void assertEventBus(Class<T> busType) {
		if (!eventBusses.containsKey(busType)) {
			eventBusses.put(busType, create(busType));
		}
	}

	/**
	 * 
	 * @param <T>
	 * @param type
	 * @return
	 */
	protected <T extends EventBus> T create(Class<T> type) {
		EventBusHandler handler = new EventBusHandler(this, handlerRegistry, type.getName());
		this.eventRegistry.registerEvents(type, handler);
		@SuppressWarnings("unchecked")
		T bus = (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[] { type }, handler);
		// busHandlers.put(bus, handler);
		return bus;
	}
}
