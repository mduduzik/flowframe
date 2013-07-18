package org.flowframe.erp.app.contractmanagement.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.flowframe.erp.app.contractmanagement.type.SUBSCRIPTIONCHANGETYPE;
import org.flowframe.erp.app.contractmanagement.type.SUBSCRIPTIONSTATUS;
import org.flowframe.erp.app.financialmanagement.domain.payment.CreditCardPayment;
import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "fferpsubscriptionchange")
public class SubscriptionChange extends MultitenantBaseEntity {
	@ManyToOne
	private Subscription subscription;
	
	@ManyToOne
	private CreditCardPayment payment;	
	
	@ManyToOne
	private SubscriptionPlan changeToPlan;

    @Enumerated(EnumType.STRING)
    private SUBSCRIPTIONCHANGETYPE changeType;
    
    private Date start;
    private SUBSCRIPTIONSTATUS status;
	public Subscription getSubscription() {
		return subscription;
	}
	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}
	public CreditCardPayment getPayment() {
		return payment;
	}
	public void setPayment(CreditCardPayment payment) {
		this.payment = payment;
	}
	public SubscriptionPlan getChangeToPlan() {
		return changeToPlan;
	}
	public void setChangeToPlan(SubscriptionPlan changeToPlan) {
		this.changeToPlan = changeToPlan;
	}
	public SUBSCRIPTIONCHANGETYPE getChangeType() {
		return changeType;
	}
	public void setChangeType(SUBSCRIPTIONCHANGETYPE changeType) {
		this.changeType = changeType;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public SUBSCRIPTIONSTATUS getStatus() {
		return status;
	}
	public void setStatus(SUBSCRIPTIONSTATUS status) {
		this.status = status;
	}
	
	public SubscriptionChange(){
	}

	public SubscriptionChange(Subscription subscription, CreditCardPayment payment, SubscriptionPlan changeToPlan, SUBSCRIPTIONCHANGETYPE changeType, Date start,
			SUBSCRIPTIONSTATUS status) {
		super();
		this.subscription = subscription;
		
		super.setTenant(this.subscription.getCustomer());
		
		this.payment = payment;
		this.changeToPlan = changeToPlan;
		this.changeType = changeType;
		this.start = start;
		this.status = status;
	}   
}