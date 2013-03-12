package org.flowframe.thirdparty.mvp.presenter;

import java.io.Serializable;

import org.flowframe.thirdparty.mvp.event.EventScope;
import org.flowframe.thirdparty.mvp.event.IEventBus;
import org.flowframe.thirdparty.mvp.event.annotation.Listener;
import org.flowframe.thirdparty.mvp.exception.IncompatibleViewException;
import org.flowframe.thirdparty.mvp.view.IView;

/**
 * Presenters are responsible for applying data to their corresponding views
 * (what the user is able to see). Presenters are created with the Presenters
 * often know what data to paint by receiving and handling events. Events are
 * handled by methods in the presenter with the {@link Listener} annotation. All
 * presenters have a view which implements the {@link IView} interface.
 * Presenters can create other presenters called Child Presenters. A child
 * presenter has the ability to propagate events directly to its parent
 * presenter.
 * 
 * @author Sandile
 * 
 * @param <T>
 *            the view type of this presenter
 */
public abstract class Presenter<T extends IView> implements Serializable {
	private static final long serialVersionUID = 5131211825391491296L;

	private Presenter<? extends IView> parent;
	private T view;
	private IEventBus eventBus;
	private PresenterFactory factory;

	/**
	 * Gets this presenters view. The type of this view is specified by the type
	 * of the generic for this class.
	 * 
	 * @return this presenter's view
	 */
	public T getView() {
		return view;
	}

	@SuppressWarnings("unchecked")
	void setView(IView view) {
		try {
			this.view = (T) view;
		} catch (ClassCastException e) {
			throw new IncompatibleViewException(view, this);
		}
	}

	void setPresenterFactory(PresenterFactory factory) {
		if (this.factory == null) {
			this.factory = factory;
		}
	}

	/**
	 * Propagates the event e to every listener method listening for it that is
	 * also registered in this presenter's event bus. The event is propagated
	 * according to the <code>getScope()</code> method of the event which
	 * returns an {@link EventScope}.
	 * 
	 * @param e
	 *            the event to be fired
	 */
	protected void fire(org.flowframe.thirdparty.mvp.event.Event e) {
		this.eventBus.fire(this, e);
	}

	/**
	 * Propagates the event e to every listener method listening for it that is
	 * also registered in this presenter's event bus. The event is propagated
	 * according to the scope parameter <b>regardless</b> of the scope of the
	 * actual event. The scope parameter will <b>override the fired event's
	 * current scope</b>.
	 * 
	 * @param e
	 *            the event to be fired
	 * @param scope
	 *            the scope to fire this event with
	 */
	protected void fire(org.flowframe.thirdparty.mvp.event.Event e, EventScope scope) {
		this.eventBus.fire(this, scope, e);
	}

	void setEventBus(IEventBus eventBus) {
		this.eventBus = eventBus;
	}

	/**
	 * Gets the parent presenter for this presenter. Not every presenter has a
	 * parent - whether or not a presenter has a parent is subject to whether
	 * the <code>PresenterFactory</code> that created it gave it a parent. If
	 * this presenter has no parent, this method will return null.
	 * 
	 * @return the parent presenter or null if no such parent presenter exists
	 */
	public Presenter<? extends IView> getParent() {
		return this.parent;
	}

	void setParent(Presenter<? extends IView> parent) {
		this.parent = parent;
	}

	/**
	 * This method is called when this presenter is ready to be initialized. At
	 * this point, this presenter has a refeence to its event bus, parent and
	 * view. <b>However,</b> before this method is called, all the getters for
	 * the previously mentioned field will return <b>null values</b>. The
	 * <code>init()</code> method of any presenter is always invoked after its
	 * view's <code>init()</code> method.
	 */
	public abstract void init();

	/**
	 * This method simply returns the chosen implementation of the this
	 * presenter's view interface. It should not return null, and the view
	 * implementation must implement the view interface of this presenter (as
	 * specified in the generic of this class).
	 * 
	 * @return the class of the view implementation for this presenter
	 */
	public abstract Class<? extends T> view();

	/**
	 * Create a new presenter that will refer to this presenter as its parent.
	 * This means that the new child presenter will have the option to propagate
	 * events only to this presenter.
	 * 
	 * @param childPresenterClass
	 *            the class of the child presenter
	 * @return a new instance of the child presenter class
	 */
	protected Presenter<? extends IView> createChild(Class<? extends Presenter<? extends IView>> childPresenterClass) {
		return this.factory.create(childPresenterClass, this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName());
		builder.append(':');
		builder.append('{');
		if (view == null) {
			builder.append("<null view>, ");
		} else {
			builder.append(view.getClass().getSimpleName());
			builder.append(", ");
		}
		if (eventBus == null) {
			builder.append("<null event bus>, ");
		} else {
			builder.append(eventBus.getClass().getSimpleName());
			builder.append(", ");
		}
		if (parent == null) {
			builder.append("<null parent>");
		} else {
			builder.append(parent.getClass().getSimpleName());
		}
		builder.append('}');

		return builder.toString();
	}
}
