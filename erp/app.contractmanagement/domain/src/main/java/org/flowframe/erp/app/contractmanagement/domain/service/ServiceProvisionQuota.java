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
import org.flowframe.erp.app.contractmanagement.type.SERVICEQUOTARESETTYPE;
import org.flowframe.erp.app.mdm.domain.enums.ITEMUNIT;
import org.flowframe.erp.app.salesmanagement.domain.rates.CalculatableRate;
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
    
    @Enumerated(EnumType.STRING)
    private SERVICEQUOTARESETTYPE resetType;      
    
	@ManyToOne
    private CalculatableRate overrageRate;    
    
    private Double limitAmount;
    
    private Double totalUsage;    
    
    public ServiceProvisionQuota(){
    	super();
    }

	public ServiceProvisionQuota(SERVICEJOBTYPE type, SERVICEQUOTARESETTYPE resetType, ITEMUNIT unit, Double limit, CalculatableRate overrageRate) {
		super();
		this.type = type;
		this.resetType = resetType;
		this.unit = unit;
		this.limitAmount = limit;
		this.overrageRate = overrageRate;
	}

	public ServiceProvision getService() {
		return service;
	}

	public void setService(ServiceProvision service) {
		this.service = service;
	}

	public SERVICEJOBTYPE getType() {
		return type;
	}

	public void setType(SERVICEJOBTYPE type) {
		this.type = type;
	}

	public ITEMUNIT getUnit() {
		return unit;
	}

	public void setUnit(ITEMUNIT unit) {
		this.unit = unit;
	}

	public SERVICEQUOTARESETTYPE getResetType() {
		return resetType;
	}

	public void setResetType(SERVICEQUOTARESETTYPE resetType) {
		this.resetType = resetType;
	}

	public CalculatableRate getOverrageRate() {
		return overrageRate;
	}

	public void setOverrageRate(CalculatableRate overrageRate) {
		this.overrageRate = overrageRate;
	}

	public Double getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(Double limit) {
		this.limitAmount = limit;
	}

	public Double getTotalUsage() {
		return totalUsage;
	}

	public void setTotalUsage(Double totalUsage) {
		this.totalUsage = totalUsage;
	}
}