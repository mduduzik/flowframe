package org.flowframe.kernel.common.mdm.dao.services;

import java.util.List;

import org.flowframe.kernel.common.mdm.domain.organization.Contact;
import org.flowframe.kernel.common.mdm.domain.organization.ContactType;
import org.flowframe.kernel.common.mdm.domain.organization.ContactTypeContact;

public interface IContactTypeContactDAOService {
	public ContactTypeContact get(long id);
	
	public List<ContactTypeContact> getAll();
	
	public ContactTypeContact getByCode(String code);	
	
	public ContactTypeContact getByTypeAndContact(ContactType type, Contact contact);

	public ContactTypeContact add(ContactTypeContact record);

	public void delete(ContactTypeContact record);

	public ContactTypeContact update(ContactTypeContact record);
	
	public ContactTypeContact provide(ContactTypeContact record);
	
	public ContactTypeContact provide(ContactType type, Contact address);	
}
