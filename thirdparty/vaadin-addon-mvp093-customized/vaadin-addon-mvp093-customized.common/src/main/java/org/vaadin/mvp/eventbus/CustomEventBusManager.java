package org.vaadin.mvp.eventbus;


/**
 * Create new instances of a typed event bus.
 * 
 */
public class CustomEventBusManager extends EventBusManager {

	public CustomEventBusManager() {
		super();
		super.handlerRegistry = new CustomEventReceiverRegistry();
	}
	
}
