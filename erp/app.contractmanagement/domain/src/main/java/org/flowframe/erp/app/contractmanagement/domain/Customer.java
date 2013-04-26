package org.flowframe.erp.app.contractmanagement.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.flowframe.kernel.common.mdm.domain.organization.Organization;

@Entity
public class Customer extends Organization {
	@ManyToOne
    private Organization paymentProvider;
	
	private Double accountBalance;
	
	private String activeCard;
}
