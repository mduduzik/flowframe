package org.phakama.vaadin.mvp.exception;

public class FieldInjectionException extends RuntimeException {
	private static final long serialVersionUID = 8726355021520780989L;
	
	private final String message;

	public FieldInjectionException(String message) {
		this.message = message;
	}
	
	public FieldInjectionException(String message, Throwable cause) {
		initCause(cause);
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
}
