package com.conx.bi.kernel.core.domain.job;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ReportingJobDefinition extends ServiceJobDefinition {
    @ManyToOne(targetEntity = Endpoint.class, cascade = {CascadeType.ALL})
    
    @JoinColumn(name = "CONNECTION__REPORTJOB_ID")    
    protected Endpoint connection;
}