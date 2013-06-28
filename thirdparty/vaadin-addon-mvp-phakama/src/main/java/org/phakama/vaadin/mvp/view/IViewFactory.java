package org.phakama.vaadin.mvp.view;

import org.phakama.vaadin.mvp.ILogger;
import org.phakama.vaadin.mvp.exception.ViewConstructionException;

/**
 * The {@link IViewFactory} is responsible for creating instances of
 * implementations of views which extend {@link IView}. The view instances are
 * created with the {@link IViewFactory#create(Class)} method. View factories
 * use a {@link IViewRegistry} to keep track of view implementations. The
 * registry used by this factory is accessible with the
 * {@link IViewFactory#getRegistry()} method.
 * 
 * @author Sandile
 * 
 */
public interface IViewFactory extends ILogger {
	/**
	 * Creates and returns an instance of a view implementing the provided
	 * <code>viewClass</code> view type. The <code>viewClass</code> parameter
	 * must extend {@link IView}. The view implementation class will primarily
	 * be obtained by requesting it from the {@link IViewRegistry} with the
	 * {@link IViewRegistry#find(Class)} method. If the {@link IViewRegistry}
	 * has no record of an implementation, reflection is used to guess where
	 * view implementation could be <i>within the same package as the
	 * <code>viewClass</code> parameter</i>.<br>
	 * </br>For example, if your <code>viewClass</code> is called
	 * <code>com.example.ITestView</code>, this {@link IViewFactory} would look
	 * for for an implementation class with the name
	 * <code>com.example.TestView</code> or
	 * <code>com.example.TestViewImpl</code>. If the view cannot be constructed
	 * due to the lack of an implementing class, a
	 * {@link ViewConstructionException} will be thrown.
	 * 
	 * @throws ViewConstructionException
	 *             thrown is the <code>viewClass</code> parameter has no
	 *             recorded implementation
	 * @param viewClass
	 *            the {@link IView} type of the view implementation to be
	 *            instantiated
	 * @return a new instance of a view implementation of the
	 *         <code>viewClass</code> interface
	 */
	<T extends IView> T create(Class<T> viewClass);

	/**
	 * Gets the {@link IViewRegistry} used by this to factory get and register
	 * implementations of view types extending {@link IView}.
	 * 
	 * @return the {@link IViewRegistry} employed by this {@link IViewFactory}
	 */
	IViewRegistry getRegistry();
}
