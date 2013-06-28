package org.phakama.vaadin.mvp.event.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.phakama.vaadin.mvp.event.IEvent;
import org.phakama.vaadin.mvp.event.IEventDispatcher;

public class Event implements IEvent {
	private static final long serialVersionUID = 3610879244521284114L;

	private IEventDispatcher source;
	private boolean foreign = false;

	@Override
	public IEventDispatcher getSource() {
		return this.source;
	}

	@Override
	public void setSource(IEventDispatcher source) {
		if (source != null) {
			this.source = source;
		}
	}

	@Override
	public boolean isForeign() {
		return foreign;
	}

	@Override
	public void markForeign() {
		this.foreign = true;
	}

	@Override
	public IEvent duplicate() {
		try {
			IEvent duplicate = getClass().newInstance();
			for (Class<?> currentClass = duplicate.getClass(); !currentClass.equals(Object.class); currentClass = currentClass.getSuperclass()) {
				Field[] fields = currentClass.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					if (fields[i] != null && !Modifier.isFinal(fields[i].getModifiers()) && !Modifier.isStatic(fields[i].getModifiers())) {
						// Only copy over fields that can be copied
						fields[i].setAccessible(true);
						fields[i].set(duplicate, fields[i].get(this));
					}
				}
			}
			// If we got this far, we doin' good
			return duplicate;
		} catch (Exception e) {
			// Catch all "there was a problem somewhere" exception
			throw new EventDuplicationException(e);
		}
	}

	private class EventDuplicationException extends RuntimeException {
		private static final long serialVersionUID = -9020152222677934642L;

		EventDuplicationException(Throwable cause) {
			initCause(cause);
		}

		@Override
		public String getMessage() {
			return "Could not duplicate this event.";
		}
	}
}
