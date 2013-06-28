package org.phakama.vaadin.mvp.exception;

public class PresenterConstructionException extends RuntimeException {
	private static final long serialVersionUID = -655555021520780989L;
	
	private String message;

	public PresenterConstructionException(String message) {
		this.message = message;
	}
	
	public PresenterConstructionException(String message, Throwable cause) {
		initCause(cause);
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
}
