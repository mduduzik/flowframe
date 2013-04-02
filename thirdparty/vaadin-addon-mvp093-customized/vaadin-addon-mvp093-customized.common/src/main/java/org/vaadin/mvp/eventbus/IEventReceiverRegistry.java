package org.vaadin.mvp.eventbus;

import java.util.Set;

/**
 * Classes that implements the event receiver registry interface will handle the registration and the lookup
 * of event receivers
 *
 * @author: apalumbo
 */
public interface IEventReceiverRegistry {
  /**
   * Add a receiver to the registry.
   *
   * @param receiver Instance of the event receiver.
   */
  void addReceiver(Object receiver);

  /**
   * Lookup a receiver by its type.
   *
   * @param <T>
   *          Receiver type
   * @param receiverType
   *          Receiver type class
   * @return the receiver instance if present in the registry or
   *         <code>null</code>
   */
  <T> T lookupReceiver(Class<T> receiverType);
  
  
  /**
   * Get all receivers
   *
   * @return the receiver instance if present in the registry or
   *         <code>null</code>
   */
  <T> Set<T> allReceivers(); 
  
  /**
   * Get all receiver types
   *
   * @return the receiver types if present in the registry or
   *         <code>null</code>
   */
  Set<Class<?>> allReceiverTypes();   
}
