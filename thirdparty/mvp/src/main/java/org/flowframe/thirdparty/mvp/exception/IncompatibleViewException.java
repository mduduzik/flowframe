package org.flowframe.thirdparty.mvp.exception;

import org.flowframe.thirdparty.mvp.presenter.Presenter;
import org.flowframe.thirdparty.mvp.view.IView;

public class IncompatibleViewException extends RuntimeException {
	private static final long serialVersionUID = -6778323253404048450L;
	
	private String message;
	
	public IncompatibleViewException(IView incompatibleView, Presenter<? extends IView> presenter) {
		this.message = "View type " + incompatibleView.getClass() + " is not compatible with presenter type " + presenter.getClass() + ".";
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
}
