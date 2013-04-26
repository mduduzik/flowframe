package org.flowframe.erp.app.contractmanagement.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
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
    private INTERVALTYPE interval;
    
    private int intervalCount;
    
	@ManyToOne
    private CalculatableRate rate;   
	
	
	@ManyToOne
    private Organization paymentProvider;  
	
	
	private int trialPeriodDays;
}