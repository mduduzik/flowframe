package org.flowframe.scheduling.jobs.manager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.flowframe.kernel.common.utils.Validator;
import org.flowframe.kernel.services.ISuperService;
import org.flowframe.scheduling.remote.services.IJobsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class JobsManagerImpl implements IJobsManager {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Map<String, ISuperService> serviceCache = Collections
			.synchronizedMap(new HashMap<String, ISuperService>());
	
	public void unregisterSuperService(
			ISuperService service, Map<String, Object> properties) {
		logger.info("unregisterSuperService called with  "+service);
		if (Validator.isNotNull(service))
		{
			logger.info("Unregistering "+service.getClass().getName());
			serviceCache.remove(service.getClass().getName());
		}
	}
	
	public void registerSuperService(
			ISuperService service, Map<String, Object> properties) {
		logger.info("registerSuperService called with  "+service);
		logger.info("Registering "+service.getClass().getName());		
		serviceCache.put(service.getClass().getName(),service);
	}

	@Override
	public Object getServiceByInterfaceClassName(String interfaceClassName) {
		// TODO Auto-generated method stub
		return serviceCache.get(interfaceClassName);
	}	
}