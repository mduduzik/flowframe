package org.flowframe.kernel.common.mdm.domain.documentlibrary;

import java.io.Serializable;

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


@SuppressWarnings("serial")
@MappedSuperclass
public class AbstractDocument implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected Long id;
	
    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.EAGER, cascade=CascadeType.REFRESH)
    @JoinColumn
    private Organization tenant;

	private Boolean active = true;

	private String externalName;

	private String externalCode;

	private String externalRefId;

	private String parentRefId;

	private String refId;

	private String portalId;

	private Long dlFolderId;

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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getExternalName() {
		return externalName;
	}

	public void setExternalName(String externalName) {
		this.externalName = externalName;
	}

	public String getExternalCode() {
		return externalCode;
	}

	public void setExternalCode(String externalCode) {
		this.externalCode = externalCode;
	}

	public String getExternalRefId() {
		return externalRefId;
	}

	public void setExternalRefId(String externalRefId) {
		this.externalRefId = externalRefId;
	}

	public String getParentRefId() {
		return parentRefId;
	}

	public void setParentRefId(String parentRefId) {
		this.parentRefId = parentRefId;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public Long getDlFolderId() {
		return dlFolderId;
	}

	public void setDlFolderId(Long dlFolderId) {
		this.dlFolderId = dlFolderId;
	}
}
