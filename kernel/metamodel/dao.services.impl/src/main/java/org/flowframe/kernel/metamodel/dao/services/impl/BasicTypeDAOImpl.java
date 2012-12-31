package org.flowframe.kernel.metamodel.dao.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.flowframe.kernel.common.mdm.domain.metamodel.BasicType;
import org.flowframe.kernel.metamodel.dao.services.IBasicTypeDAOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link BasicType} that uses JPA for persistence.
 * <p />
 * <p/>
 * This class is marked as {@link Transactional}. The Spring configuration for
 * this module, enables AspectJ weaving for adding transaction demarcation to
 * classes annotated with <code>@Transactional</code>.
 */
@Transactional
@Repository
public class BasicTypeDAOImpl implements IBasicTypeDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private transient Map<String, BasicType> cache = new HashMap<String, BasicType>();

	/**
	 * Spring will inject a managed JPA {@link EntityManager} into this field.
	 */
	@PersistenceContext
	private EntityManager em;

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public BasicType get(long id) {
		return em.getReference(BasicType.class, id);
	}

	@Override
	public List<BasicType> getAll() {
		return em.createQuery("select o from com.conx.logistics.kernel.metamodel.domain.metadata.BasicType o record by o.id", BasicType.class).getResultList();
	}

	@Override
	public BasicType getByClass(Class entityClass) {
		BasicType record = null;

		try {
			logger.error("getByClass(" + entityClass.getName() + ")");

			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<BasicType> query = builder.createQuery(BasicType.class);
			Root<BasicType> rootEntity = query.from(BasicType.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("entityJavaType"), p));

			TypedQuery<BasicType> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, entityClass.getName());

			return typedQuery.getSingleResult();
			// TypedQuery<BasicType> q =
			// em.createQuery("select DISTINCT  o from com.conx.logistics.kernel.metamodel.domain.metadata.BasicType o WHERE o.entityJavaSimpleType = :entityJavaSimpleType",BasicType.class);
			// q.setParameter("entityJavaSimpleType",
			// entityClass.getSimpleName());
			// record = q.getSingleResult();
		} catch (NoResultException e) {
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

		return record;
	}

	@Override
	public BasicType add(BasicType record) {
		record = em.merge(record);

		return record;
	}

	@Override
	public void delete(BasicType record) {
		em.remove(record);
	}

	@Override
	public BasicType update(BasicType record) {
		return em.merge(record);
	}

	@Override
	public BasicType provide(Class javaType) {
		// System.out.println("providing type for ("+javaType.getName()+")");
		BasicType targetBasicType = null;
		targetBasicType = getByClass(javaType);
		if (targetBasicType == null) {
			targetBasicType = new BasicType(javaType.getName(), javaType);

			// Save
			targetBasicType = em.merge(targetBasicType);
		}

		return targetBasicType;
	}
}
