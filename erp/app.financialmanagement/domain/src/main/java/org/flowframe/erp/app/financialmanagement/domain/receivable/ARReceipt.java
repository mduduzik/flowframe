package org.flowframe.erp.app.financialmanagement.domain.receivable;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.flowframe.erp.app.financialmanagement.domain.Receipt;
import org.flowframe.erp.app.mdm.domain.currency.CurrencyUnit;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;

@Entity
public class ARReceipt extends Receipt {
	@ManyToOne
	private Organization debtor;
	
	@ManyToOne
	private CurrencyUnit currency;
	
	Integer subtotal;
	Integer total;
	Integer amountDue;
	Integer startingBalance;
	Integer endingBalance;
	Date nextPaymentAttempt;
	Boolean attempted;
	String charge;
	Boolean closed;
	String customer;
	Date date;
	Boolean paid;
	Date periodStart;
	Date periodEnd;
	Boolean livemode;
	Integer attemptCount;
	
	public ARReceipt(){
	}
	
	public ARReceipt(Organization debtor, CurrencyUnit currency, Integer subtotal, Integer total, Integer amountDue, Integer startingBalance, Integer endingBalance,
			String externalRefId, Date nextPaymentAttempt, Boolean attempted, String charge, Boolean closed, String customer, Date date, Boolean paid, Date periodStart,
			Date periodEnd, Boolean livemode, Integer attemptCount) {
		super();
		this.debtor = debtor;
		this.currency = currency;
		this.subtotal = subtotal;
		this.total = total;
		this.amountDue = amountDue;
		this.startingBalance = startingBalance;
		this.endingBalance = endingBalance;
		this.externalRefId = externalRefId;
		this.nextPaymentAttempt = nextPaymentAttempt;
		this.attempted = attempted;
		this.charge = charge;
		this.closed = closed;
		this.customer = customer;
		this.date = date;
		this.paid = paid;
		this.periodStart = periodStart;
		this.periodEnd = periodEnd;
		this.livemode = livemode;
		this.attemptCount = attemptCount;
	}
	public Organization getDebtor() {
		return debtor;
	}
	public void setDebtor(Organization debtor) {
		this.debtor = debtor;
	}
	public CurrencyUnit getCurrency() {
		return currency;
	}
	public void setCurrency(CurrencyUnit currency) {
		this.currency = currency;
	}
	public Integer getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Integer subtotal) {
		this.subtotal = subtotal;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getAmountDue() {
		return amountDue;
	}
	public void setAmountDue(Integer amountDue) {
		this.amountDue = amountDue;
	}
	public Integer getStartingBalance() {
		return startingBalance;
	}
	public void setStartingBalance(Integer startingBalance) {
		this.startingBalance = startingBalance;
	}
	public Integer getEndingBalance() {
		return endingBalance;
	}
	public void setEndingBalance(Integer endingBalance) {
		this.endingBalance = endingBalance;
	}

	public Date getNextPaymentAttempt() {
		return nextPaymentAttempt;
	}
	public void setNextPaymentAttempt(Date nextPaymentAttempt) {
		this.nextPaymentAttempt = nextPaymentAttempt;
	}
	public Boolean getAttempted() {
		return attempted;
	}
	public void setAttempted(Boolean attempted) {
		this.attempted = attempted;
	}
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	public Boolean getClosed() {
		return closed;
	}
	public void setClosed(Boolean closed) {
		this.closed = closed;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Boolean getPaid() {
		return paid;
	}
	public void setPaid(Boolean paid) {
		this.paid = paid;
	}
	public Date getPeriodStart() {
		return periodStart;
	}
	public void setPeriodStart(Date periodStart) {
		this.periodStart = periodStart;
	}
	public Date getPeriodEnd() {
		return periodEnd;
	}
	public void setPeriodEnd(Date periodEnd) {
		this.periodEnd = periodEnd;
	}
	public Boolean getLivemode() {
		return livemode;
	}
	public void setLivemode(Boolean livemode) {
		this.livemode = livemode;
	}
	public Integer getAttemptCount() {
		return attemptCount;
	}
	public void setAttemptCount(Integer attemptCount) {
		this.attemptCount = attemptCount;
	}
}