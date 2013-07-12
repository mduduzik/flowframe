package org.flowframe.erp.app.mdm.dao.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.flowframe.erp.app.mdm.dao.services.ICurrencyUnitDAOService;
import org.flowframe.erp.app.mdm.domain.constants.CurrencyUnitCustomCONSTANTS;
import org.flowframe.erp.app.mdm.domain.currency.CurrencyUnit;
import org.flowframe.kernel.common.mdm.dao.services.ICountryDAOService;
import org.flowframe.kernel.common.mdm.domain.geolocation.Country;
import org.flowframe.kernel.common.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public class CurrencyUnitDAOImpl implements ICurrencyUnitDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
    /**
     * Spring will inject a managed JPA {@link EntityManager} into this field.
     */
    @PersistenceContext
    private EntityManager em;	
    
    @Autowired
    private ICountryDAOService countryDao;     
    
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public CurrencyUnit get(long id) {
		return em.getReference(CurrencyUnit.class, id);
	}    

	@Override
	public List<CurrencyUnit> getAll() {
		return em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.currency.CurrencyUnit o record by o.id",CurrencyUnit.class).getResultList();
	}
	
	@Override
	public CurrencyUnit getByCode(String code) {
		CurrencyUnit org = null;
		
		try
		{
			Query query = em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.currency.CurrencyUnit o WHERE o.code = :code order by o.name");
			query.setParameter("code", code);
			org = (CurrencyUnit)query.getSingleResult();				
		}
		catch(NoResultException e){}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		catch(Error e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}		
		
		return org;
	}	

	@Override
	public CurrencyUnit add(CurrencyUnit record) {
		record = em.merge(record);
		
		return record;
	}

	@Override
	public void delete(CurrencyUnit record) {
		em.remove(record);
	}

	@Override
	public CurrencyUnit update(CurrencyUnit record) {
		return em.merge(record);
	}


	@Override
	public CurrencyUnit provide(CurrencyUnit record) {
		CurrencyUnit existingRecord = getByCode(record.getCode());
		if (Validator.isNull(existingRecord))
		{		
			record = update(record);
			try {
				//em.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return record;
	}
	
	@Override
	public CurrencyUnit provide(String code, String name, Country country) {
		CurrencyUnit res = null;
		if ((res = getByCode(code)) == null)
		{
			CurrencyUnit unit = new CurrencyUnit();

			unit.setCode(code);
			unit.setName(name);
			unit.setCountry(country);

			res = add(unit);
		}
		return res;
	}	
	
	@Override
	public void provideDefaults()
	{
		Country country = countryDao.provide(CurrencyUnitCustomCONSTANTS.CURRENCY_USD_COUNTRY_CODE, CurrencyUnitCustomCONSTANTS.CURRENCY_USD_COUNTRY_NAME);
		provide(CurrencyUnitCustomCONSTANTS.CURRENCY_USD_CODE,CurrencyUnitCustomCONSTANTS.CURRENCY_USD_NAME,country);
	}
}
