package org.flowframe.erp.app.salesmanagement.domain.rates.calculator;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.flowframe.erp.domain.enums.ITEMUNIT;

@Entity
public class BaseRateCalculator extends RateCalculator {
	private static final long serialVersionUID = 4779175153108332764L;

	private Double basePrice;
	
	private Double perUnitPrice;

	
	public BaseRateCalculator() {
		super();
	}

	public BaseRateCalculator(ITEMUNIT unit, Double basePrice, Double perUnitPrice) {
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