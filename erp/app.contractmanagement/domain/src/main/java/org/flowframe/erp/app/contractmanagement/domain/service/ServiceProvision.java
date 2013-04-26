package org.flowframe.erp.app.contractmanagement.domain.service;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.flowframe.erp.app.salesmanagement.domain.rates.CalculatableRate;
import org.flowframe.erp.domain.product.Product;

@Entity
public class ServiceProvision extends Product {
	@OneToMany(cascade = CascadeType.ALL,mappedBy="service",fetch=FetchType.EAGER)
    private Set<ServiceProvisionQuota> quotas = new HashSet<ServiceProvisionQuota>();
	
	@ManyToOne
    private CalculatableRate rate;
}