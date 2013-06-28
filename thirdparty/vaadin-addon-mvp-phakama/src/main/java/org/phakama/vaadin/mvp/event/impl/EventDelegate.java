package org.phakama.vaadin.mvp.event.impl;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;

import org.phakama.vaadin.mvp.event.IEvent;
import org.phakama.vaadin.mvp.event.IEventDelegate;
import org.phakama.vaadin.mvp.event.IEventHandler;

public class EventDelegate implements IEventDelegate {
	private static final long serialVersionUID = 3831425706540938586L;

	private final Method method;
	private final WeakReference<IEventHandler> handler;
	private final Class<? extends IEvent> eventType;
	private final boolean allowForeign;
	private final HashSet<Collection<IEventDelegate>> owners = new HashSet<Collection<IEventDelegate>>();

	public EventDelegate(Method method, IEventHandler handler, Class<? extends IEvent> eventType, boolean allowForeign) {
		if (method == null) {
			throw new IllegalArgumentException("The method parameter cannot be null when creating a " + getClass().getSimpleName() + ".");
		}
		if (handler == null) {
			throw new IllegalArgumentException("The eventBus parameter cannot be null when creating a " + getClass().getSimpleName() + ".");
		}
		if (eventType == null) {
			throw new IllegalArgumentException("The eventType parameter cannot be null when creating a " + getClass().getSimpleName() + ".");
		}

		this.method = method;
		this.handler = new WeakReference<IEventHandler>(handler);
		this.eventType = eventType;
		this.allowForeign = allowForeign;
	}

	@Override
	public Method getMethod() {
		return this.method;
	}

	@Override
	public IEventHandler getHandler() {
		return this.handler.get();
	}

	@Override
	public Class<? extends IEvent> getEventType() {
		return this.eventType;
	}

	public void addOwner(Collection<IEventDelegate> owner) {
		if (!this.owners.contains(owner)) {
			this.owners.add(owner);
		}
	}

	public void removeOwner(Collection<IEventDelegate> owner) {
		this.owners.remove(owner);
	}

	@Override
	public void suicide() {
		for (Collection<IEventDelegate> owner : this.owners) {
			owner.remove(this);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName());
		builder.append(':');
		builder.append('{');
		if (method == null) {
			builder.append("<null method>, ");
		} else {
			builder.append(method.getName());
			builder.append('(');
			builder.append(')');
			builder.append(", ");
		}
		if (handler.get() == null) {
			builder.append("<null handler>, ");
		} else {
			builder.append(handler.get().getClass().getSimpleName());
			builder.append(", ");
		}
		if (eventType == null) {
			builder.append("<null event type>");
		} else {
			builder.append(eventType.getSimpleName());
		}
		builder.append('}');

		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof EventDelegate))
			return false;
		EventDelegate otherDelegate = (EventDelegate) obj;
		if (this.method == null) {
			if (otherDelegate.getMethod() != null)
				return false;
		} else {
			if (!this.method.equals(otherDelegate.getMethod()))
				return false;
		}
		if (this.handler == null) {
			if (otherDelegate.getHandler() != null)
				return false;
		} else {
			if (!this.handler.equals(otherDelegate.getHandler()))
				return false;
		}
		if (this.eventType == null) {
			if (otherDelegate.getEventType() != null)
				return false;
		} else {
			if (!this.eventType.equals(otherDelegate.getEventType()))
				return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = 91;
		result = result * 31;
		if (this.method != null) {
			result += this.method.hashCode();
		}
		result = result * 31;
		if (this.handler.get() != null) {
			result += this.handler.get().hashCode();
		}
		result = result * 31;
		if (this.method != null) {
			result += this.eventType.hashCode();
		}
		return result;
	}

	@Override
	public void invoke(IEvent event) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		IEventHandler handlerInstance = this.handler.get();
		if (handlerInstance == null) {
			// If the handler dies, so does its delegate
			suicide();
			// The invoke() didn't technically fail - so, lets forget this ever
			// happened
			return;
		}
		// If this delegate won't take foreign, then don't give it any
		if (!this.allowForeign && event.isForeign()) {
			return;
		} else {
			if (this.method.getParameterTypes().length == 0) {
				this.method.invoke(handlerInstance);
			} else if (this.method.getParameterTypes().length == 1) {
				if (event == null) {
					throw new IllegalArgumentException("The method parameter cannot be null.");
				} else if (!eventType.isAssignableFrom(event.getClass())) {
					throw new IllegalArgumentException("The event parameter did not match the event type of this delegate.");
				} else {
					this.method.invoke(handlerInstance, event);
				}
			} else {
				throw new IllegalArgumentException(
						"The event handler method "
								+ method.getName()
								+ "(..) had more than one argument. Event handler methods must specify either no arguments, or one argument which shares the event of its @EventListener annotation.");
			}
		}
	}

	@Override
	public boolean allowsForeign() {
		return allowForeign;
	}
}
