package org.vaadin.mvp.eventbus;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.vaadin.mvp.eventbus.annotation.Event;

public class AnonymousEventRegistry {
	private Map<String, List<Method>> eventMethods;
	private Map<Method, EventBusHandler> eventHandlers;

	public AnonymousEventRegistry() {
		this.eventMethods = new HashMap<String, List<Method>>();
		this.eventHandlers = new HashMap<Method, EventBusHandler>();
	}

	private List<Method> provide(String eventName) {
		List<Method> eventMethodList = this.eventMethods.get(eventName);
		if (eventMethodList == null) {
			eventMethodList = new LinkedList<Method>();
			this.eventMethods.put(eventName, eventMethodList);
		}
		return eventMethodList;
	}

	public void registerEvents(Class<?> eventBusClass, EventBusHandler handler) {
		Method[] methods = eventBusClass.getMethods();
		for (Method method : methods) {
			if (method.getAnnotation(Event.class) != null) {
				// This method is an Event
				List<Method> eventMethodList = provide(method.getName());
				if (!eventMethodList.contains(method)) {
					eventMethodList.add(method);
					this.eventHandlers.put(method, handler);
				}
			}
		}
	}

	public boolean fireAnonymousEvent(String eventName, Object[] args) {
		List<Method> eventMethodList = this.eventMethods.get(eventName);
		if (eventMethodList != null) {
			for (Method eventMethod : eventMethodList) {
				try {
					this.eventHandlers.get(eventMethod).invoke(null, eventMethod, args);
					return true;
				} catch (Exception e) {
					e.printStackTrace();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
}
