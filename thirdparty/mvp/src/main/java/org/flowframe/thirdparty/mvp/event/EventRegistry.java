package org.flowframe.thirdparty.mvp.event;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.flowframe.thirdparty.mvp.exception.EventListenerInvocationException;
import org.flowframe.thirdparty.mvp.presenter.Presenter;

class EventRegistry implements Serializable {
	private static final long serialVersionUID = -7950670068115942889L;
	
	private Map<Class<? extends Event>, Map<Object, Collection<EventRegistryEntry>>> entries;

	public EventRegistry() {
		this.entries = new HashMap<Class<? extends Event>, Map<Object, Collection<EventRegistryEntry>>>();
	}

	void register(Object handlerInstance, Method listenerMethod, Class<? extends Event> eventType) {
		if (handlerInstance == null) {
			throw new IllegalArgumentException("The handlerInstance parameter cannot be null.");
		}
		if (listenerMethod == null) {
			throw new IllegalArgumentException("The listenerMethod parameter cannot be null.");
		}
		if (eventType == null) {
			throw new IllegalArgumentException("The event parameter cannot be null.");
		}

		Map<Object, Collection<EventRegistryEntry>> entryMap = entries.get(eventType);
		if (entryMap == null) {
			entryMap = new HashMap<Object, Collection<EventRegistryEntry>>();
			HashSet<EventRegistryEntry> entrySet = new HashSet<EventRegistryEntry>();
			entrySet.add(new EventRegistryEntry(handlerInstance, listenerMethod, eventType));
			entryMap.put(handlerInstance, entrySet);
			entries.put(eventType, entryMap);
		} else {
			Collection<EventRegistryEntry> entryCollection = entryMap.get(handlerInstance);
			if (entryCollection == null) {
				entryCollection = new HashSet<EventRegistryEntry>();
				entryCollection.add(new EventRegistryEntry(handlerInstance, listenerMethod, eventType));
				entryMap.put(handlerInstance, entryCollection);
			} else {
				EventRegistryEntry entry = new EventRegistryEntry(handlerInstance, listenerMethod, eventType);
				if (!entryCollection.contains(entry)) {
					entryCollection.add(entry);
				}
			}
		}
	}

	void unregister(Object handlerInstance) {
		if (handlerInstance == null) {
			throw new IllegalArgumentException("The handlerInstance parameter cannot be null.");
		}

		Map<Object, Collection<EventRegistryEntry>> entryMap = null;
		for (Class<? extends Event> eventType : this.entries.keySet()) {
			if ((entryMap = this.entries.get(eventType)) != null) {
				entryMap.remove(handlerInstance);
			}
		}
	}

	void invokeAll(Event eventInstance) throws Throwable {
		if (eventInstance == null) {
			throw new IllegalArgumentException("The eventInstance parameter cannot be null.");
		}

		Collection<Map<Object, Collection<EventRegistryEntry>>> entryMaps = getEntryMaps(eventInstance.getClass());
		for (Map<Object, Collection<EventRegistryEntry>> entryMap : entryMaps) {
			if (entryMap != null) {
				Collection<EventRegistryEntry> entryCollection = null;
				for (Entry<Object, Collection<EventRegistryEntry>> entryMapEntry : entryMap.entrySet()) {
					entryCollection = entryMapEntry.getValue();
					if (entryCollection != null) {
						for (EventRegistryEntry entry : entryCollection) {
							try {
								invokeEntry(entry, eventInstance);
							} catch (IllegalArgumentException e) {
								// Arguments of the listener method were not
								// standard
								throw new EventListenerInvocationException("Listener method " + entry.getListenerMethod()
										+ " must have exactly one parameter (its event object) or zero parameters.");
							} catch (IllegalAccessException e) {
								throw new EventListenerInvocationException("Listener method " + entry.getListenerMethod()
										+ " could not be invoked because it was inaccesible.");
							} catch (InvocationTargetException e) {
								// There was an exception that occurred within
								// the
								// listener method
								throw e.getCause();
							}
						}
					}
				}
			}
		}
	}

	void invokeSiblings(Event eventInstance, Object parentInstance) throws Throwable {
		if (eventInstance == null) {
			throw new IllegalArgumentException("The eventInstance parameter cannot be null.");
		}
		if (parentInstance == null) {
			throw new IllegalArgumentException("The parentInstance parameter cannot be null.");
		}

		Collection<Map<Object, Collection<EventRegistryEntry>>> entryMaps = getEntryMaps(eventInstance.getClass());
		for (Map<Object, Collection<EventRegistryEntry>> entryMap : entryMaps) {
			if (entryMap != null) {
				Collection<EventRegistryEntry> entryCollection = null;
				for (Entry<Object, Collection<EventRegistryEntry>> entryMapEntry : entryMap.entrySet()) {
					entryCollection = entryMapEntry.getValue();
					if (entryCollection != null) {
						for (EventRegistryEntry entry : entryCollection) {
							if ((entry.getHandlerInstance() instanceof Presenter<?>) && (parentInstance.equals(((Presenter<?>) entry.getHandlerInstance()).getParent()))
									&& (!eventInstance.getSource().equals(entry.getHandlerInstance()))) {
								try {
									invokeEntry(entry, eventInstance);
								} catch (IllegalArgumentException e) {
									// Arguments of the listener method were not
									// standard
									throw new EventListenerInvocationException("Listener method " + entry.getListenerMethod()
											+ " must have exactly one parameter (its event object) or zero parameters.");
								} catch (IllegalAccessException e) {
									throw new EventListenerInvocationException("Listener method " + entry.getListenerMethod()
											+ " could not be invoked because it was inaccesible.");
								} catch (InvocationTargetException e) {
									// There was an exception that occurred
									// within
									// the
									// listener method
									throw e.getCause();
								}
							}
						}
					}
				}
			}
		}
	}
	
	void invokeChildren(Event eventInstance) throws Throwable {
		if (eventInstance == null) {
			throw new IllegalArgumentException("The eventInstance parameter cannot be null.");
		}
		if (eventInstance.getSource() == null) {
			throw new IllegalArgumentException("The eventInstance parameter's getSource() method cannot return null.");
		}

		Collection<Map<Object, Collection<EventRegistryEntry>>> entryMaps = getEntryMaps(eventInstance.getClass());
		for (Map<Object, Collection<EventRegistryEntry>> entryMap : entryMaps) {
			if (entryMap != null) {
				Collection<EventRegistryEntry> entryCollection = null;
				for (Entry<Object, Collection<EventRegistryEntry>> entryMapEntry : entryMap.entrySet()) {
					entryCollection = entryMapEntry.getValue();
					if (entryCollection != null) {
						for (EventRegistryEntry entry : entryCollection) {
							if ((entry.getHandlerInstance() instanceof Presenter<?>) && (eventInstance.getSource().equals(((Presenter<?>) entry.getHandlerInstance()).getParent()))) {
								try {
									invokeEntry(entry, eventInstance);
								} catch (IllegalArgumentException e) {
									// Arguments of the listener method were not
									// standard
									throw new EventListenerInvocationException("Listener method " + entry.getListenerMethod()
											+ " must have exactly one parameter (its event object) or zero parameters.");
								} catch (IllegalAccessException e) {
									throw new EventListenerInvocationException("Listener method " + entry.getListenerMethod()
											+ " could not be invoked because it was inaccesible.");
								} catch (InvocationTargetException e) {
									// There was an exception that occurred
									// within
									// the
									// listener method
									throw e.getCause();
								}
							}
						}
					}
				}
			}
		}
	}

	void invokeParent(Event eventInstance, Object parentInstance) throws Throwable {
		if (eventInstance == null) {
			throw new IllegalArgumentException("The eventInstance parameter cannot be null.");
		}
		if (parentInstance == null) {
			throw new IllegalArgumentException("The handlerInstance parameter cannot be null.");
		}

		Collection<Map<Object, Collection<EventRegistryEntry>>> entryMaps = getEntryMaps(eventInstance.getClass());
		for (Map<Object, Collection<EventRegistryEntry>> entryMap : entryMaps) {
			if (entryMap != null) {
				Collection<EventRegistryEntry> entryCollection = entryMap.get(parentInstance);
				if (entryCollection != null) {
					for (EventRegistryEntry entry : entryCollection) {
						try {
							invokeEntry(entry, eventInstance);
						} catch (IllegalArgumentException e) {
							// Arguments of the listener method were not
							// standard
							throw new EventListenerInvocationException("Listener method " + entry.getListenerMethod()
									+ " must have exactly one parameter (its event object) or zero parameters.");
						} catch (IllegalAccessException e) {
							throw new EventListenerInvocationException("Listener method " + entry.getListenerMethod() + " could not be invoked because it was inaccesible.");
						} catch (InvocationTargetException e) {
							// There was an exception that occurred within the
							// listener method
							throw e.getCause();
						}
					}
				}
			}
		}
	}

	private boolean invokeEntry(EventRegistryEntry entry, Event eventInstance) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if (entry == null) {
			return false;
		}
		if (entry.getListenerMethod() == null || entry.getEventType() == null || entry.getHandlerInstance() == null) {
			return false;
		}
		if (eventInstance == null) {
			return false;
		}
		if (!entry.getEventType().isAssignableFrom(eventInstance.getClass())) {
			return false;
		}

		if (entry.getListenerMethod().getParameterTypes().length == 1) {
			entry.getListenerMethod().invoke(entry.getHandlerInstance(), eventInstance);
			return true;
		} else if (entry.getListenerMethod().getParameterTypes().length == 0) {
			entry.getListenerMethod().invoke(entry.getHandlerInstance());
			return true;
		} else {
			throw new IllegalArgumentException("Listener method " + entry.getListenerMethod()
					+ " could not be registered because an event listener method can only have its own Event, or nothing, as a parameter.");
		}
	}

	private Collection<Map<Object, Collection<EventRegistryEntry>>> getEntryMaps(Class<? extends Event> eventType) {
		ArrayList<Map<Object, Collection<EventRegistryEntry>>> list = new ArrayList<Map<Object, Collection<EventRegistryEntry>>>();
		for (Class<? extends Event> key : this.entries.keySet()) {
			if (key.isAssignableFrom(eventType)) {
				Map<Object, Collection<EventRegistryEntry>> value = this.entries.get(key);
				list.add(value);
			}
		}
		return list;
	}
}
