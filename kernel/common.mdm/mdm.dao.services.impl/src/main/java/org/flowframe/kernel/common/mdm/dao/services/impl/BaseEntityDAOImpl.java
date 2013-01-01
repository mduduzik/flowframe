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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.flowframe.kernel.common.utils.Validator;
import org.flowframe.kernel.common.mdm.dao.services.IBaseEntityDAOService;
import org.flowframe.kernel.common.mdm.domain.BaseEntity;

@Transactional
@Repository
public class BaseEntityDAOImpl implements IBaseEntityDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@PersistenceContext
	private EntityManager em;

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseEntity get(long id, @SuppressWarnings("rawtypes") Class entityClass) {
		 return (BaseEntity) em.getReference(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> getAll(@SuppressWarnings("rawtypes") Class entityClass) {
		return em.createQuery("select o from " + entityClass.getCanonicalName() + " o record by o.id", entityClass).getResultList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public BaseEntity getByCode(String code, Class entityClass) {
		BaseEntity baseEntity = null;

		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<?> query = builder.createQuery(entityClass);
			Root rootEntity = query.from(entityClass);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("code"), p));

			TypedQuery<?> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, code);

			baseEntity = (BaseEntity) typedQuery.getSingleResult();
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

		return baseEntity;
	}

	@Override
	public BaseEntity add(BaseEntity record) {
		record = em.merge(record);
		return record;
	}

	@Override
	public void delete(BaseEntity record) {
		em.remove(record);
	}

	@Override
	public BaseEntity update(BaseEntity record) {
		return em.merge(record);
	}

	@Override
	public BaseEntity provide(BaseEntity record) {
		BaseEntity existingRecord = getByCode(record.getCode(), record.getClass());
		if (Validator.isNull(existingRecord)) {
			record = update(record);
			try {
				// em.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return record;
	}

}
