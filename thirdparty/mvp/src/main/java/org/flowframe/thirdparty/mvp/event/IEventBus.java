package org.flowframe.thirdparty.mvp.event;

import java.io.Serializable;

import org.flowframe.thirdparty.mvp.event.annotation.Listener;
import org.flowframe.thirdparty.mvp.presenter.Presenter;
import org.flowframe.thirdparty.mvp.view.IView;

/**
 * An {@link IEventBus} is how {@link Event}s are propagated to
 * {@link Presenter}s listening for them. Event busses are intended to be
 * created on a one-eventbus-per-session basis, but implementation of this
 * interface is subject to whims of the developer.
 * 
 * @author Sandile
 */
public interface IEventBus extends Serializable {
	/**
	 * Registers a presenter to this event bus. This means that all methods with
	 * a {@link Listener} annotation will be able to listen to and handle
	 * {@link Event}s. <b>NOTE:</b> Once a presenter is registered, it will
	 * cannot be garbage collected. In order for it to be de-allocated in
	 * memory, use the <code>unregister()</code> method.
	 * 
	 * @param presenter
	 *            the presenter to be registered
	 */
	void register(Presenter<? extends IView> presenter);

	/**
	 * Unregisters a presenter from this event bus. This means that listener
	 * methods will no longer be able to handle events propagated by this bus.
	 * This method also restores the ability of the presenter to be
	 * garbage-collected once again.
	 * 
	 * @param presenter
	 *            the presenter to be unregistered
	 */
	void unregister(Presenter<? extends IView> presenter);

	/**
	 * Propagates the event e to every listener method listening for it that is
	 * also registered in this event bus. The event is propagated according to
	 * the <code>getScope()</code> method of the event which returns an
	 * {@link EventScope}. The source of the event must not be null.
	 * 
	 * @param e
	 *            the event to be fired
	 * @param source
	 *            the originator of the event being fired
	 */
	void fire(Object source, Event e);

	/**
	 * Propagates the event e to every listener method listening for it that is
	 * also registered in this event bus. The event is propagated according to
	 * the <code>getScope()</code> method of the event which returns an
	 * {@link EventScope}. The source of the event must not be null. This
	 * overload also will override the scope of the event its firing. The scope
	 * must not be null.
	 * 
	 * @param e
	 *            the event to be fired
	 * @param scope
	 *            the scope to fire this event with
	 * @param source
	 *            the originator of the event being fired
	 */
	void fire(Object source, EventScope scope, Event e);
}
