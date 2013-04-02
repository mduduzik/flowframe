package org.flowframe.kernel.common.mdm.domain.organization;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;
import org.flowframe.kernel.common.utils.Validator;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name = "ffrefcontactgroup")
public class ContactGroup extends MultitenantBaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5092331784666543794L;
    
    @OneToMany(targetEntity = ContactGroupContact.class,cascade = CascadeType.PERSIST,fetch=FetchType.EAGER)
    private Set<ContactGroupContact> contacts = new HashSet<ContactGroupContact>();

	public Set<ContactGroupContact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<ContactGroupContact> contacts) {
		this.contacts = contacts;
	}
	
	@Transient
	public String generateDelimitedEmailList(char delim)
	{
		String emailList = "";
		for (ContactGroupContact cgc : getContacts())
		{
			emailList += cgc.getContact().getEmail()+delim;
		}
		
		if (Validator.isNotNull(emailList))
			emailList = emailList.substring(0,emailList.length()-1);
		
		return emailList;
	}
}