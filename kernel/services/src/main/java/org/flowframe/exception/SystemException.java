package org.flowframe.exception;

public class SystemException extends NestableException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6716084406519016601L;

	public SystemException() {
		super();
	}

	public SystemException(String msg) {
		super(msg);
	}

	public SystemException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}
}
