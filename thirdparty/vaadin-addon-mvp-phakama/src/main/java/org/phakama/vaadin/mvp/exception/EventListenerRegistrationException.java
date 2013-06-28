package org.phakama.vaadin.mvp.exception;

public class EventListenerRegistrationException extends RuntimeException {
	private static final long serialVersionUID = 2156404674525470854L;

	private String message;
	
	public EventListenerRegistrationException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
