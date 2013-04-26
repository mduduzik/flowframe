package org.flowframe.erp.app.salesmanagement.domain.invoice;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.flowframe.erp.app.salesmanagement.enums.SALESINVOICELINETYPE;
import org.flowframe.kernel.common.mdm.domain.BaseEntity;

@Entity
@Table(name = "fferpsalesinvoiceline")
public class SalesInvoiceLine extends BaseEntity {
	@ManyToOne
	private SalesInvoice parentInvoice;
	
    @Enumerated(EnumType.STRING)
    private SALESINVOICELINETYPE type;	
    
    private Boolean proration = false;

	private Date  periodStart;
	
	private Date  periodEnd;
	
	private Double amount;
	
	private Double discount;
	
	private Double amountNet;
}