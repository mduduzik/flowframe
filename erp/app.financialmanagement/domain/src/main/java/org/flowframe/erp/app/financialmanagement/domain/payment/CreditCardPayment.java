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
	
	public CreditCardPayment(Contact holderContact, Address holderAddress, String hash, Integer number, Integer expMonth, Integer expYear, Integer cvc) {
		super();
		this.holderContact = holderContact;
		this.holderAddress = holderAddress;
		this.hash = hash;
		this.number = number;
		this.expMonth = expMonth;
		this.expYear = expYear;
		this.cvc = cvc;
	}

	public Contact getHolderContact() {
		return holderContact;
	}

	public void setHolderContact(Contact holderContact) {
		this.holderContact = holderContact;
	}

	public Address getHolderAddress() {
		return holderAddress;
	}

	public void setHolderAddress(Address holderAddress) {
		this.holderAddress = holderAddress;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getExpMonth() {
		return expMonth;
	}

	public void setExpMonth(Integer expMonth) {
		this.expMonth = expMonth;
	}

	public Integer getExpYear() {
		return expYear;
	}

	public void setExpYear(Integer expYear) {
		this.expYear = expYear;
	}

	public Integer getCvc() {
		return cvc;
	}

	public void setCvc(Integer cvc) {
		this.cvc = cvc;
	}
}