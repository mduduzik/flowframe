package org.flowframe.erp.app.financialmanagement.domain.receivable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.flowframe.erp.app.financialmanagement.domain.ReceiptLine;
import org.flowframe.erp.app.financialmanagement.domain.ReceiptPeriod;
import org.flowframe.erp.app.mdm.domain.currency.CurrencyUnit;
import org.flowframe.erp.app.mdm.domain.product.Product;

@Entity
public class ARReceiptLine extends ReceiptLine {
	@ManyToOne
	private ReceiptPeriod period;	
	
	@ManyToOne
	private Product product;
	
	@ManyToOne
	CurrencyUnit currency;	
	
	String lineType;
	Boolean livemode;
	Integer amount;

	Boolean proration;
	Integer quantity;
	public ReceiptPeriod getPeriod() {
		return period;
	}
	public void setPeriod(ReceiptPeriod period) {
		this.period = period;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public String getLineType() {
		return lineType;
	}
	public void setLineType(String lineType) {
		this.lineType = lineType;
	}
	public Boolean getLivemode() {
		return livemode;
	}
	public void setLivemode(Boolean livemode) {
		this.livemode = livemode;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public CurrencyUnit getCurrency() {
		return currency;
	}
	public void setCurrency(CurrencyUnit currency) {
		this.currency = currency;
	}
	public Boolean getProration() {
		return proration;
	}
	public void setProration(Boolean proration) {
		this.proration = proration;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public ARReceiptLine(ARReceipt receipt, Product product, String externalRefId, String lineType, Boolean livemode, Integer amount, CurrencyUnit currency, Boolean proration,
			Integer quantity, String description) {
		super();
		this.product = product;
		this.externalRefId = externalRefId;
		this.lineType = lineType;
		this.livemode = livemode;
		this.amount = amount;
		this.currency = currency;
		this.proration = proration;
		this.quantity = quantity;
		this.description = description;
		this.setReceipt(receipt);
	}
}