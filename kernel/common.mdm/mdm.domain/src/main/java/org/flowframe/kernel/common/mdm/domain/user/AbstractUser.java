package org.flowframe.kernel.common.mdm.domain.user;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.flowframe.kernel.common.mdm.domain.organization.Organization;


@MappedSuperclass
public class AbstractUser{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected Long id;
	
    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn
    private Organization tenant;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Organization getTenant() {
		return tenant;
	}

	public void setTenant(Organization tenant) {
		this.tenant = tenant;
	}
}
