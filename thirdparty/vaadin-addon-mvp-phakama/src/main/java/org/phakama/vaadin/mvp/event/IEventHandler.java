package org.phakama.vaadin.mvp.event;

import java.io.Serializable;

/**
 * An interface that indicates that the class implementing it is capable of
 * handling events of type {@link IEvent} propagated to it by an
 * {@link IEventBus}.
 * 
 * @author Sandile
 * 
 */
public interface IEventHandler extends Serializable {
}
