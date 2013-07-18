package org.flowframe.erp.app.contractmanagement.business.services;

import org.flowframe.erp.app.contractmanagement.domain.Subscription;
import org.flowframe.kernel.services.ISuperService;

public interface IContractManagementBusinessServices extends ISuperService {
	public Subscription changeSubscription(Long planId, String ppToken, String userEmaillAddress);
	public Subscription changeSubscription(String planName, String ppToken, String userEmaillAddress);
}
