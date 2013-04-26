package com.conx.bi.kernel.core.domain.notification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;
import org.flowframe.kernel.common.mdm.domain.organization.ContactGroup;

import com.conx.awbtracker.core.types.EXCEPTIONTYPE;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name = "bisubscribernotificationtemplate")
public class SubscriberNotificationTemplate extends MultitenantBaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8748379950369527076L;

	@Enumerated(EnumType.STRING)
    private EXCEPTIONTYPE exceptionType;

    @Column(length=256)
    private String bodyVmTemplateTitle;    
    
    @Column(length=256)
    private String bodyVmTemplateSignature;     
    
    @Column(length=8192)
    private String bodyVmTemplate;
    
    @OneToOne(targetEntity = ContactGroup.class, fetch = FetchType.EAGER)
    @JoinColumn
    private ContactGroup contactGroup;       


	public EXCEPTIONTYPE getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(EXCEPTIONTYPE exceptionType) {
		this.exceptionType = exceptionType;
	}

	public String getBodyVmTemplateTitle() {
		return bodyVmTemplateTitle;
	}

	public void setBodyVmTemplateTitle(String bodyVmTemplateTitle) {
		this.bodyVmTemplateTitle = bodyVmTemplateTitle;
	}

	public String getBodyVmTemplateSignature() {
		return bodyVmTemplateSignature;
	}

	public void setBodyVmTemplateSignature(String bodyVmTemplateSignature) {
		this.bodyVmTemplateSignature = bodyVmTemplateSignature;
	}

	public String getBodyVmTemplate() {
		return bodyVmTemplate;
	}

	public void setBodyVmTemplate(String bodyVmTemplate) {
		this.bodyVmTemplate = bodyVmTemplate;
	}


	public ContactGroup getContactGroup() {
		return contactGroup;
	}

	public void setContactGroup(ContactGroup contactGroup) {
		this.contactGroup = contactGroup;
	}    
}
