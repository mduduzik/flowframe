package org.flowframe.portal.remote.services;

import java.util.List;

import org.flowframe.kernel.common.mdm.domain.role.Role;

public interface IPortalRoleService {
	public Role provideRole(String roleId, String roleName) throws Exception;	
	
	public List<Role> getUserRoles(String screenName) throws Exception;
	
	public Role addRole(String roleName) throws Exception;

	public boolean userHasRole(String userId, String roleName)  throws Exception;
	
	public boolean userHasRoleByScreenName(String userScreenName, String roleName)  throws Exception;
	
	public void setUserRole(String userId, String roleId)  throws Exception;

	public boolean userHasRoleByEmailAddress(String emailAddress, String roleName)
			throws Exception;
}
