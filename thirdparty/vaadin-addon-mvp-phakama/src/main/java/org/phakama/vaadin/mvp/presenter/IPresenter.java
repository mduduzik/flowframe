package org.phakama.vaadin.mvp.presenter;

import org.phakama.vaadin.mvp.annotation.field.EventBus;
import org.phakama.vaadin.mvp.annotation.field.Factory;
import org.phakama.vaadin.mvp.event.IEvent;
import org.phakama.vaadin.mvp.event.IEventBus;
import org.phakama.vaadin.mvp.event.IEventHandler;
import org.phakama.vaadin.mvp.event.IEventHandlerRegistry;
import org.phakama.vaadin.mvp.event.IScopedEventDispatcher;
import org.phakama.vaadin.mvp.view.IView;

/**
 * In the Model-View-Presenter design pattern, the presenter acts upon the model
 * and the view. It retrieves data from repositories and other data sources, the
 * model, and formats it for display in the view. The {@link IPresenter} is the
 * representation of the presenter in this relationship. Presenters can dispatch
 * events implementing the {@link IEvent} interface. Presenters correlate in a
 * parent-child structure. The result is a tree-like relationship structure,
 * which can be leveraged when dispatching events. For instance, presenters can
 * dispatch events to their parent, children or siblings. Implementations of the
 * {@link IPresenter} interface <i>must implement a default constructor</i>.
 * However, for initialization, the {@link IPresenter#onReady()} method should
 * be implemented or overridden.<br>
 * 
 * </br>In custom implementations of {@link IPresenter} types, for the
 * {@link IPresenterFactory} to wire in the {@link IEventBus} and
 * {@link IPresenterFactory}, two respective fields with the {@link EventBus}
 * and {@link Factory} annotations must exist. For example,
 * 
 * <pre>
 * <code>{@literal @}EventBus
 * private IEventBus eventBus;</code>
 * <code>{@literal @}Factory
 * private IPresenterFactory factory;</code>
 * </pre>
 * 
 * These fields are not required, but without them, implementation of event
 * dispatching becomes difficult. Also, in custom implementations, it is very
 * important that the {@link IPresenter#onUnbind()} method is implemented
 * correctly - this is essential to the memory deallocation progress initiated
 * by the {@link IPresenterRegistry#unregister(IPresenter)} method.</br>
 * 
 * @author Sandile
 * 
 * @param <T>
 *            the view type used by this presenter; it must extends
 *            {@link IView}
 */
public interface IPresenter<T extends IView> extends IEventHandler, IScopedEventDispatcher {
	/**
	 * Gets the view instance of this presenter. The view instance will always
	 * implement the {@link IView} type specified in the the generic of the
	 * presenter.
	 * 
	 * @return the view instance of this presenter
	 */
	T getView();

	/**
	 * Creates a new instance of an {@link IPresenter} which implements
	 * <code>presenterClass</code>. Child presenters may be of any presenter
	 * type. Furthermore, child presenters may dispatch events to their parent
	 * exclusively. This method ordinarily uses the {@link IPresenterFactory} to
	 * create the new child presenter instance.
	 * 
	 * @param presenterClass
	 *            the type of the child presenter to be created
	 * @return a new instance of a child presenter
	 */
	<E extends IPresenter<? extends IView>> E createChild(Class<E> presenterClass);

	/**
	 * This method is invoked when this {@link IPresenter} is bound to a view.
	 * Since presenters may be rebound to different views, this method is not
	 * ideal for initialization. The {@link IPresenter#onReady()} method should
	 * be used for general initialization.
	 * 
	 * @param view
	 *            the view to which this presenter is being bound
	 */
	void onBind(T view);

	/**
	 * This method is invoked when this {@link IPresenter} has been registered
	 * with the {@link IPresenterRegistry}, the {@link IEventHandlerRegistry}
	 * and has been bound to its {@link IView}. This method is ideal for
	 * initialization, as it is called exactly once by the
	 * {@link IPresenterFactory} which created this presenter.
	 */
	void onReady();

	/**
	 * This method is invoked when this presenter is removed from its
	 * {@link IPresenterRegistry}. Its primary purpose is to destroy the
	 * presenter instance on which it is invoked, as it is no longer in use.
	 * Destroying a presenter should result in its view also getting unbound. In
	 * custom implementations of {@link IPresenter}, it is <iv>strongly
	 * recommended</i> that this method is implemented, and considered
	 * judiciously as it has performance-altering consequences.
	 */
	void onUnbind();
}
