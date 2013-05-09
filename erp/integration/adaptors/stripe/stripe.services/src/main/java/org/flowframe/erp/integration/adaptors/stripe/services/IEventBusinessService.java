package org.flowframe.erp.integration.adaptors.stripe.services;

public interface IEventBusinessService {
	public int processEvent(String  eventInJson);
	
	public void greenLightEvent(String  eventId) throws Exception;	
	
	public void markInactive(String  eventId) throws Exception;

	public void markActive(String eventId) throws Exception; 
}
