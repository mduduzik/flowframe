package org.flowframe.erp.app.contractmanagement.domain.service;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name = "fferpserviceprovisiongroupservice")
public class ServiceProvisionGroupService extends MultitenantBaseEntity {
	@OneToOne
    private ServiceProvision service;

	@XmlTransient
    @ManyToOne
    private ServiceProvisionGroup serviceGroup;

	public ServiceProvision getService() {
		return service;
	}

	public void setService(ServiceProvision service) {
		this.service = service;
	}

	public ServiceProvisionGroup getServiceGroup() {
		return serviceGroup;
	}

	public void setServiceGroup(ServiceProvisionGroup serviceGroup) {
		this.serviceGroup = serviceGroup;
	}
}