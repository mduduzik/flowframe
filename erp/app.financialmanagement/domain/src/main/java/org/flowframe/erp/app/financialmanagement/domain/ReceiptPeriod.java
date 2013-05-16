package org.flowframe.erp.app.financialmanagement.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "fferpfinreceiptperiod")
public class ReceiptPeriod extends BaseEntity {
	private Date start;
	private Date end;
	
	public ReceiptPeriod(){
	}
	
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public ReceiptPeriod(Date start, Date end) {
		super();
		this.start = start;
		this.end = end;
	}
}