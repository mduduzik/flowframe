package org.flowframe.kernel.common.mdm.domain.geolocation;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.IRelationEntity;
import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;


@SuppressWarnings("serial")
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="refaddresstypeaddress")
public class AddressTypeAddress extends MultitenantBaseEntity implements IRelationEntity {
	@ManyToOne
	private AddressType type;
	
	@OneToOne
	@JoinColumn()
	private Address address;
	
	public AddressTypeAddress() {
	}
	
	public AddressTypeAddress(AddressType type, Address address) {
		this.type = type;
		this.address = address;
	}

	public AddressType getType() {
		return type;
	}

	public void setType(AddressType type) {
		this.type = type;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public Object getIdentifierPropertyId() {
		return "type";
	}

	@Override
	public Object getEntityPropertyId() {
		return "address";
	}
}
