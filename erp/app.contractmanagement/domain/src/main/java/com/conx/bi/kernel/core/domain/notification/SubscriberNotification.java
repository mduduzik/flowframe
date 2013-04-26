package com.conx.bi.kernel.core.domain.notification;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;
import org.flowframe.kernel.common.mdm.domain.organization.ContactGroup;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;

import com.conx.bi.kernel.core.enums.NOTIFICATIONSTATUS;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "bisubscribernotification")
@DiscriminatorColumn(discriminatorType=DiscriminatorType.STRING,length=128)
public abstract class SubscriberNotification extends MultitenantBaseEntity {
    @ManyToOne(fetch=FetchType.EAGER)
    private Organization subscriber;	
    
    @ManyToOne(fetch=FetchType.EAGER)
    private ContactGroup contactGroup;	   
    
    @Enumerated(EnumType.STRING)
    private NOTIFICATIONSTATUS status;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSent;	
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFailed;    
    
    @Column(length=8192)
    private String body;

	public Organization getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(Organization subscriber) {
		this.subscriber = subscriber;
	}

	public ContactGroup getContactGroup() {
		return contactGroup;
	}

	public void setContactGroup(ContactGroup contactGroup) {
		this.contactGroup = contactGroup;
	}

	public NOTIFICATIONSTATUS getStatus() {
		return status;
	}

	public void setStatus(NOTIFICATIONSTATUS status) {
		this.status = status;
	}

	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	public Date getDateFailed() {
		return dateFailed;
	}

	public void setDateFailed(Date dateFailed) {
		this.dateFailed = dateFailed;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}        
}
