package org.phakama.vaadin.mvp.view;

import org.phakama.vaadin.mvp.annotation.event.EventListener;
import org.phakama.vaadin.mvp.annotation.field.EventBus;
import org.phakama.vaadin.mvp.event.EventScope;
import org.phakama.vaadin.mvp.event.IEvent;
import org.phakama.vaadin.mvp.event.IEventBus;
import org.phakama.vaadin.mvp.event.IEventDispatcher;
import org.phakama.vaadin.mvp.presenter.IPresenter;
import org.phakama.vaadin.mvp.presenter.IPresenterRegistry;

import com.vaadin.ui.Component;

/**
 * In the Model-View-Presenter design pattern, the view is a passive interface
 * that displays data, the model, and routes user commands, in the for of
 * events, to the presenter to act upon that data. {@link IView} is the
 * representation of the view in this relationship. Views can dispatch events
 * implementing the {@link IEvent} interface. Instances of {@link IView} possess
 * ability to show data provided by their parent {@link IPresenter}. Views
 * communicate with presenters through the interface methods included in the
 * {@link IView} definition. UI events that necessitate the change of data maybe
 * dispatched directly to the parent {@link IPresenter} with the
 * {@link EventScope#PARENT} scope. In practice, the {@link IView} interface is
 * <b>intended for extension</b>, and should not be implemented naked.
 * Implementations of the {@link IView} interface <i>must implement a default
 * constructor</i>.<br>
 * </br>In custom implementations of {@link IView} types, for the
 * {@link IViewFactory} to wire in the {@link IEventBus}, a field with the
 * {@link EventBus} annotation must exist. For example,
 * 
 * <pre>
 * <code>{@literal @}EventBus
 * private IEventBus eventBus;</code>
 * </pre>
 * 
 * This field is not required, but without it, implementation of event
 * dispatching becomes difficult. Also, in custom implementations, it is very
 * important that the {@link IView#onUnbind()} method is implemented correctly -
 * this is essential to the memory deallocation progress initiated by the
 * {@link IPresenterRegistry#unregister(IPresenter)} method.</br>
 * 
 * @author Sandile
 * 
 */
public interface IView extends IEventDispatcher {
	/**
	 * Gets the Vaadin representation of this view. This method ensures the
	 * decoupling of this {@link IView} from the Vaadin UI infrastructure. The
	 * relationship between a view and its {@link Component} should be
	 * <b>one-to-one</b>, such that this method <i>always</i> returns the same
	 * instance of its {@link Component}.
	 * 
	 * @return the Vaadin representation of this {@link IView}
	 */
	Component getComponent();

	/**
	 * This method is invoked when this {@link IView} is bound to its presenter.
	 * The <code>onBind</code> method may be used for initialization and setup
	 * purposes. Otherwise, the default constructor should be used. The
	 * {@link IView#getComponent()} method will not be invoked prior to
	 * {@link IView#onBind()} method.
	 */
	void onBind();

	/**
	 * This method is invoked when {@link IView} is unbound from its presenter.
	 * This either means that this view's {@link IPresenter} is switching its
	 * view instance, or this view's {@link IPresenter} being destroyed for one
	 * reason or another. In any case, this method should be used to destroy
	 * this view; this includes, but is not limited to, deallocating instance
	 * variables and destroying this view's Vaadin representation (whatever is
	 * returned by the {@link IView#getComponent()} method).
	 */
	void onUnbind();

	/**
	 * This method dispatches the event provided as the <code>event</code>
	 * parameter to this view's {@link IPresenter}. The provided event may be
	 * handled by this view's presenter with a publicly accessible method
	 * correctly annotated by the {@link EventListener} annotation.
	 * 
	 * @param event
	 *            the event to dispatch to this view's parent presenter
	 */
	int dispatch(IEvent event);
}
