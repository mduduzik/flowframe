package org.flowframe.erp.app.contractmanagement.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class CustomSubscriptionPlan extends SubscriptionPlan {
	@ManyToOne
	private Customer tenant;

	public Customer getTenant() {
		return tenant;
	}

	public void setTenant(Customer tenant) {
		this.tenant = tenant;
	}
	
	public CustomSubscriptionPlan() {
		super();
	}	

	public CustomSubscriptionPlan(Customer tenant) {
		super();
		this.tenant = tenant;
	}
}
