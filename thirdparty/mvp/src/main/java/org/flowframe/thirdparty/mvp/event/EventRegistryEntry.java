package org.flowframe.thirdparty.mvp.event;

import java.io.Serializable;
import java.lang.reflect.Method;

class EventRegistryEntry implements Serializable {
	private static final long serialVersionUID = -6269452645213195412L;

	private static final int HASH_CODE_CONSTANT = 17;
	
	private Object handlerInstance;
	private Method listenerMethod;
	private Class<? extends Event> eventType;
	
	public EventRegistryEntry(Object handlerInstance, Method listenerMethod, Class<? extends Event> event) {
		this.handlerInstance = handlerInstance;
		this.listenerMethod = listenerMethod;
		this.eventType = event;
	}

	Method getListenerMethod() {
		return listenerMethod;
	}

	Object getHandlerInstance() {
		return handlerInstance;
	}
	
	Class<? extends Event> getEventType() {
		return eventType;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EventRegistryEntry)) {
			return false;
		} else {
			EventRegistryEntry other = (EventRegistryEntry) obj;
			if (other.getHandlerInstance() == getHandlerInstance()) {
				if (other.getListenerMethod().equals(this.listenerMethod)) {
					if (other.getEventType().equals(this.eventType)) {
						return true;
					}
				}
			}
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		int result = HASH_CODE_CONSTANT;
		if (handlerInstance != null && handlerInstance != null) {
			result += 31 * result + handlerInstance.hashCode();
		}
		if (listenerMethod != null) {
			result += 31 * result + listenerMethod.hashCode();
		}
		if (eventType != null) {
			result += 31 * result + eventType.hashCode();
		}
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName());
		builder.append(":{");
		if (handlerInstance != null && handlerInstance != null) {
			builder.append(handlerInstance.getClass().getSimpleName());
		} else {
			builder.append("<null instance>");
		}
		builder.append(", ");
		if (listenerMethod != null) {
			builder.append(listenerMethod.getName());
			builder.append("()");
		} else {
			builder.append("<null listener>");
		}
		builder.append(", ");
		if (eventType != null) {
			builder.append(eventType.getSimpleName());
		} else {
			builder.append("<null event type>");
		}
		builder.append("}");
		
		return builder.toString();
	}
}
