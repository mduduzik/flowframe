package org.flowframe.kernel.common.mdm.dao.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import org.flowframe.kernel.common.utils.Validator;
import org.flowframe.kernel.common.mdm.dao.services.IOrganizationDAOService;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;

@Transactional
@Repository
public class OrganizationDAOImpl implements IOrganizationDAOService {
	private static final String DEFAULT_ORGANIZATION_CODE = "DEFAULT";
	private static final String DEFAULT_ORGANIZATION_NAME = "Default";

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PlatformTransactionManager globalTransactionManager;

	/**
	 * Spring will inject a managed JPA {@link EntityManager} into this field.
	 */
	@PersistenceContext
	private EntityManager em;

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public Organization get(long id) {
		return em.getReference(Organization.class, id);
	}

	@Override
	public List<Organization> getAll() {
		return em
				.createQuery(
						"select o from org.flowframe.kernel.common.mdm.domain.organization.Organization o record by o.id",
						Organization.class).getResultList();
	}

	@Override
	public Organization getByCode(String code) {
		Organization org = null;

		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Organization> query = builder
					.createQuery(Organization.class);
			Root<Organization> rootEntity = query.from(Organization.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(
					builder.equal(rootEntity.get("code"), p));

			TypedQuery<Organization> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, code);

			org = typedQuery.getSingleResult();
			/*
			 * TypedQuery<Organization> q = em.createQuery(
			 * "select o from org.flowframe.kernel.common.mdm.domain.organization.Organization o WHERE o.code = :code"
			 * ,Organization.class); q.setParameter("code", code);
			 * 
			 * org = q.getSingleResult();
			 */
		} catch (NoResultException e) {
		} catch (EntityNotFoundException e) {
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		} catch (Error e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}

		return org;
	}

	@Override
	public Organization add(Organization record) {
		record = em.merge(record);

		return record;
	}

	@Override
	public void delete(Organization record) {
		em.remove(record);
	}

	@Override
	public Organization update(Organization record) {
		return em.merge(record);
	}

	@Override
	public Organization provide(Organization record) {
		Organization existingRecord = getByCode(record.getCode());
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
	public Organization provideDefault() {
		Organization org = getByCode(DEFAULT_ORGANIZATION_CODE);
		if (org == null) {
			org = new Organization();
			org.setCode(DEFAULT_ORGANIZATION_CODE);
			org.setName(DEFAULT_ORGANIZATION_NAME);
			org = add(org);
		}
		return org;
	}

	public void init() {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName("uat.sprint2.data");
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		def.setTimeout(1000);
		TransactionStatus status = this.globalTransactionManager.getTransaction(def);
		try {
			provideDefault();
			this.globalTransactionManager.commit(status);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
			this.globalTransactionManager.rollback(status);

			throw new RuntimeException(stacktrace, e);
		}
	}
}
