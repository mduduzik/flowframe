package org.flowframe.kernel.common.mdm.domain.organization;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name = "ffrefcontactgroupcontact")
public class ContactGroupContact extends MultitenantBaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6922487368393508164L;

	@OneToOne
    private Contact contact;

    @ManyToOne
    private ContactGroup contactGroup;       

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public ContactGroup getContactGroup() {
		return contactGroup;
	}

	public void setContactGroup(ContactGroup contactGroup) {
		this.contactGroup = contactGroup;
	}
}