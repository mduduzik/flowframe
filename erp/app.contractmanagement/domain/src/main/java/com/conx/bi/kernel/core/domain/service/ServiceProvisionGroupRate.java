package com.conx.bi.kernel.core.domain.service;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;
import org.flowframe.kernel.common.mdm.domain.organization.Contact;
import org.flowframe.kernel.common.mdm.domain.organization.ContactGroup;

import com.conx.bi.kernel.core.domain.rates.CalculatableRate;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name = "biserviceprovisiongrouprate")
public class ServiceProvisionGroupRate extends MultitenantBaseEntity {
	@ManyToOne
    private CalculatableRate rate;

    @ManyToOne
    private ServiceProvisionGroupService serviceGroup;

	public CalculatableRate getRate() {
		return rate;
	}

	public void setRate(CalculatableRate rate) {
		this.rate = rate;
	}

	public ServiceProvisionGroupService getServiceGroup() {
		return serviceGroup;
	}

	public void setServiceGroup(ServiceProvisionGroupService serviceGroup) {
		this.serviceGroup = serviceGroup;
	}
}