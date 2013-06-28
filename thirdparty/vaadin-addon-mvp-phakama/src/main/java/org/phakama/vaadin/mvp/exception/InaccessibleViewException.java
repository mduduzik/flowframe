package org.phakama.vaadin.mvp.exception;

public class InaccessibleViewException extends RuntimeException {
	private static final long serialVersionUID = -655555021520780989L;
	
	public InaccessibleViewException(Throwable cause) {
		initCause(cause);
	}

	@Override
	public String getMessage() {
		return "View impelementations must be publicly accessible.";
	}
}