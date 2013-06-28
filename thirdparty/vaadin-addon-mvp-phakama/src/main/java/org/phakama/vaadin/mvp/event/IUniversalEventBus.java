package org.phakama.vaadin.mvp.event;

import org.phakama.vaadin.mvp.ILogger;
import org.phakama.vaadin.mvp.annotation.event.EventListener;

/**
 * The <code>IUniveralEventBus</code> is intended to be the centralized event
 * bus that can propagate events from one {@link IEventBus} to another. The
 * <code>IUniveralEventBus</code> should be one-to-one with a web application
 * instance and one-to-many with a browser sessions.
 * 
 * @author Sandile
 * 
 */
public interface IUniversalEventBus extends ILogger {
	/**
	 * Similar to {@link IEventBus#propagate(IEvent, EventScope)}. Propagates
	 * the provided <code>event</code> to every registered {@link IEventBus}
	 * excluding the the event bus responsible for the propagation. In the
	 * respective event busses, the <code>event</code> is propagated with a
	 * {@link EventScope#APPLICATION} scope.
	 * 
	 * @param event
	 *            the event to be propagated
	 * @param origin
	 *            the event bus responsible for propagating the
	 *            <code>event</code> parameter
	 * @return the number of {@link EventListener} methods successfully invoked
	 */
	int propagate(IEvent event, IEventBus origin);

	/**
	 * Binds the <code>eventBus</code> parameter to this universal event bus.
	 * This allows the <code>eventBus</code> to both propagate events
	 * universally and receive universally propagated events.
	 * 
	 * @param eventBus
	 *            the {@link IEventBus} to be registered
	 */
	void register(IEventBus eventBus);

	/**
	 * Unbinds the <code>eventBus</code> parameter from this universal event
	 * bus. This dettaches said <code>eventBus</code> from foreign events and
	 * event busses. This method is usually called when an <code>eventBus</code>
	 * is being destroyed.
	 * 
	 * @param eventBus
	 */
	void unregister(IEventBus eventBus);
}
