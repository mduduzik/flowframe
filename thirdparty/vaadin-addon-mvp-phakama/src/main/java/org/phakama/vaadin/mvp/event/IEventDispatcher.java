package org.phakama.vaadin.mvp.event;

import java.io.Serializable;

import org.phakama.vaadin.mvp.presenter.IPresenter;
import org.phakama.vaadin.mvp.view.IView;

/**
 * An <code>IEventDispatcher</code> has the ability to dispatch an
 * {@link IEvent} to any {@link IEventHandler} that may be listening for the
 * type of <code>event</code> being dispatched. The {@link IPresenter} and
 * {@link IView} interfaces extend <code>IEventDispatcher</code>, but any class
 * may implement <code>IEventDispatcher</code>. The {@link IEventBus} is the
 * intended vehicle for event dispatching.
 * 
 * @author Sandile
 * 
 */
public interface IEventDispatcher extends Serializable {
	/**
	 * Dispatches an <code>event</code>. This means that all
	 * {@link IEventListener} methods listening for the type of the
	 * <code>event</code> will be invoked. This method is intended to use the an
	 * {@link IEventBus} to function.
	 * 
	 * @param event
	 *            the event to dispatch
	 * @return the number of invoked {@link IEventListener} methods listening
	 *         for the type of the <code>event</code> parameter
	 */
	int dispatch(IEvent event);
}
