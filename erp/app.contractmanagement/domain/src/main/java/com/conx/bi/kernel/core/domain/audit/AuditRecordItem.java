package com.conx.bi.kernel.core.domain.audit;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;

import com.conx.bi.kernel.core.enums.CALCULATORUNIT;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "biaudititem")
public abstract class AuditRecordItem extends MultitenantBaseEntity {
	@OneToOne
	private AuditRecord parentAudit;
	
    @Enumerated(EnumType.STRING)
    private CALCULATORUNIT unit;	
    
    private Double amount;
}