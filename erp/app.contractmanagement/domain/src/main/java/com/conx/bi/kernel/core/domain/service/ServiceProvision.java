package com.conx.bi.kernel.core.domain.service;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;

import com.conx.bi.kernel.core.domain.tenant.subscription.ServiceSubscription;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "biserviceprovision")
public class ServiceProvision extends BaseEntity {
	@OneToMany(cascade = CascadeType.ALL,mappedBy="service",fetch=FetchType.EAGER)
    private Set<ServiceSubscription> subscriptions = new HashSet<ServiceSubscription>();
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="service",fetch=FetchType.EAGER)
    private Set<ServiceProvisionQuota> quotas = new HashSet<ServiceProvisionQuota>();	
}