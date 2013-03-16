package org.vaadin.mvp.eventbus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Event handler registry holding <i>active</i> subscribers (i.e. the actual
 * event handlers) as weak references to allow collection in case no other
 * references are held by the application.
 * 
 * @author Sandile
 */
public class CustomizedEventReceiverRegistry extends EventReceiverRegistry {
	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(EventReceiverRegistry.class);

	/**
	 * Event receiver registry using WeakReference to the receiver instances to
	 * allow garbage collection of them. Note that a normal HashMap is used
	 * since a WeakHashMap does only collect non-referenced keys - which are
	 * class objects that will not be collected in our case.
	 */
	private Map<Class<?>, Set<Object>> receivers = new HashMap<Class<?>, Set<Object>>();

	/**
	 * Lookup all receivers of a given type.
	 * 
	 * @param <T>
	 * 
	 * @param <T>
	 *            Receiver type
	 * @param receiverType
	 *            Receiver type class
	 * @return the receiver instances if present in the registry or
	 *         <code>null</code>
	 */
	@SuppressWarnings("unchecked")
	public <T> Set<T> lookupReceivers(Class<T> receiverType) {
		if (receivers.containsKey(receiverType)) {
			// Is a tree set
			Set<Object> referenceSet = receivers.get(receiverType);
			if (referenceSet == null) {
				return null;
			} else {
				Set<T> resultSet = new HashSet<T>();
				for (Object reference : referenceSet) {
					if (reference != null && reference.getClass().isAssignableFrom(receiverType)) {
						resultSet.add((T) reference);
					}
				}
				return resultSet;
			}
		}
		return null;
	}
	
	@Override
	public void newReceiver(Object receiver) {
		Set<Object> referenceSet = receivers.get(receiver.getClass());
		if (referenceSet == null) {
			if (receiver != null) {
				referenceSet = new TreeSet<Object>();
				referenceSet.add(receiver);
				receivers.put(receiver.getClass(), referenceSet);
			}
		} else {
			if (!referenceSet.contains(receiver)) {
				referenceSet.add(receiver);
			}
		}
	}
}
