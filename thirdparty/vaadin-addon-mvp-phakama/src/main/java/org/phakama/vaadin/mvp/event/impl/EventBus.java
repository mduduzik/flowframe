package org.phakama.vaadin.mvp.event.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import org.phakama.vaadin.mvp.event.EventScope;
import org.phakama.vaadin.mvp.event.IEvent;
import org.phakama.vaadin.mvp.event.IEventBus;
import org.phakama.vaadin.mvp.event.IEventDelegate;
import org.phakama.vaadin.mvp.event.IEventHandler;
import org.phakama.vaadin.mvp.event.IEventHandlerRegistry;
import org.phakama.vaadin.mvp.event.IUniversalEventBus;
import org.phakama.vaadin.mvp.exception.EventListenerInvocationException;
import org.phakama.vaadin.mvp.exception.EventPropagationException;
import org.phakama.vaadin.mvp.presenter.IPresenter;
import org.phakama.vaadin.mvp.presenter.IPresenterRegistry;
import org.phakama.vaadin.mvp.view.IView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBus implements IEventBus {
	private static final long serialVersionUID = -5989527350073214759L;

	private static final Logger logger = LoggerFactory.getLogger(EventBus.class);

	private boolean logging = false;

	private IEventHandlerRegistry delegateRegistry;
	private IPresenterRegistry presenterRegistry;
	private IUniversalEventBus universalEventBus;

	public EventBus(IEventHandlerRegistry delegateRegistry, IPresenterRegistry presenterRegistry) {
		if (delegateRegistry == null) {
			throw new IllegalArgumentException("The delegateRegistry parameter cannot be null.");
		}
		if (presenterRegistry == null) {
			throw new IllegalArgumentException("The presenterRegistry parameter cannot be null.");
		}

		this.delegateRegistry = delegateRegistry;
		this.presenterRegistry = presenterRegistry;
	}

	@Override
	public int propagate(IEvent event, EventScope scope) {
		if (event == null) {
			throw new IllegalArgumentException("The event parameter cannot be null.");
		}
		if (event.getSource() == null) {
			throw new IllegalArgumentException("The event source cannot be null.");
		}
		if (scope == null) {
			throw new IllegalArgumentException("The registry parameter cannot be null.");
		}

		if (this.logging)
			logger.info("Attempting propagation of an event of type [{}] with scope [{}].", event.getClass(), scope);

		// Keep track of successful propagations so we can return the count
		int successfulPropagations = 0;

		Collection<IEventDelegate> delegates = null;
		// Choose propagation strategy
		switch (scope) {
		case CHILDREN:
			// This scope is only applicable for dispatchers of type IPresenter
			if (event.getSource() instanceof IPresenter<?>) {
				Collection<IPresenter<?>> children = this.presenterRegistry.childrenOf((IPresenter<?>) event.getSource());
				if (children != null) {
					delegates = this.delegateRegistry.find(event.getClass(), new ArrayList<IEventHandler>(children));
				}
			} else {
				throw new EventPropagationException("EventScope.CHILDREN is only applicable for event handlers of type IPresenter.");
			}
			break;
		case SIBLINGS:
			// This scope is only applicable for dispatchers of type IPresenter
			if (event.getSource() instanceof IPresenter<?>) {
				Collection<IPresenter<?>> siblings = this.presenterRegistry.siblingsOf((IPresenter<?>) event.getSource());
				if (siblings != null) {
					delegates = this.delegateRegistry.find(event.getClass(), new ArrayList<IEventHandler>(siblings));
				}
			} else {
				throw new EventPropagationException("EventScope.SIBLINGS is only applicable for event handlers of type IPresenter.");
			}
			break;
		case PARENT:
			// This scope is only applicable for dispatchers of type IPresenter
			// OR IView
			if (event.getSource() instanceof IPresenter<?>) {
				IPresenter<?> parent = this.presenterRegistry.parentOf((IPresenter<?>) event.getSource());
				if (parent != null) {
					// Since the find method only takes collections, we need to
					// wrap the parent
					ArrayList<IEventHandler> parentWrapper = new ArrayList<IEventHandler>(1);
					parentWrapper.add(parent);
					delegates = this.delegateRegistry.find(event.getClass(), parentWrapper);
				}
			} else if (event.getSource() instanceof IView) {
				IPresenter<?> parent = this.presenterRegistry.find((IView) event.getSource());
				if (parent != null) {
					// Since the find method only takes collections, we need to
					// wrap the parent
					ArrayList<IEventHandler> parentWrapper = new ArrayList<IEventHandler>(1);
					parentWrapper.add(parent);
					delegates = this.delegateRegistry.find(event.getClass(), parentWrapper);
				}
			} else {
				throw new EventPropagationException("EventScope.PARENT is only applicable for event handlers of type IPresenter or IView.");
			}
			break;
		case APPLICATION:
			delegates = this.delegateRegistry.find(event.getClass());
			break;
		case UNIVERSAL:
			// Keep in mind, EventScope.UNIVERSAL just means
			// EventScope.APPLICATION for every other
			// bus - we have to do our own EventScope.APPLICATION propagation
			delegates = this.delegateRegistry.find(event.getClass());
			if (this.universalEventBus != null) {
				successfulPropagations = this.universalEventBus.propagate(event, this);
			}
		}

		if (delegates != null) {
			for (IEventDelegate delegate : delegates) {
				try {
					delegate.invoke(event);
					if (this.logging)
						logger.debug("Event Type [{}] successfully propagated to Event Listener method [{}] within an Event Handler of type [{}].", event.getClass(),
								delegate.getMethod().getName(), delegate.getHandler().getClass());
					// This will only increment if invocation succeeded
					successfulPropagations++;
				} catch (IllegalArgumentException e) {
					throw new EventListenerInvocationException(
							"The parameter requirements of an @EventListener could not be satisfied. The delegate that failed to invoke was " + delegate + ".", e);
				} catch (IllegalAccessException e) {
					throw new EventListenerInvocationException("The an @EventListener could not be accessed. The delegate that failed to invoke was " + delegate + ".", e);
				} catch (InvocationTargetException e) {
					throw new EventListenerInvocationException(
							"There was an exception inside the body of an @EventListener method. The delegate that failed to invoke was " + delegate + ".", e);
				}
			}
		} else {
			if (this.logging)
				logger.debug("There were no event handlers listening for an event of type [{}].", event.getClass());
		}

		// Return the number of successful propagations
		return successfulPropagations;
	}

	@Override
	public IEventHandlerRegistry getEventHandlerRegistry() {
		return this.delegateRegistry;
	}

	@Override
	public IPresenterRegistry getPresenterRegistry() {
		return this.presenterRegistry;
	}

	@Override
	public void onBind(IUniversalEventBus universalEventBus) {
		if (this.logging)
			logger.debug("Event bus [{}] successfully bound to the universal event bus.", this);
		this.universalEventBus = universalEventBus;
	}

	@Override
	public void onUnbind() {
		if (this.logging)
			logger.debug("Event bus [{}] successfully unbound from the universal event bus.", this);
		this.universalEventBus = null;
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
