package org.phakama.vaadin.mvp.event.one;

import org.phakama.vaadin.mvp.annotation.event.EventListener;
import org.phakama.vaadin.mvp.event.IEventHandler;

public class OneEventHandler implements IEventHandler {
	private static final long serialVersionUID = -2637746704496710598L;

	@EventListener(event = YetAnotherOneTestEvent.class, excludes = { FinallyAnotherOneTestEvent.class })
	public void onYetAnotherOne() {
	}
	
	@EventListener(event = AnotherOneTestEvent.class)
	public void onAnotherOne() {
	}
	
	@EventListener(event = FinallyAnotherOneTestEvent.class)
	public void onFinallyAnotherOne() {
	}
}
