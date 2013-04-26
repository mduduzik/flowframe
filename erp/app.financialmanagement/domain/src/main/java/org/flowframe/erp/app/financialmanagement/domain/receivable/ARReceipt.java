package org.flowframe.erp.app.financialmanagement.domain.receivable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.flowframe.erp.app.financialmanagement.domain.Receipt;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;

@Entity
public class ARReceipt extends Receipt {
	@ManyToOne
	private Organization debtor;
}