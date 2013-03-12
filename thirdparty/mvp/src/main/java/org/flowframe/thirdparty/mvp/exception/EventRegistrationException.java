package org.flowframe.thirdparty.mvp.exception;

public class EventRegistrationException extends RuntimeException {
	private static final long serialVersionUID = 2156404674525470854L;

	private String message;
	
	public EventRegistrationException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
