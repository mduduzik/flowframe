package org.flowframe.kernel.common.mdm.dao.services;

import java.util.List;

import org.flowframe.kernel.common.mdm.domain.metadata.DefaultEntityMetadata;
import org.flowframe.kernel.common.mdm.domain.organization.Contact;

public interface IContactDAOService {
	public Contact get(long id);
	
	public List<Contact> getAll();
	
	public Contact getByCode(String code);	

	public Contact add(Contact record);

	public void delete(Contact record);

	public Contact update(Contact record);
	
	public Contact provide(Contact record);
	
	public Contact provide(DefaultEntityMetadata entityMetadata, Long entityPK, Contact record);
	
	public void provideDefaults();
}
