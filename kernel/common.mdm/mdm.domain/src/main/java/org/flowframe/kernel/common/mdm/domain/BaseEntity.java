package org.flowframe.kernel.common.mdm.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.datatype.XMLGregorianCalendar;

import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;
import org.flowframe.kernel.common.mdm.domain.note.Note;
import org.flowframe.kernel.common.mdm.domain.preferences.EntityPreference;

@MappedSuperclass
public abstract class BaseEntity
    implements Serializable
{
	private static final long serialVersionUID = 2729618029911190092L;
	
	public static final String BASIC_ENTITY_ATTRIBUTE_ID = "id";
	public static final String BASIC_ENTITY_ATTRIBUTE_NAME = "name";
	public static final String BASIC_ENTITY_ATTRIBUTE_CODE = "code";
	public static final String BASIC_ENTITY_ATTRIBUTE_DATE_CREATED = "dateCreated";
	public static final String BASIC_ENTITY_ATTRIBUTE_DATE_LAST_UPDATED = "dateLastUpdated";

	
    @Id
    @Column(name = "id", scale = 0)
    @GeneratedValue(strategy = GenerationType.AUTO)	
    protected Long id;
    @Version
    @Column(name = "version_", scale = 0)
    protected int version;
    @Basic
    @Column(name = "issystem")
    protected boolean issystem;
    @Basic
    @Column(name = "datecreated")
    @Temporal(TemporalType.DATE)
    protected Date dateCreated = new Date();
    @Basic
    @Column(name = "datelastupdated")
    @Temporal(TemporalType.DATE)
    protected Date dateLastUpdated = new Date();
    @Basic
    @Column(name = "ownerentityid", precision = 20, scale = 0)
    protected long ownerEntityId;
    @Basic
    @Column(name = "ownerentitytypeid", precision = 20, scale = 0)
    protected long ownerEntityTypeId;
    @Basic
    @Column(name = "active_")    
    protected boolean active = true;
    @Basic
    @Column(name = "externalname", length = 255)    
    protected String externalName;
    @Basic
    @Column(name = "externalcode", length = 255)    
    protected String externalCode;
    @Basic
    @Column(name = "externalrefid", length = 255)    
    protected String externalRefId;
    @Basic
    @Column(name = "synchonized")    
    protected Boolean synchonized = false;    
    @Basic
    @Column(name = "parentrefid", length = 255)    
    protected String parentRefId;
    @Basic
    @Column(name = "name_", length = 255)    
    protected String name;
    @Basic
    @Column(name = "code", length = 255,unique = true)    
    protected String code;
    @Basic
    @Column(name = "refid", length = 255)    
    protected String refId;
    @Basic
    @Column(name = "description", length = 255)    
    protected String description;
    @Basic
    @Column(name = "portalid", length = 255)    
    protected String portalId;
    @Basic
    @Column(name = "schedulerjobid", length = 255)    
    protected String schedulerJobId;    
    @Basic
    @Column(name = "repositoryid", length = 255)    
    protected String repositoryid;
    
    @ManyToOne(targetEntity = Folder.class, fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn
    private Folder docFolder;
    
    @ManyToOne(targetEntity = Note.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn
    private Note note; 
    
    @ManyToOne(targetEntity = EntityPreference.class, fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn
    private EntityPreference preferences;

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

	/**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the version property.
     * 
     */
    public int getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     */
    public void setVersion(int value) {
        this.version = value;
    }

    /**
     * Gets the value of the issystem property.
     * 
     */
    public boolean isIssystem() {
        return issystem;
    }

    /**
     * Sets the value of the issystem property.
     * 
     */
    public void setIssystem(boolean value) {
        this.issystem = value;
    }

    /**
     * Gets the value of the dateCreated property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the value of the dateCreated property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateCreated(Date value) {
        this.dateCreated = value;
    }

    /**
     * Gets the value of the dateLastUpdated property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }

    /**
     * Sets the value of the dateLastUpdated property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateLastUpdated(Date value) {
        this.dateLastUpdated = value;
    }

    /**
     * Gets the value of the ownerEntityId property.
     * 
     */
    public long getOwnerEntityId() {
        return ownerEntityId;
    }

    /**
     * Sets the value of the ownerEntityId property.
     * 
     */
    public void setOwnerEntityId(long value) {
        this.ownerEntityId = value;
    }

    /**
     * Gets the value of the ownerEntityTypeId property.
     * 
     */
    public long getOwnerEntityTypeId() {
        return ownerEntityTypeId;
    }

    /**
     * Sets the value of the ownerEntityTypeId property.
     * 
     */
    public void setOwnerEntityTypeId(long value) {
        this.ownerEntityTypeId = value;
    }

    /**
     * Gets the value of the active property.
     * 
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the value of the active property.
     * 
     */
    public void setActive(boolean value) {
        this.active = value;
    }

    /**
     * Gets the value of the externalName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalName() {
        return externalName;
    }
    
    public void setPreferences(EntityPreference preferences) {
		this.preferences = preferences;
	}

    public EntityPreference getPreferences() {
		return preferences;
	}

	/**
     * Sets the value of the externalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalName(String value) {
        this.externalName = value;
    }

    /**
     * Gets the value of the externalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalCode() {
        return externalCode;
    }

    /**
     * Sets the value of the externalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalCode(String value) {
        this.externalCode = value;
    }

    /**
     * Gets the value of the externalRefId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalRefId() {
        return externalRefId;
    }

    /**
     * Sets the value of the externalRefId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalRefId(String value) {
        this.externalRefId = value;
    }

    /**
     * Gets the value of the parentRefId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentRefId() {
        return parentRefId;
    }

    /**
     * Sets the value of the parentRefId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentRefId(String value) {
        this.parentRefId = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the refId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefId() {
        return refId;
    }

    /**
     * Sets the value of the refId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefId(String value) {
        this.refId = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the portalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPortalId() {
        return portalId;
    }

    /**
     * Sets the value of the portalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPortalId(String value) {
        this.portalId = value;
    }

    /**
     * Gets the value of the repositoryid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepositoryid() {
        return repositoryid;
    }

    /**
     * Sets the value of the repositoryid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepositoryid(String value) {
        this.repositoryid = value;
    }

	public String getSchedulerJobId() {
		return schedulerJobId;
	}

	public void setSchedulerJobId(String schedulerJobId) {
		this.schedulerJobId = schedulerJobId;
	}

	public Boolean getSynchonized() {
		return synchonized;
	}

	public void setSynchonized(Boolean synchonized) {
		this.synchonized = synchonized;
	}
}
