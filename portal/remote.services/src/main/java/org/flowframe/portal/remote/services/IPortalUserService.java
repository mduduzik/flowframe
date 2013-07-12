package org.flowframe.portal.remote.services;

import java.util.List;
import java.util.Set;

import org.flowframe.kernel.common.mdm.domain.user.User;

public interface IPortalUserService {
	public List<User> getUsersByDefaultCompany() throws Exception;
	
	public List<User> getUsersByCompanyId(String companyId) throws Exception;
	
	public Set<User> getUsersByOrganizationId(String organizationId) throws Exception;
	
	public User provideUserByScreenName(String portalOrganizationId, String screenName) throws Exception;
	
	public User provideUserByEmailAddress(String portalOrganizationId, String emailAddress) throws Exception;
	
	public User provideUserByEmailAddress(String firstName, String lastName, String emailAddress) throws Exception;
	
	public User provideUserByEmailAddress(String firstName, String lastName, String emailAddress,String portalOrganizationId) throws Exception;

	public User getUserByEmailAddress(String emailAddress) throws Exception;
	
	public User provideUserByScreenName(String screenName) throws Exception;	
	
	public void syncUsersFromPortal() throws Exception;
	
	public Boolean isAvailable() throws Exception;

	public int getUserCountByCompanyId(String companyId) throws Exception;
	
	public String generateUnencryptedTemporaryPassword(String userEmailAddress) throws Exception;
	
	public User updatePassword(Long userId, String password, String password2, Boolean passwordReset) throws Exception;
}
