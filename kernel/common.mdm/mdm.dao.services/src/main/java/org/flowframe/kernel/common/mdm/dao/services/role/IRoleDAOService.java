package org.flowframe.kernel.common.mdm.dao.services.role;

import java.util.List;

import org.flowframe.kernel.common.mdm.domain.role.Role;

public interface IRoleDAOService {
	public Role get(long id);
	
	public List<Role> getAll();
	
	public Role getByCode(String code);	

	public Role add(Role record);

	public void delete(Role record);

	public Role update(Role record);
	
	public Role provide(Role record);
	
	public Role provide(String name);
	
	public void provideDefaults();

	public Role getByName(String name);
}
