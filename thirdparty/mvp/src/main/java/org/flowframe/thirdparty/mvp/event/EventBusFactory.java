package org.flowframe.thirdparty.mvp.event;

import java.io.Serializable;

/**
 * Creates new instances of {@link IEventBus}. However, event busses are
 * intended to exist in a one-eventbus-per-session scheme. This is because
 * native event busses can only propagate events within themselves. This does
 * not mean that customized implementations of {@link IEventBus} will suffer
 * this same limitation.
 * 
 * @author Sandile
 */
public class EventBusFactory implements Serializable {
	private static final long serialVersionUID = -6335972206494100881L;

	/**
	 * Creates an implementation of {@link IEventBus} and returns it.
	 * @return implementation of {@link IEventBus}
	 */
	public IEventBus create() {
		return new EventBus();
	}

}
