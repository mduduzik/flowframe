package org.phakama.vaadin.mvp.event;

import java.io.Serializable;

import org.phakama.vaadin.mvp.annotation.event.EventListener;
import org.phakama.vaadin.mvp.ui.MVPUI;

/**
 * An <code>IEvent</code> is a message that an {@link IEventDispatcher} can send
 * to any number of {@link EventListener} methods within any number of
 * {@link IEventHandler}. An event may be used to indicate anything informative.
 * For instance, an event may be dispatched when a used submits a form, or even
 * when a database query finishes. <b>NOTE:</b> an <code>IEvent</code> must be
 * cloneable.
 * 
 * @author Sandile
 * 
 */
public interface IEvent extends Serializable, Cloneable {
	/**
	 * Checks if this event was propagated by the {@link IUniversalEventBus}.
	 * This usually indicates that this event was dispatched by the
	 * {@link IEventBus} of another {@link MVPUI}.
	 * 
	 * @return whether this event is foreign
	 */
	boolean isForeign();

	/**
	 * Flags this event as foreign, meaning this event was propagated by the
	 * {@link IUniversalEventBus}. An event should usually only be marked
	 * foreign by an {@link IUniversalEventBus}. Once an event is marked
	 * foreign, it cannot be marked domestic (un-foreign) again.
	 */
	void markForeign();

	/**
	 * Gets the {@link IEventDispatcher} responsible for dispatching this event.
	 * 
	 * @return this event's dispatcher
	 */
	IEventDispatcher getSource();

	/**
	 * Sets the {@link IEventDispatcher} responsible for dispatching this event.
	 * The source should <b>only be set once</b>.
	 * 
	 * @param source
	 *            this event's dispatcher
	 */
	void setSource(IEventDispatcher source);

	/**
	 * Creates a shallow-copied new instance of this event. This method should
	 * mimic the functionality of the standard Java <code>clone()</code>
	 * function. This method is used during the process of
	 * {@link EventScope#UNIVERSAL} event propagation.
	 * 
	 * @return a shallow-copied new instance of this event
	 */
	IEvent duplicate();
}
