package org.flowframe.kernel.common.mdm.domain.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.flowframe.kernel.common.mdm.domain.organization.Organization;


@MappedSuperclass
public class AbstractUser implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected Long id;
	
    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.EAGER)
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
	
    @Transient
    public String getTenantName() {
    	return this.tenant != null?this.tenant.getName():null;
    }  
}
