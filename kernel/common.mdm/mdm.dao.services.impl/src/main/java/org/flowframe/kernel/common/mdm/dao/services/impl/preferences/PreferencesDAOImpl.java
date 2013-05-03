package org.flowframe.kernel.common.mdm.dao.services.impl.preferences;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.flowframe.kernel.common.mdm.dao.services.IEntityMetadataDAOService;
import org.flowframe.kernel.common.mdm.dao.services.preferences.IPreferencesDAOService;
import org.flowframe.kernel.common.mdm.domain.preferences.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class PreferencesDAOImpl implements IPreferencesDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * Spring will inject a managed JPA {@link EntityManager} into this field.
	 */
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private IEntityMetadataDAOService entityMetadataDao;

	@Autowired
	private PlatformTransactionManager globalTransactionManager;

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public Preferences get(long id) {
		return em.getReference(Preferences.class, id);
	}

	@Override
	public List<Preferences> getAll() {
		return em
				.createQuery(
						"select o from org.flowframe.kernel.common.mdm.domain.preferences.Preferences o record by o.id",
						Preferences.class).getResultList();
	}

	@Override
	public Preferences getByCode(String code) {
		Preferences org = null;

		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Preferences> query = builder
					.createQuery(Preferences.class);
			Root<Preferences> rootEntity = query.from(Preferences.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(
					builder.equal(rootEntity.get("code"), p));

			TypedQuery<Preferences> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, code);

			org = typedQuery.getSingleResult();
			// TypedQuery<Preferences> q =
			// em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.referencenumber.Preferences o WHERE o.code = :code",Preferences.class);
			// q.setParameter("code", code);

			// org = q.getSingleResult();
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
	public Preferences add(Preferences record) {
		record = em.merge(record);

		return record;
	}

	@Override
	public void delete(Preferences record) {
		em.remove(record);
	}

	@Override
	public Preferences update(Preferences record) {
		return em.merge(record);
	}

	@Override
	public Preferences add(Long parentEntityPK, Class<?> parentEntityType) {
		/*
		 * assert (parentEntityPK != null) : "Parent Entity Id was null.";
		 * assert (parentEntityType != null) : "Parent Entity Type was null.";
		 * Object parentEntity = em.getReference(parentEntityType,
		 * parentEntityPK); assert (parentEntity instanceof BaseEntity) :
		 * "Parent Entity was not of type Base Entity."; DefaultEntityMetadata
		 * parentEntityMetaData = entityMetadataDao.provide(parentEntityType);
		 * assert (parentEntityMetaData != null) :
		 * "Could not get parent entity metadata."; Preferences newRecord = new
		 * Preferences(); newRecord.setEntityPK(((BaseEntity)
		 * parentEntity).getId());
		 * newRecord.setEntityMetadata(parentEntityMetaData); newRecord =
		 * em.merge(newRecord);
		 * 
		 * String format = String.format("%%0%dd", 3); String paddedId =
		 * String.format(format, newRecord.getId()); String code = ((BaseEntity)
		 * parentEntity).getCode() + "-RN" + paddedId; newRecord.setName(code);
		 * newRecord.setCode(code); newRecord = em.merge(newRecord);
		 * 
		 * return newRecord;
		 */
		return null;
	}

	public IEntityMetadataDAOService getEntityMetadataDao() {
		return entityMetadataDao;
	}

	public void setEntityMetadataDao(IEntityMetadataDAOService entityMetadataDao) {
		this.entityMetadataDao = entityMetadataDao;
	}

	public void init() {
		logger.info("init called...");
	}
}
