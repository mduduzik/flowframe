package org.flowframe.erp.app.salesmanagement.domain.invoice;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.flowframe.kernel.common.mdm.domain.currency.CurrencyUnit;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;

@Entity
public class SalesInvoice extends Invoice {
	@ManyToOne
	private Organization customer;

	@ManyToOne
	private CurrencyUnit currency;
	
	@OneToMany(targetEntity = SalesInvoiceLine.class,mappedBy="parentInvoice",cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	private Set<SalesInvoiceLine> lines = new HashSet<SalesInvoiceLine>();

	private Date periodStart;
	private Date periodEnd;
	private Double subtotal;
	private Double total;
	private Boolean attempted = false;
	private Boolean closed = false;
	private Boolean paid = false;
	private Boolean livemode = false;
	private Integer attemptCount = 0;
	private Double amountDue;

	private Double startingBalance;
	private Double endingBalance;
	private Date nextPaymentAttempt;
	private String charge;
	private String discount;
}