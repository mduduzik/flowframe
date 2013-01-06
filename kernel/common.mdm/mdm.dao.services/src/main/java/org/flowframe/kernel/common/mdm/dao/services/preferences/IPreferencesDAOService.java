package org.flowframe.kernel.common.mdm.dao.services.preferences;

import java.util.List;

import org.flowframe.kernel.common.mdm.domain.preferences.Preferences;

public interface IPreferencesDAOService {
	public Preferences get(long id);
	
	public List<Preferences> getAll();
	
	public Preferences getByCode(String code);	

	public Preferences add(Preferences record);
	
	public Preferences add(Long parentEntityPK, Class<?> parentEntityType);

	public void delete(Preferences record);

	public Preferences update(Preferences record);
}
