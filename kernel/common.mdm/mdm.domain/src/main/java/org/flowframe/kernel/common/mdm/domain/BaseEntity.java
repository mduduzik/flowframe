package org.flowframe.kernel.common.mdm.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;
import org.flowframe.kernel.common.mdm.domain.note.Note;


@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = 2729618029911190092L;
	
	public static final String BASIC_ENTITY_ATTRIBUTE_ID = "id";
	public static final String BASIC_ENTITY_ATTRIBUTE_NAME = "name";
	public static final String BASIC_ENTITY_ATTRIBUTE_CODE = "code";
	public static final String BASIC_ENTITY_ATTRIBUTE_DATE_CREATED = "dateCreated";
	public static final String BASIC_ENTITY_ATTRIBUTE_DATE_LAST_UPDATED = "dateLastUpdated";

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected Long id;
	
	private Long ownerEntityId;
	
	private Long ownerEntityTypeId;
	
	@Transient
	private EntityManager entityManager;
    	
    @Version
    @Column(name = "version")
    private Integer version;
    
    @ManyToOne(targetEntity = Folder.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn
    private Folder docFolder;
    
    @ManyToOne(targetEntity = Note.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn
    private Note note;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated = new Date();
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLastUpdated = new Date();
    
    private Boolean active = true;

    private String externalName;

    private String externalCode;

    private String externalRefId;
    
	private String parentRefId;

    private String name;

    @Column(unique = true)
    private String code;

    private String refId;
    
    private String description;
    
    private String portalId;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}    

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}    

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateLastUpdated() {
		return dateLastUpdated;
	}

	public void setDateLastUpdated(Date dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public Folder getDocFolder() {
		return docFolder;
	}

	public void setDocFolder(Folder docFolder) {
		this.docFolder = docFolder;
	}

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	public Long getOwnerEntityId() {
		return ownerEntityId;
	}

	public void setOwnerEntityId(Long ownerEntityId) {
		this.ownerEntityId = ownerEntityId;
	}

	public Long getOwnerEntityTypeId() {
		return ownerEntityTypeId;
	}

	public void setOwnerEntityTypeId(Long ownerEntityTypeId) {
		this.ownerEntityTypeId = ownerEntityTypeId;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}