package org.flowframe.thirdparty.mvp.exception;

public class ViewConstructionException extends RuntimeException {
	private static final long serialVersionUID = -655555021520780989L;
	
	private final String message;

	public ViewConstructionException(String message) {
		this.message = message;
	}
	
	public ViewConstructionException(String message, Throwable cause) {
		initCause(cause);
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
}
