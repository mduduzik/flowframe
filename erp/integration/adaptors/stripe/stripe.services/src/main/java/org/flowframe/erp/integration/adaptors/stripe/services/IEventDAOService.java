package org.flowframe.erp.integration.adaptors.stripe.services;

import java.util.List;

import org.flowframe.erp.integration.adaptors.stripe.domain.event.Event;


public interface IEventDAOService {
	
	public Event get(long id);
	
	public List<Event> getAll();
	
	public Event getByCode(String code);	

	public Event add(Event record);

	public void delete(Event record);

	public Event update(Event record);
	
	public Event provide(Event record) throws Exception;

	public List<Event> getAllActiveEvents();

	public Event getEventById(String eventId);
}
