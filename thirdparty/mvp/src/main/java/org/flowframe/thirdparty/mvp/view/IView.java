package org.flowframe.thirdparty.mvp.view;

import org.flowframe.thirdparty.mvp.presenter.Presenter;
import org.flowframe.thirdparty.mvp.presenter.PresenterFactory;

import com.vaadin.ui.Component;

/**
 * The view interface represents an arbitrary view type that a presenter can use
 * to present data to the user. This interface may be implemented in any fashion
 * that the developer sees fit, but the standard base implementation for an
 * <code>IView</code> is a {@link View}. <code>IView</code>s are valid Vaadin
 * components, and may be included in Vaadin layouts and component containers.
 * 
 * @author Sandile
 */
public interface IView extends Component {
	/**
	 * Initializes the user interface artifacts so that they are ready to
	 * display data. This method should be used in place of the default
	 * constructor for initialization of the view. Furthermore, this method will
	 * always be invoked before its presenter's <code>init()</code> method.
	 */
	void init();

	/**
	 * Returns a reference to the owner of this view if it exists. If the owner
	 * of this view does not exist, this method returns null. <b>Note:</b> the
	 * owner of a {@link View} is its {@link Presenter} when created by a
	 * {@link PresenterFactory}.
	 * 
	 * @return the owner of this view
	 */
	Object getOwner();
}
