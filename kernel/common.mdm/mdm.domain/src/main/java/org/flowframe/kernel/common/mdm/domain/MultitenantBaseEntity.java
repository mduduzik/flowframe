package org.flowframe.kernel.common.mdm.domain;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.flowframe.kernel.common.mdm.domain.organization.Organization;


@MappedSuperclass
public abstract class MultitenantBaseEntity extends BaseEntity {
	@ManyToOne(targetEntity = Organization.class, fetch = FetchType.LAZY)
	@JoinColumn
	private Organization tenant;

	public Organization getTenant() {
		return tenant;
	}

	public void setTenant(Organization tenant) {
		this.tenant = tenant;
	}
}