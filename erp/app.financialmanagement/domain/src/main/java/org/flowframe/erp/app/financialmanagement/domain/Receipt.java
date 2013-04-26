package org.flowframe.erp.app.financialmanagement.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "fferpfinreceipt")
public class Receipt extends BaseEntity {
}