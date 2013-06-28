package org.phakama.vaadin.mvp.annotation.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.phakama.vaadin.mvp.event.IEvent;
import org.phakama.vaadin.mvp.event.IEventBus;
import org.phakama.vaadin.mvp.event.IEventHandler;

/**
 * This annotation is used to mark methods within an {@link IEventHandler}
 * intended to receive events. Every <code>EventListener</code> annotation must
 * specify an event type to listen to. This event type must representer a class
 * implementing {@linkplain IEvent} (instead of an interface extending
 * <code>IEvent</code>). The other parameters of this annotation are optional.
 * 
 * @author Sandile
 * 
 */
@Target(ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface EventListener {
	/**
	 * The event type this listener is listening for. The event type must
	 * representer a class implementing {@linkplain IEvent} (instead of an
	 * interface extending <code>IEvent</code>). The event type parameter is a
	 * <b>required</b> parameter.
	 * 
	 * @return the type of the event we're listening for
	 */
	Class<? extends IEvent> event();

	/**
	 * The event types this listener should never receive. This is an optional
	 * parameter.
	 * 
	 * @return the event types this listener should never receive
	 */
	Class<? extends IEvent>[] excludes() default {};

	/**
	 * This parameter determines whether this listener can listener to events
	 * propagated by a different {@link IEventBus}. This is an optional
	 * parameter. The default value of this property is <code>true</code>.
	 * 
	 * @return whether events from other event busses can be received
	 */
	boolean allowForeign() default true;
}
