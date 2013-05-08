package org.flowframe.kernel.common.mdm.dao.services;

import java.util.List;

import org.flowframe.kernel.common.mdm.domain.organization.Organization;


public interface IOrganizationDAOService {
	public Organization get(long id);
	
	public List<Organization> getAll();
	
	public Organization getByCode(String code);	
	
	public Organization getByExternalRefId(String externalRefId);
	
	public Organization getByName(String name);

	public Organization add(Organization record) throws Exception;

	public void delete(Organization record);

	public Organization update(Organization record);
	
	public Organization provide(Organization record) throws Exception;
	
	public Organization provideDefault() throws Exception;
}
