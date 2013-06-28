package org.phakama.vaadin.mvp.event.impl;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.ListIterator;

import org.phakama.vaadin.mvp.event.EventScope;
import org.phakama.vaadin.mvp.event.IEvent;
import org.phakama.vaadin.mvp.event.IEventBus;
import org.phakama.vaadin.mvp.event.IUniversalEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UniversalEventBus implements IUniversalEventBus {
	private static final long serialVersionUID = 6236360073352874831L;

	private static final Logger logger = LoggerFactory.getLogger(UniversalEventBus.class);

	private boolean logging = false;

	private ArrayList<SoftReference<IEventBus>> eventBusList = new ArrayList<SoftReference<IEventBus>>();

	@Override
	public int propagate(IEvent event, IEventBus origin) {
		if (event == null) {
			throw new IllegalArgumentException("The event parameter cannot be null.");
		}
		if (origin == null) {
			throw new IllegalArgumentException("The origin parameter cannot be null.");
		}

		if (this.logging)
			logger.info("Attempting propagation of an event of type [{}].", event.getClass());

		// Keep track of successful propagations so we can return the count
		int successfulPropagations = 0;

		synchronized (this.eventBusList) {
			ArrayList<SoftReference<IEventBus>> killList = null;
			IEventBus eventBus = null;

			if (this.logging)
				logger.info("[{}] Event Busses are available for propagation.", this.eventBusList.size());
			for (SoftReference<IEventBus> eventBusRef : this.eventBusList) {
				if (eventBusRef != null) {
					eventBus = eventBusRef.get();
					if (eventBus == null) {
						// This reference died, so lets give it a proper funeral
						if (killList == null) {
							killList = new ArrayList<SoftReference<IEventBus>>();
						}
						killList.add(eventBusRef);
					} else {
						// ITS ALIIVVEE (the soft reference didn't perish in the
						// night); Also, make sure we don't propagate back to
						// the original bus
						if (!eventBus.equals(origin)) {
							// Make sure everyone knows that the event came from
							// another guy
							if (this.logging)
								logger.info("Attempting to duplicate event [{}] for foreign propagation.", event);
							event = event.duplicate();
							// Make a copy of the event for the other busses so
							// we can markForeign() without worrying
							event.markForeign();
							eventBus.propagate(event, EventScope.APPLICATION);
							if (this.logging)
								logger.debug("Event [{}] successfully propagated to Event Bus [{}].", event, eventBus);
							// This will only increment if invocation succeeded
							successfulPropagations++;
						}
					}
				}
			}
			// Ok, lets kill all the dead busses
			if (killList != null) {
				if (this.logging)
					logger.debug("[{}] Event Bus references died.", killList.size());
				for (SoftReference<IEventBus> deadEventBusRef : killList) {
					this.eventBusList.remove(deadEventBusRef);
				}
			}
		}

		// Return the number of successful propagations
		return successfulPropagations;
	}

	@Override
	public void register(IEventBus eventBus) {
		if (eventBus == null) {
			throw new IllegalArgumentException("The eventBus parameter cannot be null.");
		}

		synchronized (this.eventBusList) {
			for (SoftReference<IEventBus> eventBusRef : this.eventBusList) {
				if (eventBus.equals(eventBusRef.get())) {
					// If its already in the list, we don't need to be here
					return;
				}
			}
			// Everything's going according to plan, so add the new bus to our
			// list
			this.eventBusList.add(new SoftReference<IEventBus>(eventBus));
			eventBus.onBind(this);
		}
	}

	@Override
	public void unregister(IEventBus eventBus) {
		if (eventBus == null) {
			throw new IllegalArgumentException("The eventBus parameter cannot be null.");
		}

		synchronized (this.eventBusList) {
			SoftReference<IEventBus> eventBusRef = null;
			ListIterator<SoftReference<IEventBus>> it = this.eventBusList.listIterator();
			while (it.hasNext()) {
				eventBusRef = it.next();
				if (eventBus.equals(eventBusRef.get())) {
					eventBusRef.clear();
					it.remove();
					eventBus.onUnbind();
					return;
				}
			}
		}
	}

	@Override
	public boolean isLogging() {
		return this.logging;
	}

	@Override
	public void enableLogging() {
		this.logging = true;
	}

	@Override
	public void disableLogging() {
		this.logging = false;
	}

}
