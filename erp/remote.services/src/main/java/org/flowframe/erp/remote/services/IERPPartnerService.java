package org.flowframe.erp.remote.services;

import java.util.List;

import org.flowframe.kernel.common.mdm.domain.organization.Organization;

public interface IERPPartnerService {
	public Organization getOrganizationById(String orgId) throws Exception;
	
	public List<Organization> getAllOrganizations() throws Exception;
	
	public Boolean isAvailable() throws Exception;
}
