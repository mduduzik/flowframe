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
import org.flowframe.kernel.common.mdm.dao.services.IAddressTypeAddressDAOService;
import org.flowframe.kernel.common.mdm.domain.geolocation.Address;
import org.flowframe.kernel.common.mdm.domain.geolocation.AddressType;
import org.flowframe.kernel.common.mdm.domain.geolocation.AddressTypeAddress;

@Transactional
@Repository
public class AddressTypeAddressDAOImpl implements IAddressTypeAddressDAOService {
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
	public AddressTypeAddress get(long id) {
		return em.getReference(AddressTypeAddress.class, id);
	}

	@Override
	public List<AddressTypeAddress> getAll() {
		return em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.geolocation.Address o record by o.id", AddressTypeAddress.class).getResultList();
	}

	@Override
	public AddressTypeAddress add(AddressTypeAddress record) {
		record = em.merge(record);

		return record;
	}

	@Override
	public void delete(AddressTypeAddress record) {
		em.remove(record);
	}

	@Override
	public AddressTypeAddress update(AddressTypeAddress record) {
		return em.merge(record);
	}

	@Override
	public AddressTypeAddress provide(AddressTypeAddress record) {
		AddressTypeAddress existingRecord = getByCode(record.getCode());
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

	public AddressTypeAddress getByTypeAndAddress(AddressType type, Address address) {
		AddressTypeAddress res = null;
		try {
			TypedQuery<AddressTypeAddress> q = em
					.createQuery(
							"SELECT AddressTypeAddress FROM org.flowframe.kernel.common.mdm.domain.geolocation.AddressTypeAddress AS addressTypeAddress WHERE addressTypeAddress.type.id = :typeId AND addressTypeAddress.address.id = :addressId",
							AddressTypeAddress.class);
			q.setParameter("typeId", type.getId());
			q.setParameter("addressId", address.getId());
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
	public AddressTypeAddress provide(AddressType type, Address address) {

		AddressTypeAddress res = null;

		res = getByTypeAndAddress(type, address);
		if (Validator.isNull(res)) {
			res = new AddressTypeAddress();
			res.setName(type.getName());
			res.setType(type);
			res.setAddress(address);
			res.setCode(type.getCode() + "@" + address.getCode());
			
			res = add(res);
		}

		return res;
	}

	@Override
	public AddressTypeAddress getByCode(String code) {
		AddressTypeAddress res = null;
		try {
			TypedQuery<AddressTypeAddress> q = em
					.createQuery(
							"SELECT AddressTypeAddress FROM org.flowframe.kernel.common.mdm.domain.geolocation.AddressTypeAddress AS addressTypeAddress WHERE addressTypeAddress.code = :code",
							AddressTypeAddress.class);
			q.setParameter("code", code);
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
	public AddressTypeAddress getById(Long id) {
		AddressTypeAddress res = null;
		try {
			TypedQuery<AddressTypeAddress> q = em
					.createQuery(
							"SELECT AddressTypeAddress FROM org.flowframe.kernel.common.mdm.domain.geolocation.AddressTypeAddress AS addressTypeAddress WHERE addressTypeAddress.id = :id",
							AddressTypeAddress.class);
			q.setParameter("id", id);
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
}
