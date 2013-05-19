package org.flowframe.kernel.eventmanagement.springdm.services.impl;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminEventHandler implements EventHandler {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	
	@Override
	public void handleEvent(Event event) {
	    String topicName = event.getTopic();
	    StringBuilder sb = new StringBuilder();
	    sb.append("handleEvent: topicName="+topicName+";");
	    String[] propertyNames = event.getPropertyNames();
	    for (int cpt=0; cpt<propertyNames.length; cpt++) {
	      String propertyName = propertyNames[cpt];
	      Object propertyValue = event.getProperty(propertyName);
	      sb.append(";"+propertyName+"="+propertyValue);
	    }	
	    logger.info(sb.toString());
	}
}
