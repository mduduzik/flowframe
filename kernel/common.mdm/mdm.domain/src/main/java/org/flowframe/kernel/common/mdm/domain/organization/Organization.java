package org.flowframe.kernel.common.mdm.domain.organization;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;
import org.flowframe.kernel.common.mdm.domain.geolocation.AddressTypeAddress;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="reforganization")
public class Organization extends MultitenantBaseEntity {
	@OneToMany(targetEntity = AddressTypeAddress.class)
    private Set<AddressTypeAddress> addressTypeAddresses = new HashSet<AddressTypeAddress>();
    
    @OneToMany(targetEntity = ContactTypeContact.class)
    private Set<ContactTypeContact> contactTypeContacts = new HashSet<ContactTypeContact>();
    
	public Set<AddressTypeAddress> getAddressTypeAddresses() {
		return addressTypeAddresses;
	}

	public void setAddressTypeAddresses(Set<AddressTypeAddress> addressTypeAddresses) {
		this.addressTypeAddresses = addressTypeAddresses;
	}

	public Set<ContactTypeContact> getContactTypeContacts() {
		return contactTypeContacts;
	}

	public void setContactTypeContacts(Set<ContactTypeContact> contactTypeContacts) {
		this.contactTypeContacts = contactTypeContacts;
	}	
}
