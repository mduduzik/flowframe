package org.flowframe.kernel.common.mdm.domain.organization;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;
import org.flowframe.kernel.common.mdm.domain.geolocation.AddressTypeAddress;



@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name="ffreforganization")
public class Organization extends MultitenantBaseEntity {
	
	private long organizationId;
	private long parentOrganizationId;
	private String type;
	
	@XmlTransient
	@OneToMany(targetEntity = AddressTypeAddress.class)
    private Set<AddressTypeAddress> addressTypeAddresses = new HashSet<AddressTypeAddress>();
    
	@XmlTransient
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

	public long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	public long getParentOrganizationId() {
		return parentOrganizationId;
	}

	public void setParentOrganizationId(long parentOrganizationId) {
		this.parentOrganizationId = parentOrganizationId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}	
}
