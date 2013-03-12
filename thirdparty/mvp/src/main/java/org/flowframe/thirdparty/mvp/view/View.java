package org.flowframe.thirdparty.mvp.view;

import org.flowframe.thirdparty.mvp.event.EventScope;
import org.flowframe.thirdparty.mvp.event.IEventBus;
import org.flowframe.thirdparty.mvp.presenter.Presenter;

import com.vaadin.ui.VerticalLayout;

/**
 * The default implementation of the {@link IView} interface. While this
 * implementation is the one supported by default, and other implementation of
 * the <code>IView</code> will suffice for use in a {@link Presenter}.
 * 
 * @author Sandile
 * 
 */
public abstract class View extends VerticalLayout implements IView {
	private static final long serialVersionUID = 5552315123806395809L;

	private IEventBus eventBus;
	private Presenter<? extends IView> presenter;

	public View() {
		setSizeFull();
	}

	void setPresenter(Presenter<? extends IView> presenter) {
		if (this.presenter == null) {
			this.presenter = presenter;
		}
	}

	void setEventBus(IEventBus eventBus) {
		if (this.eventBus == null) {
			this.eventBus = eventBus;
		}
	}

	/**
	 * Propagates the event e to every listener method listening for it that is
	 * also registered in this view's event bus. The event is
	 * propagated according to the <code>getScope()</code> method of the event
	 * which returns an {@link EventScope}.
	 * 
	 * @param e
	 *            the event to be fired
	 */
	protected void fire(org.flowframe.thirdparty.mvp.event.Event e) {
		this.eventBus.fire(this, e);
	}

	/**
	 * Propagates the event e to every listener method listening for it that is
	 * also registered in this view's event bus. The event is
	 * propagated according to the scope parameter <b>regardless</b> of the
	 * scope of the actual event. The scope parameter will <b>override the fired
	 * event's current scope</b>.
	 * 
	 * @param e
	 *            the event to be fired
	 * @param scope
	 *            the scope to fire this event with
	 */
	protected void fire(org.flowframe.thirdparty.mvp.event.Event e, EventScope scope) {
		this.eventBus.fire(this, scope, e);
	}

	@Override
	public Object getOwner() {
		return presenter;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName());
		builder.append(':');
		builder.append('{');
		if (presenter == null) {
			builder.append("<null presenter>, ");
		} else {
			builder.append(presenter.getClass().getName());
			builder.append(", ");
		}
		if (eventBus == null) {
			builder.append("<null event bus>");
		} else {
			builder.append(eventBus.getClass().getName());
		}
		builder.append('}');

		return builder.toString();
	}
}
