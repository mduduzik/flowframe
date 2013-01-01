package org.flowframe.kernel.common.mdm.dao.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.flowframe.kernel.common.utils.Validator;
import org.flowframe.kernel.common.mdm.dao.services.IAddressDAOService;
import org.flowframe.kernel.common.mdm.dao.services.ICountryDAOService;
import org.flowframe.kernel.common.mdm.dao.services.ICountryStateDAOService;
import org.flowframe.kernel.common.mdm.dao.services.IUnlocoDAOService;
import org.flowframe.kernel.common.mdm.domain.geolocation.Address;
import org.flowframe.kernel.common.mdm.domain.geolocation.Country;
import org.flowframe.kernel.common.mdm.domain.geolocation.CountryState;
import org.flowframe.kernel.common.mdm.domain.geolocation.Unloco;
import org.flowframe.kernel.common.mdm.domain.metamodel.EntityType;

@Transactional
@Repository
public class AddressDAOImpl implements IAddressDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * Spring will inject a managed JPA {@link EntityManager} into this field.
	 */
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ICountryDAOService countryDao;

	@Autowired
	private ICountryStateDAOService countryStateDao;

	@Autowired
	private IUnlocoDAOService unlocoDao;
	
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public Address get(long id) {
		return em.getReference(Address.class, id);
	}

	@Override
	public List<Address> getAll() {
		return em
				.createQuery(
						"select o from org.flowframe.kernel.common.mdm.domain.geolocation.Address o record by o.id",
						Address.class).getResultList();
	}

	@Override
	public Address getByTypeAndId(Long entityTypeId, Long entityId) {
		Address res = null;
		try {
			TypedQuery<Address> q = em
					.createQuery(
							"SELECT Address FROM org.flowframe.kernel.common.mdm.domain.geolocation.Address AS address WHERE address.ownerEntityTypeId = :ownerEntityTypeId AND address.ownerEntityId = :ownerEntityId",
							Address.class);
			q.setParameter("ownerEntityTypeId", entityTypeId);
			q.setParameter("ownerEntityId", entityId);
			res = q.getSingleResult();
		} catch (NoResultException e) {
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}

		return res;
	}

	@Override
	public Address getByCode(String code) {
		Address org = null;

		try {
			TypedQuery<Address> q = em
					.createQuery(
							"select o from org.flowframe.kernel.common.mdm.domain.geolocation.Address o WHERE o.code = :code",
							Address.class);
			q.setParameter("code", code);

			org = q.getSingleResult();
		} catch (NoResultException e) {
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}

		return org;
	}

	@Override
	public Address add(Address record) {
		record = em.merge(record);

		return record;
	}

	@Override
	public void delete(Address record) {
		em.remove(record);
	}

	@Override
	public Address update(Address record) {
		return em.merge(record);
	}

	@Override
	public Address provide(Address record) {
//		ClassLoader cl = Thread.currentThread().getContextClassLoader();
//		Thread.currentThread().setContextClassLoader(
//				this.getClass().getClassLoader());
		Address existingRecord = getByCode(record.getCode());
		if (Validator.isNull(existingRecord)) {
			record = update(record);
			try {
				// em.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return record;
	}

	@Override
	public Address provide(EntityType entityType, Long entityId, String street1,
			String street2, String zipCode, String stateOrProvince,
			String unlocoCode, String unlocoPortCity, String countryCode,
			String countryName, String stateCode, String stateName) {

		Address res = null;

		res = getByTypeAndId(entityType.getId(), entityId);
		if (Validator.isNull(res)) {
			res = new Address();
			res.setOwnerEntityTypeId(entityType.getId());
			res.setOwnerEntityId(entityId);
			res.setCode(street1+"@"+entityType+"@"+entityId);
			res.setDescription(street1);
			res.setStreet1(street1);
			res.setStreet2(street2);

			// --Country
			Country country = null;
			if (Validator.isNotNull(countryCode)) {
				country = countryDao.provide(countryCode, countryName);
				res.setCountry(country);
			}

			// --Country
			CountryState countryState = null;
			Long statePK = null;
			if (Validator.isNotNull(stateCode)) {
				countryState = countryStateDao.provide(stateCode, stateName,
						country.getId());
				statePK = countryState.getId();
			}

			// --Unloco
			if (Validator.isNotNull(unlocoCode)) {
				Unloco unloco = unlocoDao.provide(unlocoCode, unlocoCode,
						unlocoPortCity, country.getId(), statePK);
				res.setUnloco(unloco);
			}

			res = add(res);
		}

		return res;
	}
}
