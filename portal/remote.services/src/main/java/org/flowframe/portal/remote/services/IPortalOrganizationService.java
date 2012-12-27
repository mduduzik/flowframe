package org.flowframe.portal.remote.services;

import java.util.Set;

import org.flowframe.kernel.common.mdm.domain.organization.Organization;

public interface IPortalOrganizationService {
	public Set<Organization> getOrganizationsByCompanyId(String companyId);
	
	public Organization provideOrganization(String portalOrganizationId);	
}
