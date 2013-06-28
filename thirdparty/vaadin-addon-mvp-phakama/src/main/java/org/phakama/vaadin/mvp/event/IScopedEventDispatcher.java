package org.phakama.vaadin.mvp.event;

import org.phakama.vaadin.mvp.presenter.IPresenter;

/**
 * An <code>IScopedEventDispatcher</code> has the ability to dispatch an
 * {@link IEvent} to any {@link IEventHandler} <b>within a specified
 * {@link EventScope}</b> that may be listening for the type of
 * <code>event</code> being dispatched. The {@link IPresenter} interface extends
 * <code>IScopedEventDispatcher</code>, but any class may implement
 * <code>IScopedEventDispatcher</code>. The {@link IEventBus} is the intended
 * vehicle for event dispatching.
 * 
 * @author Sandile
 * 
 */
public interface IScopedEventDispatcher extends IEventDispatcher {
	/**
	 * Dispatches an <code>event</code> with a specified <code>scope</code>.
	 * This means that all concerned {@link IEventListener} methods within all
	 * {@linkplain IEventHandler}s included by the <code>scope</code> parameter
	 * listening for the type of the <code>event</code> parameter will be
	 * invoked. This method is intended to use an {@link IEventBus} to function.
	 * 
	 * @param event
	 *            the event to dispatch
	 * @param scope
	 *            the scope with which the <code>event</code> will be dispatched
	 * @return the number of invoked {@link IEventListener} methods listening
	 *         for the type of the <code>event</code> parameter
	 */
	int dispatch(IEvent event, EventScope scope);
}
