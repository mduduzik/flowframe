package org.flowframe.erp.app.contractmanagement.domain.service;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.flowframe.erp.app.mdm.domain.product.Product;
import org.flowframe.erp.app.salesmanagement.domain.rates.CalculatableRate;

@Entity
public class ServiceProvision extends Product {
	@OneToMany(cascade = CascadeType.ALL,mappedBy="service",fetch=FetchType.EAGER)
    private Set<ServiceProvisionQuota> quotas = new HashSet<ServiceProvisionQuota>();
	
	@ManyToOne
    private CalculatableRate rate;

	public Set<ServiceProvisionQuota> getQuotas() {
		return quotas;
	}

	public void setQuotas(Set<ServiceProvisionQuota> quotas) {
		this.quotas = quotas;
	}

	public CalculatableRate getRate() {
		return rate;
	}

	public void setRate(CalculatableRate rate) {
		this.rate = rate;
	}
}