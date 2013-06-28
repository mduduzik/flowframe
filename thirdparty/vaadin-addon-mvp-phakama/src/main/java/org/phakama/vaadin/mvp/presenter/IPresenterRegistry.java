package org.phakama.vaadin.mvp.presenter;

import java.util.Collection;

import org.phakama.vaadin.mvp.ILogger;
import org.phakama.vaadin.mvp.view.IView;

/**
 * The {@link IPresenterRegistry} is responsible for keeping track of
 * relationships between {@link IPresenter} instances. Presenter instances may
 * be registered, unregistered and queried for presenter relationships.
 * Presenter's are intended not to exist outside a registry. Also, a
 * {@link IPresenterRegistry} is responsible for remembering mappings between
 * {@link IView}s and their corresponding presenters.
 * 
 * @author Sandile
 * 
 */
public interface IPresenterRegistry extends ILogger {
	/**
	 * Adds a presenter to the registry, and indicates the presenter's parent
	 * initially.
	 * 
	 * @param presenter
	 *            the presenter to be registered
	 * @param parent
	 *            the presenter responsible for the <code>presenter</code>
	 *            parameter's existence
	 * @param view
	 *            the view instance created for the <code>presenter</code>
	 *            parameter
	 */
	void register(IPresenter<? extends IView> presenter, IPresenter<? extends IView> parent, IView view);

	/**
	 * Removes a presenter from the registry and simultaneously destroys the
	 * presenter instance and its corresponding view instance. The presenter is
	 * destroyed in the {@link IPresenter#onUnbind()} method.
	 * 
	 * @param presenter
	 *            the presenter to be unregistered
	 */
	void unregister(IPresenter<? extends IView> presenter);

	/**
	 * Gets the total number of presenter instances currently listed in the
	 * registry.
	 * 
	 * @return the number of registered presenter instances
	 */
	int size();

	/**
	 * Gets the presenter instance in ownership of the view instance indicated
	 * by the <code>view</code> parameter. If no such presenter exists, then
	 * <code>null</code> is returned.
	 * 
	 * @param view
	 *            the view to find a presenter for
	 * @return the <code>view</code>'s presenter, or <code>null</code>.
	 */
	IPresenter<? extends IView> find(IView view);

	/**
	 * Gets the parent presenter of the provided <code>presenter</code>
	 * instance. If no such parent exists, <code>null</code> is returned.
	 * 
	 * @param presenter
	 *            the presenter to find the parent for
	 * @return the parent presenter of the provided <code>presenter</code>, or
	 *         <code>null</code>.
	 */
	IPresenter<? extends IView> parentOf(IPresenter<? extends IView> presenter);

	/**
	 * Gets the sibling presenters of the provided <code>presenter</code>
	 * instance. Sibling presenters are presenters that share the same parent.
	 * If no such siblings exist, <code>null</code> is returned.
	 * 
	 * @param presenter
	 *            the presenter to find siblings of
	 * @return the sibling presenters of the provided <code>presenter</code>, or
	 *         <code>null</code>.
	 */
	Collection<IPresenter<? extends IView>> siblingsOf(IPresenter<? extends IView> presenter);

	/**
	 * Gets the child presenters of the provided <code>presenter</code>
	 * instance. If no such children exist, <code>null</code> is returned.
	 * 
	 * @param presenter
	 *            the presenter to find children of
	 * @return the child presenters of the provided <code>presenter</code>, or
	 *         <code>null</code>.
	 */
	Collection<IPresenter<? extends IView>> childrenOf(IPresenter<? extends IView> presenter);
}
