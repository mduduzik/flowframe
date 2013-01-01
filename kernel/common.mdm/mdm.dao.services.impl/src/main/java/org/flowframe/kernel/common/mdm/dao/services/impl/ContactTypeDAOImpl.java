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
import org.flowframe.kernel.common.mdm.dao.services.IContactTypeDAOService;
import org.flowframe.kernel.common.mdm.domain.constants.ContactTypeCustomCONSTANTS;
import org.flowframe.kernel.common.mdm.domain.organization.ContactType;

@Transactional
@Repository
public class ContactTypeDAOImpl implements IContactTypeDAOService {
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
	public ContactType get(long id) {
		return em.getReference(ContactType.class, id);
	}

	@Override
	public List<ContactType> getAll() {
		return em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.organization.ContactType o record by o.id", ContactType.class).getResultList();
	}

	@Override
	public ContactType getByCode(String code) {
		ContactType org = null;

		try {
			TypedQuery<ContactType> q = em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.organization.ContactType o WHERE o.code = :code", ContactType.class);
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
	public ContactType add(ContactType record) {
		record = em.merge(record);

		return record;
	}

	@Override
	public void delete(ContactType record) {
		em.remove(record);
	}

	@Override
	public ContactType update(ContactType record) {
		return em.merge(record);
	}

	@Override
	public ContactType provide(ContactType record) {
		ContactType existingRecord = getByCode(record.getCode());
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
	public ContactType provide(String code, String name) {
		ContactType res = null;
		if ((res = getByCode(code)) == null) {
			ContactType unit = new ContactType();

			unit.setCode(code);
			unit.setName(name);

			res = add(unit);
		}
		return res;
	}

	@Override
	public void provideDefaults() {
		provide(ContactTypeCustomCONSTANTS.TYPE_MAIN, ContactTypeCustomCONSTANTS.TYPE_MAIN_DESCRIPTION);
		provide(ContactTypeCustomCONSTANTS.TYPE_SECONDARY, ContactTypeCustomCONSTANTS.TYPE_SECONDARY_DESCRIPTION);
	}
}
