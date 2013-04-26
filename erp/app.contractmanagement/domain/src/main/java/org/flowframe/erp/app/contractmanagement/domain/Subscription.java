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
}