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
import org.flowframe.kernel.common.mdm.dao.services.IContactTypeContactDAOService;
import org.flowframe.kernel.common.mdm.domain.organization.Contact;
import org.flowframe.kernel.common.mdm.domain.organization.ContactType;
import org.flowframe.kernel.common.mdm.domain.organization.ContactTypeContact;

@Transactional
@Repository
public class ContactTypeContactDAOImpl implements IContactTypeContactDAOService {
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
	public ContactTypeContact get(long id) {
		return em.getReference(ContactTypeContact.class, id);
	}

	@Override
	public List<ContactTypeContact> getAll() {
		return em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.organization.ContactTypeContact o record by o.id", ContactTypeContact.class).getResultList();
	}

	@Override
	public ContactTypeContact add(ContactTypeContact record) {
		record = em.merge(record);

		return record;
	}

	@Override
	public void delete(ContactTypeContact record) {
		em.remove(record);
	}

	@Override
	public ContactTypeContact update(ContactTypeContact record) {
		return em.merge(record);
	}

	@Override
	public ContactTypeContact provide(ContactTypeContact record) {
		ContactTypeContact existingRecord = getByCode(record.getCode());
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

	public ContactTypeContact getByTypeAndContact(ContactType type, Contact contact) {
		ContactTypeContact res = null;
		try {
			TypedQuery<ContactTypeContact> q = em
					.createQuery(
							"SELECT ContactTypeContact FROM org.flowframe.kernel.common.mdm.domain.organization.ContactTypeContact AS contactTypeContact WHERE contactTypeContact.type.id = :typeId AND contactTypeContact.contact.id = :contactId",
							ContactTypeContact.class);
			q.setParameter("typeId", type.getId());
			q.setParameter("contactId", contact.getId());
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
	public ContactTypeContact provide(ContactType type, Contact contact) {

		ContactTypeContact res = null;

		res = getByTypeAndContact(type, contact);
		if (Validator.isNull(res)) {
			res = new ContactTypeContact();
			res.setName(type.getName());
			res.setType(type);
			res.setContact(contact);
			res.setCode(type.getCode() + "@" + contact.getCode());
			
			res = add(res);
		}

		return res;
	}

	@Override
	public ContactTypeContact getByCode(String code) {
		ContactTypeContact res = null;
		try {
			TypedQuery<ContactTypeContact> q = em
					.createQuery(
							"SELECT ContactTypeContact FROM org.flowframe.kernel.common.mdm.domain.organization.ContactTypeContact AS contactTypeContact WHERE contactTypeContact.code = :code",
							ContactTypeContact.class);
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
}
