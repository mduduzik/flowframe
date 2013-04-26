package com.conx.bi.kernel.core.domain.job;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "biservicejobinstance")
public abstract class ServiceJobInstance extends MultitenantBaseEntity {
	@ManyToOne
	private ServiceJobDefinition jobDefinition;
	
	
}