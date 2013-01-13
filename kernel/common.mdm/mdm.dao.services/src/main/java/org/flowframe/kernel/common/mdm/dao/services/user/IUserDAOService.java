package org.flowframe.kernel.common.mdm.dao.services.user;

import java.util.List;

import org.flowframe.kernel.common.mdm.domain.user.User;


public interface IUserDAOService {
	public User get(long id);
	
	public List<User> getAll();
	
	public User getByCode(String code);	

	public User add(User record);

	public void delete(User record);

	public User update(User record);
	
	public User provide(User record);
	
	public User provide(String code, String name);
	
	public void provideDefaults();

	public User getByEmailOrScreenname(String email, String screenName);

	public User getByEmailAndScreenname(String email, String screenName);
}
