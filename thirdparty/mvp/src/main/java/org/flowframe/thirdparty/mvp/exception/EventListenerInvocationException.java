package org.flowframe.thirdparty.mvp.exception;

public class EventListenerInvocationException extends RuntimeException {
	private static final long serialVersionUID = 215640467452512354L;

	private String message;
	
	public EventListenerInvocationException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
