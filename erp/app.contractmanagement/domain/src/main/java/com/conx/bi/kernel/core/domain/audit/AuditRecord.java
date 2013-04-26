package com.conx.bi.kernel.core.domain.audit;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "biaudit")
public abstract class AuditRecord extends MultitenantBaseEntity {
	@OneToMany(targetEntity = AuditRecordItem.class,mappedBy="parentAudit",cascade = CascadeType.PERSIST,fetch=FetchType.EAGER)
    private Set<AuditRecordItem> items = new HashSet<AuditRecordItem>();
}