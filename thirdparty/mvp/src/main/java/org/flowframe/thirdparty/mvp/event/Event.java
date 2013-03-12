package org.flowframe.thirdparty.mvp.event;

import java.io.Serializable;

import org.flowframe.thirdparty.mvp.event.annotation.Listener;
import org.flowframe.thirdparty.mvp.presenter.Presenter;

/**
 * This is the super type for all events. Events are fired with the
 * {@link IEventBus} <code>fire()</code> method. Events can be listened to by
 * with {@link Presenter} methods labeled with the {@link Listener} annotation.
 * 
 * @author Sandile
 */
public abstract class Event implements Serializable {
	private static final long serialVersionUID = -1668154897552260787L;
	private static final int HASH_CODE_CONSTANT = 29;

	private Object source;
	private EventScope scope = EventScope.ALL;

	/**
	 * The default constructor of the Event class. This constructor initializes
	 * the scope of the event to <code>EventScope.ALL</code>.
	 */
	public Event() {
	}

	/**
	 * Initializes the event with the scope parameter.
	 * 
	 * @param scope
	 *            propagation scope level of this event
	 */
	public Event(EventScope scope) {
		this.scope = scope;
	}

	void setSource(Object source) {
		this.source = source;
	}

	/**
	 * Gets the entity responsible for this event being fired.
	 * 
	 * @return the event originator
	 */
	protected Object getSource() {
		return source;
	}

	/**
	 * Changes the scope of this event. This change is only considered the next
	 * time that this event is triggered.
	 * 
	 * @param scope the new scope of this event
	 */
	protected void setScope(EventScope scope) {
		this.scope = scope;
	}

	/**
	 * Gets the scope of this event.
	 * 
	 * @return the scope of this event
	 */
	public EventScope getScope() {
		return scope;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!getClass().isAssignableFrom(obj.getClass())) {
			return false;
		}
		if (!((Event) obj).getScope().equals(this.scope)) {
			return false;
		}
		if (((Event) obj).getSource() == null) {
			if (this.source != null) {
				return false;
			} else {
				return true;
			}
		} else {
			return ((Event) obj).getSource().equals(this.source);
		}
	}

	@Override
	public int hashCode() {
		int result = HASH_CODE_CONSTANT;
		if (source != null) {
			result += 31 * result + source.hashCode();
		}
		if (scope != null) {
			result += 31 * result + scope.hashCode();
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName());
		builder.append(':');
		builder.append('{');
		if (source == null) {
			builder.append("<null source>, ");
		} else {
			builder.append(source.getClass().getName());
			builder.append(", ");
		}
		if (scope == null) {
			builder.append("<null scope>");
		} else {
			builder.append(scope);
		}
		builder.append('}');

		return builder.toString();
	}
}
