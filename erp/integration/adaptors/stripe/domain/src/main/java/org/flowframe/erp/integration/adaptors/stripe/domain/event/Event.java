package org.flowframe.erp.integration.adaptors.stripe.domain.event;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;


@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name="ffmsgstripeevent")
public class Event extends MultitenantBaseEntity {
    @Column(length=8192)
    private String body;
    
    private String stripeId;
    
    private String eventType;
    
    private Integer pendingWebhooks;
    
    private Date dateLastTried;
    
    private Date dateResponseSuccessReady;
    
    private Date dateResponsedWithSuccess;
    
    private Boolean actionedWithSuccess = false;
    
    private Integer processingTries = 0;
    
    private Date eventCreated;
    private Boolean livemode = false;
    
    public Event(){
    }

	public Event(String body, String stripeId, String eventType, Integer pendingWebhooks) {
		super();
		this.body = body;
		this.stripeId = stripeId;
		this.eventType = eventType;
		this.pendingWebhooks = pendingWebhooks;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getStripeId() {
		return stripeId;
	}

	public void setStripeId(String stripeId) {
		this.stripeId = stripeId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Integer getPendingWebhooks() {
		return pendingWebhooks;
	}

	public void setPendingWebhooks(Integer pendingWebhooks) {
		this.pendingWebhooks = pendingWebhooks;
	}

	public Date getDateLastTried() {
		return dateLastTried;
	}

	public void setDateLastTried(Date dateLastTried) {
		this.dateLastTried = dateLastTried;
	}

	public Date getDateResponseSuccessReady() {
		return dateResponseSuccessReady;
	}

	public void setDateResponseSuccessReady(Date dateResponseSuccessReady) {
		this.dateResponseSuccessReady = dateResponseSuccessReady;
	}

	public Boolean getActionedWithSuccess() {
		return actionedWithSuccess;
	}

	public void setActionedWithSuccess(Boolean actionedWithSuccess) {
		this.actionedWithSuccess = actionedWithSuccess;
	}

	public Date getEventCreated() {
		return eventCreated;
	}

	public void setEventCreated(Date eventCreated) {
		this.eventCreated = eventCreated;
	}

	public Boolean getLivemode() {
		return livemode;
	}

	public void setLivemode(Boolean livemode) {
		this.livemode = livemode;
	}

	public Date getDateResponsedWithSuccess() {
		return dateResponsedWithSuccess;
	}

	public void setDateResponsedWithSuccess(Date dateResponsedWithSuccess) {
		this.dateResponsedWithSuccess = dateResponsedWithSuccess;
	}

	public Integer getProcessingTries() {
		return processingTries;
	}

	public void setProcessingTries(Integer processingTries) {
		this.processingTries = processingTries;
	}
}
