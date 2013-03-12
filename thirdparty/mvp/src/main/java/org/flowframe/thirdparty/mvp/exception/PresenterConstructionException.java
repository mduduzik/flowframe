package org.flowframe.thirdparty.mvp.exception;

public class PresenterConstructionException extends RuntimeException {
	private static final long serialVersionUID = -655555021520780989L;
	
	private String message;

	public PresenterConstructionException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
}
