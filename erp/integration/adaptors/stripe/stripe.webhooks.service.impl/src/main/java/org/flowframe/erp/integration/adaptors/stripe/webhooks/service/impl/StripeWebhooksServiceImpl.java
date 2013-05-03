package org.flowframe.erp.integration.adaptors.stripe.webhooks.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.flowframe.erp.integration.adaptors.stripe.webhooks.service.IRemoteStripeWebHookService;
import org.flowframe.kernel.common.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stripe.model.Event;

@Transactional
@Service
public class StripeWebhooksServiceImpl implements IRemoteStripeWebHookService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public int processEvent(String eventInJson) {
		
		int httpResponseStatus = 302;//Found
		
		Event evt = Event.gson.fromJson(eventInJson, Event.class);
		org.flowframe.erp.integration.adaptors.stripe.domain.event.Event ffEvent = getEventById(evt.getId());
		if (Validator.isNull(ffEvent)) {
			ffEvent = toFFEvent(evt,eventInJson);
			ffEvent = add(ffEvent);
		}
		else
		{
			if (ffEvent.getActionedWithSuccess() && ffEvent.getDateResponsedWithSuccess() == null)
			{
				ffEvent.setDateResponsedWithSuccess(new Date());
				ffEvent = update(ffEvent);
				httpResponseStatus = 200;//OK
			}
		}

		return httpResponseStatus;
	}

	private org.flowframe.erp.integration.adaptors.stripe.domain.event.Event toFFEvent(Event evt, String eventInJson) {
		org.flowframe.erp.integration.adaptors.stripe.domain.event.Event ffEvent = new org.flowframe.erp.integration.adaptors.stripe.domain.event.Event();
		ffEvent.setStripeId(evt.getId());
		ffEvent.setEventType(evt.getType());
		ffEvent.setActive(true);
		ffEvent.setLivemode(evt.getLivemode());
		ffEvent.setBody(eventInJson);
		return ffEvent;
	}

	@Override
	public void greenLightEvent(String eventId) {
		org.flowframe.erp.integration.adaptors.stripe.domain.event.Event existingEvent = getEventById(eventId);
		existingEvent.setActionedWithSuccess(true);
		existingEvent.setDateResponseSuccessReady(new Date());
		existingEvent = update(existingEvent);
	}

	@Override
	public List<org.flowframe.erp.integration.adaptors.stripe.domain.event.Event> getAllActiveEvents() {
		return em.createQuery("select o from org.flowframe.erp.integration.adaptors.stripe.domain.event.Event o record by o.id", org.flowframe.erp.integration.adaptors.stripe.domain.event.Event.class).getResultList();
	}
	
	@Override
	public org.flowframe.erp.integration.adaptors.stripe.domain.event.Event getEventById(String eventId) {
		org.flowframe.erp.integration.adaptors.stripe.domain.event.Event org = null;

		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<org.flowframe.erp.integration.adaptors.stripe.domain.event.Event> query = builder.createQuery(org.flowframe.erp.integration.adaptors.stripe.domain.event.Event.class);
			Root<org.flowframe.erp.integration.adaptors.stripe.domain.event.Event> rootEntity = query.from(org.flowframe.erp.integration.adaptors.stripe.domain.event.Event.class);
			ParameterExpression<String> p = builder.parameter(String.class);
			query.select(rootEntity).where(builder.equal(rootEntity.get("stripeId"), p));

			TypedQuery<org.flowframe.erp.integration.adaptors.stripe.domain.event.Event> typedQuery = em.createQuery(query);
			typedQuery.setParameter(p, eventId);

			org = typedQuery.getSingleResult();
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
	public org.flowframe.erp.integration.adaptors.stripe.domain.event.Event add(org.flowframe.erp.integration.adaptors.stripe.domain.event.Event record) {
		record = em.merge(record);
		return record;
	}

	@Override
	public void delete(org.flowframe.erp.integration.adaptors.stripe.domain.event.Event record) {
		em.remove(record);
	}

	@Override
	public org.flowframe.erp.integration.adaptors.stripe.domain.event.Event update(org.flowframe.erp.integration.adaptors.stripe.domain.event.Event record) {
		return em.merge(record);
	}

	@Override
	public org.flowframe.erp.integration.adaptors.stripe.domain.event.Event provide(org.flowframe.erp.integration.adaptors.stripe.domain.event.Event record)
			throws Exception {
		org.flowframe.erp.integration.adaptors.stripe.domain.event.Event existingEvent = getEventById(record.getStripeId());
		if (Validator.isNull(existingEvent))
		{
			existingEvent = add(record);
		}
		return existingEvent;
	}
}