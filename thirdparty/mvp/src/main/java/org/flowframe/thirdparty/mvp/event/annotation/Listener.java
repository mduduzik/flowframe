package org.flowframe.thirdparty.mvp.event.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.flowframe.thirdparty.mvp.event.Event;

/**
 * Any method with the <code>Listener</code> annotation above it listens for the
 * event specified, and invokes the message when that event is received. The
 * event property of this annotation is <b>required</b> and therefore should not
 * be null. This annotation should only be used over methods that have <b>no
 * parameters</b> or <b>one parameter of the same type as the event property of
 * this annotation</b>. Listener methods can be named whatever the developer
 * likes. Listener methods may have a return type, but they will never return
 * anything to whatever fired the event.<br>
 * Example Usage:
 * 
 * <pre><code>
 * {@literal @}Listener(event = ExampleEvent.class)
 * public void exampleListener(ExampleEvent event) {
 * 	...
 * }
 * </pre></code>
 * Another Example Usage:
 * <pre><code>
 * {@literal @}Listener(event = AnotherExampleEvent.class)
 * public void anotherExampleListener() {
 * 	...
 * }
 * </pre></code>
 * @author Sandile
 * 
 */
@Target(ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Listener {
	/**
	 * The event that this listener is listening for.
	 * @return event type
	 */
	Class<? extends Event> event();
}
