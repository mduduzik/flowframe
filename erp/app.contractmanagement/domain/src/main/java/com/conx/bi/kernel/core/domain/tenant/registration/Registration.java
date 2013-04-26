package com.conx.bi.kernel.core.domain.tenant.registration;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.flowframe.kernel.common.mdm.domain.user.User;

import com.conx.bi.kernel.core.domain.notification.SubscriberEmailNotification;
import com.conx.bi.kernel.core.domain.tenant.TenantUser;
import com.conx.bi.kernel.core.enums.NOTIFICATIONSTATUS;
import com.conx.bi.kernel.core.enums.REGISTRATIONSTATUS;


@Entity
@Table(name = "bitenantregistration")
public class Registration extends MultitenantBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6025936980994121721L;
	
	private String emailAddress;
	private String organizationName;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	
    @ManyToOne(fetch=FetchType.EAGER)
    private User tenantUser;
    
    @ManyToOne(fetch=FetchType.EAGER)
    private SubscriberEmailNotification tenantUserNotification;    
	
    @Enumerated(EnumType.STRING)
    private REGISTRATIONSTATUS status;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public User getTenantUser() {
		return tenantUser;
	}

	public void setTenantUser(User tenantUser) {
		this.tenantUser = tenantUser;
	}

	public SubscriberEmailNotification getTenantUserNotification() {
		return tenantUserNotification;
	}

	public void setTenantUserNotification(
			SubscriberEmailNotification tenantUserNotification) {
		this.tenantUserNotification = tenantUserNotification;
	}

	public REGISTRATIONSTATUS getStatus() {
		return status;
	}

	public void setStatus(REGISTRATIONSTATUS status) {
		this.status = status;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
}
