package org.flowframe.erp.app.contractmanagement.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.flowframe.erp.app.contractmanagement.type.SUBSCRIPTIONSTATUS;
import org.flowframe.erp.app.financialmanagement.domain.payment.CreditCardPayment;
import org.flowframe.kernel.common.mdm.domain.BaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "fferpsubscription")
public class Subscription extends BaseEntity {
	@ManyToOne
	private SubscriptionPlan subscribedPlan;

	@ManyToOne
	private Customer customer;
	
	@OneToOne(cascade=CascadeType.ALL)
	private CreditCardPayment payment;
	
	@OneToMany(targetEntity = SubscriptionChange.class,mappedBy="subscription",cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<SubscriptionChange> changes = new HashSet<SubscriptionChange>();
	
	private Integer quantity = 1;
	
    @Enumerated(EnumType.STRING)
    private SUBSCRIPTIONSTATUS status;
	
	private Boolean cancelAtPeriodEnd = false;

	private Date start;
	
	private Date cancelAt;
	
	private Date currentPeriodStart;
	
	private Date currentPeriodEnd;
	
	private Date endedAt;
	
	private Date trialStart;
	
	private Date trialEnd;
	
	public Subscription() {
		this.status = SUBSCRIPTIONSTATUS.ACTIVE;
	}	

	public Subscription(SubscriptionPlan subscribedPlan, Customer customer) {
		this();
		this.customer = customer;
		this.subscribedPlan = subscribedPlan;
	}

	public SubscriptionPlan getSubscribedPlan() {
		return subscribedPlan;
	}

	public void setSubscribedPlan(SubscriptionPlan subscribedPlan) {
		this.subscribedPlan = subscribedPlan;
	}

	public CreditCardPayment getPayment() {
		return payment;
	}

	public void setPayment(CreditCardPayment payment) {
		this.payment = payment;
	}

	public Set<SubscriptionChange> getChanges() {
		return changes;
	}

	public void setChanges(Set<SubscriptionChange> changes) {
		this.changes = changes;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public SUBSCRIPTIONSTATUS getStatus() {
		return status;
	}

	public void setStatus(SUBSCRIPTIONSTATUS status) {
		this.status = status;
	}

	public Boolean getCancelAtPeriodEnd() {
		return cancelAtPeriodEnd;
	}

	public void setCancelAtPeriodEnd(Boolean cancelAtPeriodEnd) {
		this.cancelAtPeriodEnd = cancelAtPeriodEnd;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getCancelAt() {
		return cancelAt;
	}

	public void setCancelAt(Date cancelAt) {
		this.cancelAt = cancelAt;
	}

	public Date getCurrentPeriodStart() {
		return currentPeriodStart;
	}

	public void setCurrentPeriodStart(Date currentPeriodStart) {
		this.currentPeriodStart = currentPeriodStart;
	}

	public Date getCurrentPeriodEnd() {
		return currentPeriodEnd;
	}

	public void setCurrentPeriodEnd(Date currentPeriodEnd) {
		this.currentPeriodEnd = currentPeriodEnd;
	}

	public Date getEndedAt() {
		return endedAt;
	}

	public void setEndedAt(Date endedAt) {
		this.endedAt = endedAt;
	}

	public Date getTrialStart() {
		return trialStart;
	}

	public void setTrialStart(Date trialStart) {
		this.trialStart = trialStart;
	}

	public Date getTrialEnd() {
		return trialEnd;
	}

	public void setTrialEnd(Date trialEnd) {
		this.trialEnd = trialEnd;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}