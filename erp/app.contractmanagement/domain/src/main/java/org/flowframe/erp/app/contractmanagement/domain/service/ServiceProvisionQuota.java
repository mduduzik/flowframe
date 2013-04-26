package org.flowframe.erp.app.contractmanagement.domain.service;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.flowframe.erp.app.contractmanagement.type.SERVICEJOBTYPE;
import org.flowframe.erp.app.salesmanagement.domain.rates.CalculatableRate;
import org.flowframe.erp.domain.enums.ITEMUNIT;
import org.flowframe.kernel.common.mdm.domain.BaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name = "fferpserviceprovisionquota")
public class ServiceProvisionQuota extends BaseEntity {
	@OneToOne
	private ServiceProvision service;
	
    @Enumerated(EnumType.STRING)
    private SERVICEJOBTYPE type;
    
    @Enumerated(EnumType.STRING)
    private ITEMUNIT unit;  
    
	@ManyToOne
    private CalculatableRate overrageRate;    
    
    private Double limit;
    
    private Double totalUsage;    
    
    public ServiceProvisionQuota(){
    	super();
    }

	public ServiceProvisionQuota(SERVICEJOBTYPE type, ITEMUNIT unit, Double limit, CalculatableRate overrageRate) {
		super();
		this.type = type;
		this.unit = unit;
		this.limit = limit;
		this.overrageRate = overrageRate;
	}
}