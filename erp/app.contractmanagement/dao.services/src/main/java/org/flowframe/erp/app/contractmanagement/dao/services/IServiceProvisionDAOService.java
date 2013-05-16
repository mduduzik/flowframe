package org.flowframe.erp.app.contractmanagement.dao.services;

import java.util.List;

import org.flowframe.erp.app.contractmanagement.domain.service.ServiceProvision;
import org.flowframe.erp.app.contractmanagement.domain.service.ServiceProvisionGroup;


public interface IServiceProvisionDAOService {
	
	public ServiceProvision get(long id);
	
	public List<ServiceProvision> getAll();
	
	public ServiceProvision getByCode(String code);	
	
	public ServiceProvision add(ServiceProvision record);

	public void delete(ServiceProvision record);

	public ServiceProvision update(ServiceProvision record);
	
	public ServiceProvision provide(ServiceProvision record);
	
	public ServiceProvisionGroup getGroup(long id);
	
	public List<ServiceProvisionGroup> getAllGroups();
	
	public ServiceProvisionGroup getGroupByCode(String code);	
	
	public ServiceProvisionGroup addGroup(ServiceProvisionGroup record);

	public void deleteGroup(ServiceProvisionGroup record);

	public ServiceProvisionGroup updateGroup(ServiceProvisionGroup record);
	
	public ServiceProvisionGroup provideGroup(ServiceProvisionGroup record);	
}
