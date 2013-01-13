package org.flowframe.portal.remote.services;

import java.util.List;
import java.util.Set;

import org.flowframe.kernel.common.mdm.domain.organization.Organization;

public interface IPortalOrganizationService {
	public Set<Organization> getOrganizationsByCompanyId(String companyId);
	
	public List<Organization> getOrganizationsByUserId(String userId) throws Exception;	
	
	public Organization getUserDefaultOrganization(String userId) throws Exception;	
	
	public Organization provideOrganization(String portalOrganizationId);	
}
