package org.flowframe.erp.integration.adaptors.stripe.services;

public interface IEventBusinessService {
	public int processEvent(String  eventInJson);
	
	public void greenLightEvent(String  eventId) throws Exception;	
}
