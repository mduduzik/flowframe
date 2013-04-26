package com.conx.bi.kernel.core.domain.rates.calculator;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;

import com.conx.bi.kernel.core.enums.CALCULATORUNIT;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "biratecalculator")
public abstract class RateCalculator extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private CALCULATORUNIT unit;
    
	public RateCalculator() {
		super();
	}
    
    
	public RateCalculator(CALCULATORUNIT unit) {
		super();
		this.unit = unit;
	}

	public CALCULATORUNIT getUnit() {
		return unit;
	}
	
	public void setUnit(CALCULATORUNIT unit) {
		this.unit = unit;
	}   
	
	@Transient
	public abstract Double calculate(Double amount);
}