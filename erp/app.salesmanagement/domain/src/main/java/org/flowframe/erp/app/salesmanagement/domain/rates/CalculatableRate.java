package org.flowframe.erp.app.salesmanagement.domain.rates;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.flowframe.erp.app.salesmanagement.domain.rates.calculator.BaseRateCalculator;
import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.mdm.domain.currency.CurrencyUnit;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "fferpsalesrate")
public class CalculatableRate extends BaseEntity {
	@ManyToOne
	private CurrencyUnit currency;
	
	private Double price;
	
	private Double discount = 0.0;
	
    @ManyToOne
    private BaseRateCalculator calculator;		
		
	
	public CurrencyUnit getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyUnit currency) {
		this.currency = currency;
	}
	
	public BaseRateCalculator getCalculator() {
		return calculator;
	}

	public void setCalculator(BaseRateCalculator calculator) {
		this.calculator = calculator;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double calculatePrice(Double amount) {
		return calculator.calculate(amount);
	}
}