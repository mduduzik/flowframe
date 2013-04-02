package org.flowframe.exception;

public class ApplicationException extends NestableException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7255001338264416017L;

	public ApplicationException() {
		super();
	}

	public ApplicationException(String msg) {
		super(msg);
	}

	public ApplicationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ApplicationException(Throwable cause) {
		super(cause);
	}
}
