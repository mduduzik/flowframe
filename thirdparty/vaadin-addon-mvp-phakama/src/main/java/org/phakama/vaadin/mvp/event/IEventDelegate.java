package org.phakama.vaadin.mvp.event;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface IEventDelegate extends Serializable {
	Method getMethod();
	Class<? extends IEvent> getEventType();
	IEventHandler getHandler();
	boolean allowsForeign();
	
	void suicide();
	void invoke(IEvent event) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException;
}
