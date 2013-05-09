package org.flowframe.erp.integration.adaptors.stripe.webhooks.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

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
import com.stripe.model.StripeObject;

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
				ffEvent.setProcessingTries(ffEvent.getProcessingTries()+1);
				ffEvent.setDateLastTried(new Date());					
				if (ffEvent.getActionedWithSuccess() && ffEvent.getDateResponsedWithSuccess() == null)
				{
					ffEvent.setDateResponsedWithSuccess(new Date());
					ffEvent = eventDAoService.update(ffEvent);
					httpResponseStatus = 200;//OK
				}
				else
				{
					ffEvent.setLastReturnCode(httpResponseStatus);
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
		Object objectId = getIdString(evt.getData().getObject());
		Object objectType = getTypeString(evt.getData().getObject());
		ffEvent.setObjectId(objectId.toString());
		ffEvent.setObjectType(objectType.toString());
		ffEvent.setCode(evt.getId());
		ffEvent.setStripeId(evt.getId());
		ffEvent.setEventType(evt.getType());
		ffEvent.setEventCreated(new Date(evt.getCreated()));
		ffEvent.setActive(true);
		ffEvent.setLivemode(evt.getLivemode());
		ffEvent.setBody(eventInJson);
		ffEvent.setDateCreated(new Date());
		return ffEvent;
	}
	
	private Object getIdString(StripeObject so) {
		try {
			Method getIdMethod = so.getClass().getDeclaredMethod("getId");
			return getIdMethod.invoke(so);
		} catch (SecurityException e) {
			return "";
		} catch (NoSuchMethodException e) {
			return "";
		} catch (InvocationTargetException e) {
			return "";
		} catch (IllegalAccessException e) {
			return "";
		}
	}
	
	private Object getTypeString(StripeObject so) {
		try {
			return so.getClass().getSimpleName().toLowerCase();
		} catch (SecurityException e) {
			return "";
		}
	}	

	@Override
	public void greenLightEvent(String eventId) {
		org.flowframe.erp.integration.adaptors.stripe.domain.event.Event existingEvent = eventDAoService.getEventById(eventId);
		existingEvent.setActionedWithSuccess(true);
		existingEvent.setDateResponseSuccessReady(new Date());
		existingEvent = eventDAoService.update(existingEvent);
	}

	@Override
	public void markInactive(String eventId) throws Exception {
		org.flowframe.erp.integration.adaptors.stripe.domain.event.Event existingEvent = eventDAoService.getEventById(eventId);
		existingEvent.setActive(false);
		existingEvent = eventDAoService.update(existingEvent);
	}
	
	@Override
	public void markActive(String eventId) throws Exception {
		org.flowframe.erp.integration.adaptors.stripe.domain.event.Event existingEvent = eventDAoService.getEventById(eventId);
		existingEvent.setActive(false);
		existingEvent = eventDAoService.update(existingEvent);
	}	
}