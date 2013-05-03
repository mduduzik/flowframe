package org.flowframe.erp.app.mdm.dao.services;

import java.util.List;

import org.flowframe.erp.app.mdm.domain.currency.CurrencyUnit;
import org.flowframe.kernel.common.mdm.domain.geolocation.Country;


public interface ICurrencyUnitDAOService {
	public CurrencyUnit get(long id);
	
	public List<CurrencyUnit> getAll();
	
	public CurrencyUnit getByCode(String code);	

	public CurrencyUnit add(CurrencyUnit record);

	public void delete(CurrencyUnit record);

	public CurrencyUnit update(CurrencyUnit record);
	
	public CurrencyUnit provide(CurrencyUnit record);
	
	public CurrencyUnit provide(String code, String name, Country country);
	
	public void provideDefaults();
}
