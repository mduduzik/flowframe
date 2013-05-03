package org.flowframe.erp.integration.adaptors.stripe.webhooks.service;

import java.util.Set;

import org.flowframe.erp.integration.adaptors.stripe.domain.event.Event;



public interface IRemoteStripeWebHookService {
	/**
	 * Incoming from Stripe
	 */
	public int processEvent(String  eventInJson);
	
	
	/**
	 * CRUD
	 */
	public Set<Event> getAllActiveEvents() throws Exception;

	public Event getEventById(String eventId) throws Exception;

	public Event add(Event record) throws Exception;

	public void delete(Event record) throws Exception;

	public Event update(Event record) throws Exception;
	
	public Event provide(Event record) throws Exception;
	
	public void greenLightEvent(String  eventId) throws Exception;	
}
