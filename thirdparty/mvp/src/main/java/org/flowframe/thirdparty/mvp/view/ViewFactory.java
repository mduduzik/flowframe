package org.flowframe.thirdparty.mvp.view;

import java.io.Serializable;

import org.flowframe.thirdparty.mvp.event.IEventBus;
import org.flowframe.thirdparty.mvp.exception.InaccessibleViewException;
import org.flowframe.thirdparty.mvp.exception.ViewConstructionException;
import org.flowframe.thirdparty.mvp.presenter.Presenter;

/**
 * Creates new instances of {@link IView} given the presenter and event bus.
 * This factory is used primarily by the presenter factory, but custom MVP
 * implementations can also leverage it.
 * 
 * @author Sandile
 * 
 */
public class ViewFactory implements Serializable {
	private static final long serialVersionUID = 1746553215095831890L;

	/**
	 * Creates a new instance of the provided view class. The provided event bus
	 * and presenter are injected into the newly created view (if possible).
	 * 
	 * @param eventBus
	 *            the event bus of the provided presenter
	 * @param presenter
	 *            the presenter that this view is being created for
	 * @param viewClass
	 *            the type of view to be created
	 * @return a new instance of the provided viewClass
	 */
	public IView create(IEventBus eventBus, Presenter<? extends IView> presenter, Class<? extends IView> viewClass) {
		if (eventBus == null) {
			throw new IllegalArgumentException("The eventBus paramter must not be null.");
		}
		if (presenter == null) {
			throw new IllegalArgumentException("The presenter paramter must not be null.");
		}
		if (viewClass == null) {
			throw new IllegalArgumentException("The viewType paramter must not be null.");
		}

		IView viewInstance = null;
		try {
			viewInstance = (IView) viewClass.newInstance();
		} catch (InstantiationException e) {
			throw new ViewConstructionException("Views must have a default constructor and cannot be abstract.", e);
		} catch (IllegalAccessException e) {
			throw new InaccessibleViewException();
		} catch (ClassCastException e) {
			throw new ViewConstructionException("The provided view of type " + viewClass.getName() + " could not be cast to " + IView.class.getName() + ".", e);
		}

		if (viewInstance instanceof View) {
			((View) viewInstance).setEventBus(eventBus);
			((View) viewInstance).setPresenter(presenter);
		}

		return viewInstance;
	}
}
