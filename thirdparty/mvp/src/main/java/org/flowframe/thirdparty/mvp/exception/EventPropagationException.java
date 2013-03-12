package org.flowframe.thirdparty.mvp.exception;

public class EventPropagationException extends RuntimeException {
	private static final long serialVersionUID = -6771700238208659934L;

	private String message;
	
	public EventPropagationException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
}
