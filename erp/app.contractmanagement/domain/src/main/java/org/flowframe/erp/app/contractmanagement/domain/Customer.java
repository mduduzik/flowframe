package org.flowframe.erp.app.contractmanagement.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.flowframe.erp.app.financialmanagement.domain.payment.Payment;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;

@Entity
public class Customer extends Organization {
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Subscription subscription;
	
	@OneToMany(targetEntity = Subscription.class,cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<Subscription> previousSubscriptions = new HashSet<Subscription>();	
	
	@ManyToOne
    private Organization paymentProvider;
	
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private Payment activePayment;	
	
	@OneToMany(targetEntity = Payment.class,cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<Payment> previousPayments = new HashSet<Payment>();
	
	private Double accountBalance;
	
	private Boolean delinquent = false;
	
	private Boolean subscribed = false;
	
	public Customer(){
		super();
	}

	public Customer(Organization toCopy) {
		super();
		setCode(toCopy.getCode());
		setName(toCopy.getName());
		setOrganizationId(toCopy.getOrganizationId());
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public Organization getPaymentProvider() {
		return paymentProvider;
	}

	public void setPaymentProvider(Organization paymentProvider) {
		this.paymentProvider = paymentProvider;
	}

	public Payment getActivePayment() {
		return activePayment;
	}

	public void setActivePayment(Payment activePayment) {
		this.activePayment = activePayment;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	public Boolean getDelinquent() {
		return delinquent;
	}

	public void setDelinquent(Boolean delinquent) {
		this.delinquent = delinquent;
	}

	public Set<Payment> getPreviousPayments() {
		return previousPayments;
	}

	public void setPreviousPayments(Set<Payment> previousPayments) {
		this.previousPayments = previousPayments;
	}

	public Set<Subscription> getPreviousSubscriptions() {
		return previousSubscriptions;
	}

	public void setPreviousSubscriptions(Set<Subscription> previousSubscriptions) {
		this.previousSubscriptions = previousSubscriptions;
	}

	public Boolean getSubscribed() {
		return subscribed;
	}

	public void setSubscribed(Boolean subscribed) {
		this.subscribed = subscribed;
	}
}
