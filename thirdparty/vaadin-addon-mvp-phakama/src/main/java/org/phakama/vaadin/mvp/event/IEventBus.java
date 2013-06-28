package org.phakama.vaadin.mvp.event;

import org.phakama.vaadin.mvp.ILogger;
import org.phakama.vaadin.mvp.annotation.event.EventListener;
import org.phakama.vaadin.mvp.presenter.IPresenter;
import org.phakama.vaadin.mvp.presenter.IPresenterRegistry;
import org.phakama.vaadin.mvp.ui.MVPUI;

/**
 * An <code>IEventBus</code> allows publish-subscribe-style communication
 * between components without requiring the components to explicitly register
 * with one another (and thus be aware of each other). It is designed
 * exclusively to replace traditional Java in-process event distribution using
 * explicit registration.<br>
 * <br>
 * Event busses enable any entity which implements {@link IEvent} to be
 * propagated to, and then received by, {@link IEventHandler}s registered with
 * the event bus' {@link IEventHandlerRegistry}.<br>
 * <br>
 * The <code>IEventBus</code> is designed to have a one-to-one relationship with
 * an {@link MVPUI}, and a many-to-one relationship with an
 * {@link IUniversalEventBus}.
 * 
 * @author Sandile
 * 
 */
public interface IEventBus extends ILogger {
	/**
	 * This method is the bread-and-butter of publish-subscribe-style
	 * communication. The <code>event</code> parameter is used to invoke
	 * {@link EventListener} methods in registered {@link IEventHandler}s. The
	 * {@link EventListener} methods are invoked if they are listening for an
	 * {@link IEvent} type that is <i>assignable from the type of the
	 * <code>event</code> parameter</i>. The <code>scope</code> parameter
	 * restricts the nature of {@link IEventHandler} capable of handling the
	 * <code>event</code>. The most expensive scope is
	 * {@link EventScope#UNIVERSAL} since it fires to every {@link IEventBus}.
	 * 
	 * @param event
	 *            the event to propagate
	 * @param scope
	 *            the restriction for event propagation
	 * @return the number of {@link EventListener} methods successfully invoked
	 */
	int propagate(IEvent event, EventScope scope);

	/**
	 * This method is invoked when this <code>IEventBus</code> is bound to the
	 * {@link IUniversalEventBus}. It is recommended that the reference to the
	 * universal event bus is kept for event propagation with the
	 * {@link EventScope#UNIVERSAL} scope.
	 * 
	 * @param universalEventBus
	 *            the universal event bus
	 */
	void onBind(IUniversalEventBus universalEventBus);

	/**
	 * This method is invoked when this <code>IEventBus</code> is unbound from
	 * the {@link IUniversalEventBus} it was originally bound to. This method
	 * should be treated like a destructor.
	 */
	void onUnbind();

	/**
	 * Returns the {@link IEventHandlerRegistry} used by this
	 * <code>IEventBus</code> to propagate {@link IEvent}s.
	 * 
	 * @return the {@link IEventHandlerRegistry} used by this
	 *         <code>IEventBus</code> to propagate {@link IEvent}s
	 */
	IEventHandlerRegistry getEventHandlerRegistry();

	/**
	 * Returns the {@link IPresenterRegistry} used by this
	 * <code>IEventBus</code> to propagate {@link IEvent}s with
	 * {@link IPresenter} related {@link EventScope}s.
	 * 
	 * @return the {@link IPresenterRegistry} used by this
	 *         <code>IEventBus</code> to propagate {@link IEvent}s
	 */
	IPresenterRegistry getPresenterRegistry();
}
