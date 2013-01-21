package org.flowframe.kernel.common.mdm.dao.services.impl.preferences;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.flowframe.kernel.common.mdm.dao.services.preferences.IEntityPreferenceDAOService;
import org.flowframe.kernel.common.mdm.domain.preferences.EntityPreference;
import org.flowframe.kernel.common.mdm.domain.preferences.EntityPreferenceItem;

public class EntityPreferenceDAOImpl implements IEntityPreferenceDAOService {

	@PersistenceContext
	private EntityManager em;

	@Override
	public EntityPreference get(long id) {
		return em.find(EntityPreference.class, id);
	}

	@Override
	public EntityPreferenceItem getItem(long id) {
		return em.find(EntityPreferenceItem.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<EntityPreference> getAll() {
		return (List<EntityPreference>) em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.preferences.EntityPreference o record by o.id").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<EntityPreferenceItem> getAllItems() {
		return (List<EntityPreferenceItem>) em.createQuery("select o from org.flowframe.kernel.common.mdm.domain.preferences.EntityPreferenceItem o record by o.id").getResultList();
	}

	@Override
	public EntityPreference getByCode(String code) {
		EntityPreference pref = null;

		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<EntityPreference> query = builder.createQuery(EntityPreference.class);
			Root<EntityPreference> rootEntity = query.from(EntityPreference.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("code"), p));

			TypedQuery<EntityPreference> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, code);

			pref = typedQuery.getSingleResult();
		} catch (NoResultException e) {
		}

		return pref;
	}

	@Override
	public EntityPreferenceItem getItemByCode(String code) {
		EntityPreferenceItem prefItem = null;

		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<EntityPreferenceItem> query = builder.createQuery(EntityPreferenceItem.class);
			Root<EntityPreferenceItem> rootEntity = query.from(EntityPreferenceItem.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("code"), p));

			TypedQuery<EntityPreferenceItem> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, code);

			prefItem = typedQuery.getSingleResult();
		} catch (NoResultException e) {
		}

		return prefItem;
	}

	@Override
	public EntityPreference add(EntityPreference record) {
		return em.merge(record);
	}

	@Override
	public EntityPreferenceItem addItem(EntityPreferenceItem record) {
		return em.merge(record);
	}

	@Override
	public void delete(EntityPreference record) {
		em.remove(record);
	}

	@Override
	public void deleteItem(EntityPreferenceItem record) {
		em.remove(record);
	}

	@Override
	public EntityPreference update(EntityPreference record) {
		return em.merge(record);
	}

	@Override
	public EntityPreferenceItem updateItem(EntityPreferenceItem record) {
		return em.merge(record);
	}

}
