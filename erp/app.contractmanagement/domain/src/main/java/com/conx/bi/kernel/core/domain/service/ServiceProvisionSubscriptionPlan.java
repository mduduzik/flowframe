package com.conx.bi.kernel.core.domain.service;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name = "biserviceprovisionsubscriptionplan")
public class ServiceProvisionSubscriptionPlan extends BaseEntity {
	private static final long serialVersionUID = -2407263542856901513L;
	@OneToOne
    private ServiceProvisionGroup serviceGroup;
	public ServiceProvisionGroup getServiceGroup() {
		return serviceGroup;
	}
	public void setServiceGroup(ServiceProvisionGroup serviceGroup) {
		this.serviceGroup = serviceGroup;
	}
}