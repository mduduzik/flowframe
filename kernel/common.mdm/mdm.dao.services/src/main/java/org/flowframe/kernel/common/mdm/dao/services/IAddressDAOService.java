package org.flowframe.kernel.common.mdm.dao.services;

import java.util.List;

import org.flowframe.kernel.common.mdm.domain.geolocation.Address;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityType;

public interface IAddressDAOService {
	public Address get(long id);
	
	public List<Address> getAll();
	
	public Address getByCode(String code);	
	
	public Address getByTypeAndId(Long entityType, Long entityId);

	public Address add(Address record);

	public void delete(Address record);

	public Address update(Address record);
	
	public Address provide(Address record);
	
	public Address provide(EntityType entityType, Long entityId, String street1,
			String street2, String zipCode, String stateOrProvince,
			String unlocoCode, String unlocoPortCity, String countryCode,
			String countryName, String stateCode, String stateName);	
}
