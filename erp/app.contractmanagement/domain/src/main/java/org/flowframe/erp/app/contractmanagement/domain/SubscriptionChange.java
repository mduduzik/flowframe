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

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "fferpsubscriptionchange")
public class SubscriptionChange extends BaseEntity {
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
}