package org.flowframe.erp.integration.adaptors.stripe.webhooks.service.impl;

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

import org.flowframe.erp.integration.adaptors.stripe.domain.event.Event;
import org.flowframe.erp.integration.adaptors.stripe.services.IEventDAOService;
import org.flowframe.kernel.common.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class EventDAOServiceImpl implements IEventDAOService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	@PersistenceContext
	private EntityManager em;
	


	@Override
	public List<Event> getAllActiveEvents() {
		return em.createQuery("select o from Event o record where o.active = true order by o.id", Event.class).getResultList();
	}
	
	@Override
	public Event getEventById(String eventId) {
		Event rec = null;

		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Event> query = builder.createQuery(Event.class);
			Root<Event> rootEntity = query.from(Event.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("stripeId"), p));

			TypedQuery<Event> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, eventId);

			rec = typedQuery.getSingleResult();
		} catch (NoResultException e) {
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}

		return rec;
	}
	
	@Override
	public Event add(Event record) {
		record = em.merge(record);
		return record;
	}

	@Override
	public void delete(Event record) {
		em.remove(record);
	}

	@Override
	public Event update(Event record) {
		return em.merge(record);
	}

	@Override
	public Event provide(Event record)
			throws Exception {
		Event existingEvent = getEventById(record.getStripeId());
		if (Validator.isNull(existingEvent))
		{
			existingEvent = add(record);
		}
		return existingEvent;
	}

	@Override
	public Event get(long id) {
		return em.getReference(Event.class, id);
	}

	@Override
	public List<Event> getAll() {
		return em.createQuery("select o from Event o record by o.id", Event.class).getResultList();
	}

	@Override
	public Event getByCode(String code) {
		Event rec = null;

		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Event> query = builder.createQuery(Event.class);
			Root<Event> rootEntity = query.from(Event.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("code"), p));

			TypedQuery<Event> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, code);

			rec = typedQuery.getSingleResult();
		} catch (NoResultException e) {
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}

		return rec;
	}
}