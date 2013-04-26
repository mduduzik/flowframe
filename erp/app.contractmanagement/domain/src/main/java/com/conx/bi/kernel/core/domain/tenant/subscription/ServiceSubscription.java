package com.conx.bi.kernel.core.domain.tenant.subscription;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;

import com.conx.bi.kernel.core.domain.service.ServiceProvision;
import com.conx.bi.kernel.core.domain.tenant.Tenant;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name = "biservicesubscription")
public class ServiceSubscription extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6484689289168825188L;

    
    @OneToOne
    private ServiceProvision service;
    
    @ManyToOne(fetch=FetchType.EAGER)
    private Tenant subscriber;	
    
    private Double monthlyCost;
    
    private Double hourlyCost;
    
    private Double discount;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtStarted;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtScheduledStart;  
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtScheduledStop;

	public ServiceProvision getService() {
		return service;
	}

	public void setService(ServiceProvision service) {
		this.service = service;
	}

	public Tenant getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(Tenant subscriber) {
		this.subscriber = subscriber;
	}

	public Double getMonthlyCost() {
		return monthlyCost;
	}

	public void setMonthlyCost(Double monthlyCost) {
		this.monthlyCost = monthlyCost;
	}

	public Double getHourlyCost() {
		return hourlyCost;
	}

	public void setHourlyCost(Double hourlyCost) {
		this.hourlyCost = hourlyCost;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Date getDtStarted() {
		return dtStarted;
	}

	public void setDtStarted(Date dtStarted) {
		this.dtStarted = dtStarted;
	}

	public Date getDtScheduledStart() {
		return dtScheduledStart;
	}

	public void setDtScheduledStart(Date dtScheduledStart) {
		this.dtScheduledStart = dtScheduledStart;
	}

	public Date getDtScheduledStop() {
		return dtScheduledStop;
	}

	public void setDtScheduledStop(Date dtScheduledStop) {
		this.dtScheduledStop = dtScheduledStop;
	}    
}