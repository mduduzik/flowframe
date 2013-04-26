package com.conx.bi.kernel.core.domain.service;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;

import com.conx.bi.kernel.core.domain.rates.CalculatableRate;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name = "biserviceprovisionrate")
public class ServiceProvisionRate extends MultitenantBaseEntity {
	@ManyToOne
    private CalculatableRate rate;

    @ManyToOne
    private ServiceProvision service;

	public CalculatableRate getRate() {
		return rate;
	}

	public void setRate(CalculatableRate rate) {
		this.rate = rate;
	}

	public ServiceProvision getService() {
		return service;
	}

	public void setService(ServiceProvision service) {
		this.service = service;
	}
}