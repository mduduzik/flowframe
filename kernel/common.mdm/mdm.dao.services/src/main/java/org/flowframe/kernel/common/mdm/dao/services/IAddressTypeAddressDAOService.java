package org.flowframe.kernel.common.mdm.dao.services;

import java.util.List;

import org.flowframe.kernel.common.mdm.domain.geolocation.Address;
import org.flowframe.kernel.common.mdm.domain.geolocation.AddressType;
import org.flowframe.kernel.common.mdm.domain.geolocation.AddressTypeAddress;

public interface IAddressTypeAddressDAOService {
	public AddressTypeAddress get(long id);
	
	public List<AddressTypeAddress> getAll();
	
	public AddressTypeAddress getByCode(String code);	
	
	public AddressTypeAddress getById(Long id);

	public AddressTypeAddress add(AddressTypeAddress record);

	public void delete(AddressTypeAddress record);

	public AddressTypeAddress update(AddressTypeAddress record);
	
	public AddressTypeAddress provide(AddressTypeAddress record);
	
	public AddressTypeAddress provide(AddressType type, Address address);	
}
