package org.flowframe.erp.integration.adaptors.remote.services.payments;


public interface IRemotePaymentProcessorWebHookService {
	public int processEvent(String  eventInJson);
}
