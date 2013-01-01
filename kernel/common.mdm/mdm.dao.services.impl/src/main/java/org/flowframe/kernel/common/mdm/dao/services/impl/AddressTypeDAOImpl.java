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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.flowframe.kernel.common.utils.Validator;
import org.flowframe.kernel.common.mdm.dao.services.IAddressTypeDAOService;
import org.flowframe.kernel.common.mdm.domain.constants.AddressTypeCustomCONSTANTS;
import org.flowframe.kernel.common.mdm.domain.geolocation.AddressType;

@Transactional
@Repository
public class AddressTypeDAOImpl implements IAddressTypeDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * Spring will inject a managed JPA {@link EntityManager} into this field.
	 */
	@PersistenceContext
	private EntityManager em;

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public AddressType get(long id) {
		return em.getReference(AddressType.class, id);
	}

	@Override
	public List<AddressType> getAll() {
		return em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.geolocation.AddressType o record by o.id", AddressType.class).getResultList();
	}

	@Override
	public AddressType getByCode(String code) {
		AddressType org = null;

		try {
			TypedQuery<AddressType> q = em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.geolocation.AddressType o WHERE o.code = :code", AddressType.class);
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
	public AddressType add(AddressType record) {
		record = em.merge(record);

		return record;
	}

	@Override
	public void delete(AddressType record) {
		em.remove(record);
	}

	@Override
	public AddressType update(AddressType record) {
		return em.merge(record);
	}

	@Override
	public AddressType provide(AddressType record) {
		AddressType existingRecord = getByCode(record.getCode());
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
	public AddressType provide(String code, String name) {
		AddressType res = null;
		if ((res = getByCode(code)) == null) {
			AddressType unit = new AddressType();

			unit.setCode(code);
			unit.setName(name);

			res = add(unit);
		}
		return res;
	}

	@Override
	public void provideDefaults() {
		provide(AddressTypeCustomCONSTANTS.TYPE_MAIN, AddressTypeCustomCONSTANTS.TYPE_MAIN_DESCRIPTION);
		provide(AddressTypeCustomCONSTANTS.TYPE_ADHOC_ADDRESS, AddressTypeCustomCONSTANTS.TYPE_ADHOC_ADDRESS_DESCRIPTION);
		provide(AddressTypeCustomCONSTANTS.TYPE_BILLING_ADDRESS, AddressTypeCustomCONSTANTS.TYPE_BILLING_ADDRESS_DESCRIPTION);
		provide(AddressTypeCustomCONSTANTS.TYPE_DELIVERY_ADDRESS, AddressTypeCustomCONSTANTS.TYPE_DELIVERY_ADDRESS_DESCRIPTION);
		provide(AddressTypeCustomCONSTANTS.TYPE_PICKUP_ADDRESS, AddressTypeCustomCONSTANTS.TYPE_PICKUP_ADDRESS_DESCRIPTION);
		provide(AddressTypeCustomCONSTANTS.TYPE_RECEIVING_ADDRESS, AddressTypeCustomCONSTANTS.TYPE_RECEIVING_ADDRESS_DESCRIPTION);
		provide(AddressTypeCustomCONSTANTS.TYPE_SHIPPING_ADDRESS, AddressTypeCustomCONSTANTS.TYPE_SHIPPING_ADDRESS_DESCRIPTION);
	}
}
