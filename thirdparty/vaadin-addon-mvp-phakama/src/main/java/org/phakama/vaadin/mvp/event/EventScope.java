package org.phakama.vaadin.mvp.event;

import org.phakama.vaadin.mvp.ui.MVPUI;

/**
 * The <code>EventScope</code> defines the event propagation restriction for the
 * {@link IEventBus#propagate(IEvent, EventScope)} method.
 * 
 * @author Sandile
 */
public enum EventScope {
	/**
	 * If the event source is a view, the event propagates only to its
	 * presenter. If the event source is a presenter, the event propagates only
	 * to its parent presenter. If the parent of the event source is null, the
	 * event will not be propagated.
	 */
	PARENT,
	/**
	 * If the event source is a presenter and has a parent, then the event will
	 * be propagated to only other children of the event source's parent. If the
	 * parent of the event source is null, then the event will not be
	 * propagated.
	 */
	SIBLINGS,
	/**
	 * If the event source is a presenter, then this event will be propagated to
	 * only the children of the event source.
	 */
	CHILDREN,
	/**
	 * Propagate the event to all event listeners registered under the current
	 * {@link MVPUI}'s event bus. This scope is very broad, and, therefore, is
	 * mildly expensive.
	 */
	APPLICATION,
	/**
	 * Propagate the event to all event listeners registered in <b><i>every
	 * {@link MVPUI}'s event bus.</i></b> This scope should be employed with
	 * care, since propagating an event of this scope can be very expensive.
	 */
	UNIVERSAL
}
