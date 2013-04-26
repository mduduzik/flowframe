package com.conx.bi.kernel.core.domain.service;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name = "biserviceprovisiongroup")
public class ServiceProvisionGroup extends BaseEntity {
	private static final long serialVersionUID = 8879530775716567234L;
	
	@ManyToOne
	private ServiceProvisionGroupRate groupRate;

	@OneToMany(targetEntity = ServiceProvisionGroupService.class,cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<ServiceProvisionGroupService> services = new HashSet<ServiceProvisionGroupService>();

	public ServiceProvisionGroupRate getGroupRate() {
		return groupRate;
	}

	public void setGroupRate(ServiceProvisionGroupRate groupRate) {
		this.groupRate = groupRate;
	}

	public Set<ServiceProvisionGroupService> getServices() {
		return services;
	}

	public void setServices(Set<ServiceProvisionGroupService> services) {
		this.services = services;
	}
}