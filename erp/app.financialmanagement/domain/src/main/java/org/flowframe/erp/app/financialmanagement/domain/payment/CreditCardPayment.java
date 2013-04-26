package org.flowframe.erp.app.financialmanagement.domain.payment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.flowframe.kernel.common.mdm.domain.geolocation.Address;
import org.flowframe.kernel.common.mdm.domain.organization.Contact;

@Entity
public class CreditCardPayment extends Payment {
	@ManyToOne
	private Contact holderContact;
	
	@ManyToOne
	private Address holderAddress;	
	
	private String hash;
	private Integer number;
	private Integer expMonth;
	private Integer expYear;
	private Integer cvc;
}