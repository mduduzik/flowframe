package org.flowframe.ui.component.dao.services.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.flowframe.ds.domain.DataSource;
import org.flowframe.kernel.metamodel.dao.services.IBasicTypeDAOService;
import org.flowframe.kernel.metamodel.dao.services.IEntityTypeDAOService;
import org.flowframe.ui.component.dao.services.IComponentDAOService;
import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.masterdetail.MasterDetailComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Implementation of {@link AbstractComponent} that uses JPA for
 * persistence.
 * <p />
 * <p/>
 * This class is marked as {@link Transactional}. The Spring configuration for
 * this module, enables AspectJ weaving for adding transaction demarcation to
 * classes annotated with <code>@Transactional</code>.
 */
@Transactional
@Repository
public class ComponentDAOImpl implements IComponentDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private transient Map<String, AbstractComponent> cache = new HashMap<String, AbstractComponent>();

	/**
	 * Spring will inject a managed JPA {@link EntityManager} into this field.
	 */
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private IEntityTypeDAOService entityTypeDao;

	@Autowired
	private IBasicTypeDAOService basicTypeDao;

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public AbstractComponent get(long id) {
		return em.getReference(AbstractComponent.class, id);
	}

	@Override
	public List<AbstractComponent> getAll() {
		return em.createQuery("select o from com.conx.logistics.kernel.ui.components.domain.AbstractComponent o record by o.id",
				AbstractComponent.class).getResultList();
	}

	@Override
	public AbstractComponent add(AbstractComponent record) {
		record = em.merge(record);

		return record;
	}

	@Override
	public AbstractComponent getByCode(String code) {
		AbstractComponent comp = null;

		try {
			Query q = em.createQuery("select o from com.conx.logistics.kernel.ui.components.domain.AbstractComponent o WHERE o.code = :code");
			q.setParameter("code", code);
			return (AbstractComponent) q.getSingleResult();
//			CriteriaBuilder builder = em.getCriteriaBuilder();
//			CriteriaQuery query = builder.createQuery();
//			Root<AbstractComponent> rootEntity = query.from(AbstractComponent.class);
//			ParameterExpression<String> p = builder.parameter(String.class);
//			query.select(rootEntity).where(builder.equal(rootEntity.get("code"), p));
//
//			TypedQuery<AbstractComponent> typedQuery = em.createQuery(query);
//			typedQuery.setParameter(p, code);
//
//			comp = typedQuery.getSingleResult();
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

		return comp;
	}

	@Override
	public void delete(AbstractComponent record) {
		em.remove(record);
	}

	@Override
	public AbstractComponent update(AbstractComponent record) {
		return em.merge(record);
	}

	@Override
	public MasterDetailComponent getMasterDetailByDataSource(DataSource ds) {
		MasterDetailComponent mdc = null;

		try {
			TypedQuery<MasterDetailComponent> q = em
					.createQuery(
							"select DISTINCT o from com.conx.logistics.kernel.ui.components.domain.AbstractComponent o WHERE o.typeId = :typeId AND o.dataSource = :dataSource",
							MasterDetailComponent.class);
			q.setParameter("typeId", "masterdetailcomponent");
			q.setParameter("dataSource", ds);
			mdc = q.getSingleResult();
		} catch (javax.persistence.NoResultException e) {
		}

		return mdc;
	}
}
