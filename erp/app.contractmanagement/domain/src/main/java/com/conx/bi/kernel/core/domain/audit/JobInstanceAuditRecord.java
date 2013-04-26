package com.conx.bi.kernel.core.domain.audit;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.conx.bi.kernel.core.domain.job.ServiceJobInstance;

@Entity
public class JobInstanceAuditRecord extends AuditRecord {
	@ManyToOne
	private ServiceJobInstance jobInstance;
}