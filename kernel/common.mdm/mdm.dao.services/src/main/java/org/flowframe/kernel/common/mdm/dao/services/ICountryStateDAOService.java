package org.flowframe.kernel.common.mdm.dao.services;

import java.util.List;

import org.flowframe.kernel.common.mdm.domain.geolocation.CountryState;

public interface ICountryStateDAOService {
	public CountryState get(long id);
	
	public List<CountryState> getAll();
	
	public CountryState getByCode(String code);	

	public CountryState add(CountryState record);

	public void delete(CountryState record);

	public CountryState update(CountryState record);
	
	public CountryState provide(CountryState record);
	
	public CountryState provide(String code, 
			 String name,
			 Long countryPK);	
}
