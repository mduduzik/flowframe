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


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.HashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


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
    
    protected String repositoryid;
    
    protected boolean system;

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
	
    public String getRepositoryid() {
        return repositoryid;
    }

    public void setRepositoryid(String value) {
        this.repositoryid = value;
    }	
	
    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean value) {
        this.system = value;
    }	

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	 public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
	     if (!(object instanceof BaseEntity)) {
	         return false;
	     }
	     if (this == object) {
	         return true;
	     }
	     final BaseEntity that = ((BaseEntity) object);
	     {
	         Long lhsId;
	         lhsId = this.getId();
	         Long rhsId;
	         rhsId = that.getId();
	         if (!strategy.equals(LocatorUtils.property(thisLocator, "id", lhsId), LocatorUtils.property(thatLocator, "id", rhsId), lhsId, rhsId)) {
	             return false;
	         }
	     }
	     {
	         int lhsVersion;
	         lhsVersion = this.getVersion();
	         int rhsVersion;
	         rhsVersion = that.getVersion();
	         if (!strategy.equals(LocatorUtils.property(thisLocator, "version", lhsVersion), LocatorUtils.property(thatLocator, "version", rhsVersion), lhsVersion, rhsVersion)) {
	             return false;
	         }
	     }
	     {
	         String lhsRepositoryid;
	         lhsRepositoryid = this.getRepositoryid();
	         String rhsRepositoryid;
	         rhsRepositoryid = that.getRepositoryid();
	         if (!strategy.equals(LocatorUtils.property(thisLocator, "repositoryid", lhsRepositoryid), LocatorUtils.property(thatLocator, "repositoryid", rhsRepositoryid), lhsRepositoryid, rhsRepositoryid)) {
	             return false;
	         }
	     }
	     {
	         String lhsCode;
	         lhsCode = this.getCode();
	         String rhsCode;
	         rhsCode = that.getCode();
	         if (!strategy.equals(LocatorUtils.property(thisLocator, "code", lhsCode), LocatorUtils.property(thatLocator, "code", rhsCode), lhsCode, rhsCode)) {
	             return false;
	         }
	     }
	     {
	         String lhsName;
	         lhsName = this.getName();
	         String rhsName;
	         rhsName = that.getName();
	         if (!strategy.equals(LocatorUtils.property(thisLocator, "name", lhsName), LocatorUtils.property(thatLocator, "name", rhsName), lhsName, rhsName)) {
	             return false;
	         }
	     }
	     {
	         String lhsDescription;
	         lhsDescription = this.getDescription();
	         String rhsDescription;
	         rhsDescription = that.getDescription();
	         if (!strategy.equals(LocatorUtils.property(thisLocator, "description", lhsDescription), LocatorUtils.property(thatLocator, "description", rhsDescription), lhsDescription, rhsDescription)) {
	             return false;
	         }
	     }
	     {
	         boolean lhsSystem;
	         lhsSystem = this.isSystem();
	         boolean rhsSystem;
	         rhsSystem = that.isSystem();
	         if (!strategy.equals(LocatorUtils.property(thisLocator, "system", lhsSystem), LocatorUtils.property(thatLocator, "system", rhsSystem), lhsSystem, rhsSystem)) {
	             return false;
	         }
	     }
	     return true;
	 }

	 public boolean equals(Object object) {
	     final EqualsStrategy strategy = JAXBEqualsStrategy.INSTANCE;
	     return equals(null, null, object, strategy);
	 }

	 public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
	     int currentHashCode = 1;
	     {
	         Long theId;
	         theId = this.getId();
	         currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "id", theId), currentHashCode, theId);
	     }
	     {
	         int theVersion;
	         theVersion = this.getVersion();
	         currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "version", theVersion), currentHashCode, theVersion);
	     }
	     {
	         String theRepositoryid;
	         theRepositoryid = this.getRepositoryid();
	         currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "repositoryid", theRepositoryid), currentHashCode, theRepositoryid);
	     }
	     {
	         String theCode;
	         theCode = this.getCode();
	         currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "code", theCode), currentHashCode, theCode);
	     }
	     {
	         String theName;
	         theName = this.getName();
	         currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "name", theName), currentHashCode, theName);
	     }
	     {
	         String theDescription;
	         theDescription = this.getDescription();
	         currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "description", theDescription), currentHashCode, theDescription);
	     }
	     {
	         boolean theSystem;
	         theSystem = this.isSystem();
	         currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "system", theSystem), currentHashCode, theSystem);
	     }
	     return currentHashCode;
	 }

	 public int hashCode() {
	     final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
	     return this.hashCode(null, strategy);
	 }	
}




