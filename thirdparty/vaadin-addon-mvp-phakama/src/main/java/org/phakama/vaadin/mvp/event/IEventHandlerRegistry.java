package org.phakama.vaadin.mvp.event;

import java.util.Collection;

import org.phakama.vaadin.mvp.ILogger;

public interface IEventHandlerRegistry extends ILogger {
	void register(IEventHandler handler);
	void unregister(IEventHandler handler);
	void clear();
	/**
	 * Gets the number of currently registered event handlers.
	 * @return the number of currently registered event handlers.
	 */
	int size();
	
	Collection<IEventDelegate> find(Class<? extends IEvent> eventClass);
	Collection<IEventDelegate> find(Class<? extends IEvent> eventClass, Collection<IEventHandler> handlers);
}