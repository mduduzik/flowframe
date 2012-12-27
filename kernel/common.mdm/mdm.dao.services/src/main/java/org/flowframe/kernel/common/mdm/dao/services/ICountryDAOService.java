package org.flowframe.kernel.common.mdm.dao.services;

import java.util.List;

import org.flowframe.kernel.common.mdm.domain.geolocation.Country;

public interface ICountryDAOService {
	public Country get(long id);
	
	public List<Country> getAll();
	
	public Country getByCode(String code);	

	public Country add(Country record);

	public void delete(Country record);

	public Country update(Country record);
	
	public Country provide(Country record);
	
	public Country provide(String code, String name);
}
