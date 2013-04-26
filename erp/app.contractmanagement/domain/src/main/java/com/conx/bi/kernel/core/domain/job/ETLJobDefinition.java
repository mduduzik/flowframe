package com.conx.bi.kernel.core.domain.job;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ETLJobDefinition extends ServiceJobDefinition {
    @ManyToOne(targetEntity = ETLJobMetaReference.class, cascade = {CascadeType.ALL})
    private ETLJobMetaReference jobMetaReference;
    

	public ETLJobMetaReference getJobMetaReference() {
		return jobMetaReference;
	}

	public void setJobMetaReference(ETLJobMetaReference jobMetaReference) {
		this.jobMetaReference = jobMetaReference;
	}
}