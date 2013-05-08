package org.flowframe.erp.app.financialmanagement.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "fferpfinreceipt")
public class Receipt extends BaseEntity {
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL,mappedBy="receipt")
	private Set<ReceiptLine> lines = new HashSet<ReceiptLine>();
}