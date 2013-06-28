package org.phakama.vaadin.mvp.exception;

public class EventPropagationException extends RuntimeException {
	private static final long serialVersionUID = -6771700238208659934L;

	private String message;
	
	public EventPropagationException(String message) {
		this.message = message;
	}
	
	public EventPropagationException(String message, Throwable cause) {
		initCause(cause);
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
}
