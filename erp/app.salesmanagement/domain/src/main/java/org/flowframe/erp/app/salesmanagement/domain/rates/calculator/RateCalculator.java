package org.flowframe.erp.app.salesmanagement.domain.rates.calculator;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.flowframe.erp.domain.enums.ITEMUNIT;
import org.flowframe.kernel.common.mdm.domain.BaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "fferpsalesratecalculator")
public abstract class RateCalculator extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private ITEMUNIT unit;
    
	public RateCalculator() {
		super();
	}
    
    
	public RateCalculator(ITEMUNIT unit) {
		super();
		this.unit = unit;
	}

	public ITEMUNIT getUnit() {
		return unit;
	}
	
	public void setUnit(ITEMUNIT unit) {
		this.unit = unit;
	}   
	
	@Transient
	public abstract Double calculate(Double amount);
}