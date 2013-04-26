package org.flowframe.erp.app.salesmanagement.domain.invoice;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "fferpsalesinvoice")
public class Invoice extends BaseEntity {
}