package org.flowframe.thirdparty.mvp.exception;

public class InaccessiblePresenterException extends RuntimeException {
	private static final long serialVersionUID = -655555021520780989L;

	@Override
	public String getMessage() {
		return "Presenter sub-types must be accessible by the Presenter Factory.";
	}
}
