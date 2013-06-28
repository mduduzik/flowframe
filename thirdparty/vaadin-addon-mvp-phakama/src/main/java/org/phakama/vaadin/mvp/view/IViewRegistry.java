package org.phakama.vaadin.mvp.view;

import java.util.Collection;

import org.phakama.vaadin.mvp.ILogger;

/**
 * The {@link IViewRegistry} is responsible for keeping track of implementations
 * for specific {@link IView} types. View implementations may be registered,
 * unregistered and searched for by {@link IView} type.
 * 
 * @author Sandile
 * 
 */
public interface IViewRegistry extends ILogger {
	/**
	 * Registers the class of an implementation of a view extending the
	 * {@link IView} interface. The {@link IView} this view instance type is
	 * implementing must be <i>an extension of {@linkplain IView}</i> -
	 * <b>not</b> {@link IView} itself.
	 * 
	 * @param viewImplClass
	 *            the class of the view implementation we're registering
	 */
	void register(Class<? extends IView> viewImplClass);

	/**
	 * Unregisters the class of an implementation of a view extending the
	 * {@link IView} interface. If this view implementation type has not been
	 * registered with the {@link IViewRegistry#register(Class)} method, this
	 * method will do nothing.
	 * 
	 * @param viewImplClass
	 *            the class of the view implementation we're unregistering
	 */
	void unregister(Class<? extends IView> viewImplClass);

	/**
	 * Unregisters every view implementation class in the registry.
	 */
	void clear();

	/**
	 * Gets the number of {@link IView} types currently with implementations in
	 * this registry.
	 * 
	 * @return the number of view types currently registered in this registry
	 */
	int size();

	/**
	 * Finds a view implementation for the provided {@link IView} type. If there
	 * are more than one view implementations registered for this {@link IView}
	 * type, the most recently registered one view implementation will be
	 * returned.
	 * 
	 * @param viewClass
	 *            the {@link IView} type of the implementation to search for
	 * @return the most recently registered view implementation for the
	 *         {@link IView} type indicated by <code>viewClass</code>
	 */
	Class<? extends IView> find(Class<? extends IView> viewClass);

	/**
	 * Finds a view implementation for the provided {@link IView} type. If there
	 * are more than one view implementations registered for this {@link IView}
	 * type, the highest priority view implementation will be returned. The
	 * highest priority view type will by ascertained by the
	 * <code>viewPrioritizer</code> parameter.
	 * 
	 * @param viewClass
	 *            the {@link IView} type of the implementation to search for
	 * @param viewPrioritizer
	 *            the prioritizer, which can identify the most preferable of two
	 *            view implementation when provided
	 * @return the highest priority view implementation for the {@link IView}
	 *         type indicated by <code>viewClass</code>
	 */
	Class<? extends IView> find(Class<? extends IView> viewClass, IViewPrioritizer viewPrioritizer);

	/**
	 * Finds all view implementations for the provided {@link IView} type.
	 * 
	 * @param viewClass
	 *            the {@link IView} type of the implementation to search for
	 * @return all view implementations for the provided {@link IView} type
	 */
	Collection<Class<? extends IView>> findAll(Class<? extends IView> viewClass);
}
