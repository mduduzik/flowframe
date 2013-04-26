package com.conx.bi.kernel.core.domain.service;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;

import com.conx.bi.kernel.core.enums.CALCULATORUNIT;
import com.conx.bi.kernel.core.enums.SERVICEJOBTYPE;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name = "biserviceprovisionquota")
public class ServiceProvisionQuota extends BaseEntity {
	@OneToOne
	private ServiceProvision service;
	
    @Enumerated(EnumType.STRING)
    private SERVICEJOBTYPE type;
    
    @Enumerated(EnumType.STRING)
    private CALCULATORUNIT unit;  
    
    private Double amount;
    
    public ServiceProvisionQuota(){
    	super();
    }

	public ServiceProvisionQuota(SERVICEJOBTYPE type, CALCULATORUNIT unit, Double amount) {
		super();
		this.type = type;
		this.unit = unit;
		this.amount = amount;
	}
}