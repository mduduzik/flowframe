package org.flowframe.erp.app.contractmanagement.domain;

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

import org.flowframe.erp.app.contractmanagement.domain.service.ServiceProvisionGroup;
import org.flowframe.erp.app.contractmanagement.type.INTERVALTYPE;
import org.flowframe.erp.app.salesmanagement.domain.rates.CalculatableRate;
import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "fferpsubscriptionplan")
public class SubscriptionPlan extends BaseEntity {
	private static final long serialVersionUID = -3112539146151123146L;
	
	@OneToOne
    private ServiceProvisionGroup serviceGroup;	

    @Enumerated(EnumType.STRING)
    private INTERVALTYPE intervalType;
    
    private int intervalCount;
    
	@ManyToOne(cascade=CascadeType.ALL)
    private CalculatableRate rate;   
	
	
	@ManyToOne
    private Organization paymentProvider;  
	
	@OneToMany(targetEntity = Subscription.class,mappedBy="subscribedPlan",fetch=FetchType.EAGER)
    private Set<Subscription> subscriptions = new HashSet<Subscription>();	
	
	
	private int trialPeriodDays;


	public ServiceProvisionGroup getServiceGroup() {
		return serviceGroup;
	}


	public void setServiceGroup(ServiceProvisionGroup serviceGroup) {
		this.serviceGroup = serviceGroup;
	}


	public INTERVALTYPE getIntervalType() {
		return intervalType;
	}


	public void setIntervalType(INTERVALTYPE interval) {
		this.intervalType = interval;
	}


	public int getIntervalCount() {
		return intervalCount;
	}


	public void setIntervalCount(int intervalCount) {
		this.intervalCount = intervalCount;
	}


	public CalculatableRate getRate() {
		return rate;
	}


	public void setRate(CalculatableRate rate) {
		this.rate = rate;
	}


	public Organization getPaymentProvider() {
		return paymentProvider;
	}


	public void setPaymentProvider(Organization paymentProvider) {
		this.paymentProvider = paymentProvider;
	}


	public int getTrialPeriodDays() {
		return trialPeriodDays;
	}


	public void setTrialPeriodDays(int trialPeriodDays) {
		this.trialPeriodDays = trialPeriodDays;
	}


	public Set<Subscription> getSubscriptions() {
		return subscriptions;
	}


	public void setSubscriptions(Set<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}
}