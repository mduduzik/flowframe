package com.conx.bi.kernel.core.domain.job;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;

import com.conx.bi.kernel.core.domain.service.ServiceProvision;
import com.conx.bi.kernel.core.enums.SERVICEJOBTYPE;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "biservicejobdefinition")
public abstract class ServiceJobDefinition extends MultitenantBaseEntity {
	
    @Enumerated(EnumType.STRING)
    private SERVICEJOBTYPE type;
	
	@ManyToOne
	private ServiceProvision service;
}