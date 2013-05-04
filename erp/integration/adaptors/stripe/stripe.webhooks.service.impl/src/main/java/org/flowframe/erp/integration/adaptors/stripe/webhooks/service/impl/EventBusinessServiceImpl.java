package org.flowframe.erp.integration.adaptors.stripe.webhooks.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.flowframe.erp.integration.adaptors.stripe.services.IEventBusinessService;
import org.flowframe.erp.integration.adaptors.stripe.services.IEventDAOService;
import org.flowframe.kernel.common.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonSyntaxException;
import com.stripe.model.Event;

@Transactional
@Service
public class EventBusinessServiceImpl implements IEventBusinessService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	@Autowired
	private IEventDAOService eventDAoService;
	
	@Override
	public int processEvent(String eventInJson) {
		
		int httpResponseStatus = 302;//Found
		
		try {
			Event evt = Event.gson.fromJson(eventInJson, Event.class);
			org.flowframe.erp.integration.adaptors.stripe.domain.event.Event ffEvent = eventDAoService.getEventById(evt.getId());
			if (Validator.isNull(ffEvent)) {
				ffEvent = toFFEvent(evt,eventInJson);
				ffEvent = eventDAoService.add(ffEvent);
			}
			else
			{
				if (ffEvent.getActionedWithSuccess() && ffEvent.getDateResponsedWithSuccess() == null)
				{
					ffEvent.setDateResponsedWithSuccess(new Date());
					ffEvent = eventDAoService.update(ffEvent);
					httpResponseStatus = 200;//OK
				}
				else
				{
					ffEvent.setProcessingTries(ffEvent.getProcessingTries()+1);
					ffEvent.setDateLastTried(new Date());
					ffEvent = eventDAoService.update(ffEvent);
				}
			}
		} catch (JsonSyntaxException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			logger.error(stacktrace);
		}

		return httpResponseStatus;
	}

	private org.flowframe.erp.integration.adaptors.stripe.domain.event.Event toFFEvent(Event evt, String eventInJson) {
		org.flowframe.erp.integration.adaptors.stripe.domain.event.Event ffEvent = new org.flowframe.erp.integration.adaptors.stripe.domain.event.Event();
		ffEvent.setCode(evt.getId());
		ffEvent.setStripeId(evt.getId());
		ffEvent.setEventType(evt.getType());
		ffEvent.setActive(true);
		ffEvent.setLivemode(evt.getLivemode());
		ffEvent.setBody(eventInJson);
		return ffEvent;
	}

	@Override
	public void greenLightEvent(String eventId) {
		org.flowframe.erp.integration.adaptors.stripe.domain.event.Event existingEvent = eventDAoService.getEventById(eventId);
		existingEvent.setActionedWithSuccess(true);
		existingEvent.setDateResponseSuccessReady(new Date());
		existingEvent = eventDAoService.update(existingEvent);
	}
}