package org.flowframe.kernel.common.mdm.dao.services.preferences;

import java.util.Collection;

import org.flowframe.kernel.common.mdm.domain.preferences.EntityPreference;
import org.flowframe.kernel.common.mdm.domain.preferences.EntityPreferenceItem;

public interface IEntityPreferenceDAOService {
	public EntityPreference get(long id);
	
	public EntityPreferenceItem getItem(long id);
	
	public Collection<EntityPreference> getAll();
	
	public Collection<EntityPreferenceItem> getAllItems();
	
	public EntityPreference getByCode(String code);
	
	public EntityPreferenceItem getItemByCode(String code);

	public EntityPreference add(EntityPreference record);
	
	public EntityPreferenceItem addItem(EntityPreferenceItem record);

	public void delete(EntityPreference record);
	
	public void deleteItem(EntityPreferenceItem record);

	public EntityPreference update(EntityPreference record);
	
	public EntityPreferenceItem updateItem(EntityPreferenceItem record);
}
