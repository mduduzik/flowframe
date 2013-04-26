package com.conx.bi.kernel.core.domain.rates.calculator;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.conx.bi.kernel.core.enums.CALCULATORUNIT;

@Entity
public class BaseRateCalculator extends RateCalculator {
	private Double basePrice;
	
	private Double perUnitPrice;

	
	public BaseRateCalculator() {
		super();
	}

	public BaseRateCalculator(CALCULATORUNIT unit, Double basePrice, Double perUnitPrice) {
		super(unit);
		this.basePrice = basePrice;
		this.perUnitPrice = perUnitPrice;
	}
	

	public Double getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}


	public Double getPerUnitPrice() {
		return perUnitPrice;
	}
	public void setPerUnitPrice(Double perUnitPrice) {
		this.perUnitPrice = perUnitPrice;
	}

	@Transient
	@Override
	public Double calculate(Double amount) {
		return basePrice + amount*perUnitPrice;
	}
}