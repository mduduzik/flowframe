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
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;
import org.flowframe.kernel.common.mdm.domain.note.Note;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsDate;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XmlAdapterUtils;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.HashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * <p>Java class for BaseEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="issystem" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="dateCreated" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="dateLastUpdated" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="ownerEntityId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ownerEntityTypeId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="active" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="externalName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="externalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="externalRefId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="parentRefId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="refId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="portalId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="repositoryid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseEntity", propOrder = {
    "id",
    "version",
    "issystem",
    "dateCreated",
    "dateLastUpdated",
    "ownerEntityId",
    "ownerEntityTypeId",
    "active",
    "externalName",
    "externalCode",
    "externalRefId",
    "parentRefId",
    "name",
    "code",
    "refId",
    "description",
    "portalId",
    "repositoryid"
})
@XmlSeeAlso({
    MultitenantBaseEntity.class
})
@MappedSuperclass
public abstract class BaseEntity
    implements Equals, HashCode, Serializable
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
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    @Basic
    @Column(name = "datecreated")
    @Temporal(TemporalType.DATE)
    protected Date dateCreated;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    @Basic
    @Column(name = "datelastupdated")
    @Temporal(TemporalType.DATE)
    protected Date dateLastUpdated;
    @Basic
    @Column(name = "ownerentityid", precision = 20, scale = 0)
    protected long ownerEntityId;
    @Basic
    @Column(name = "ownerentitytypeid", precision = 20, scale = 0)
    protected long ownerEntityTypeId;
    @XmlElement(defaultValue = "true")
    @Basic
    @Column(name = "active_")    
    protected boolean active;
    @XmlElement(required = true)
    @Basic
    @Column(name = "externalname", length = 255)    
    protected String externalName;
    @XmlElement(required = true)
    @Basic
    @Column(name = "externalcode", length = 255)    
    protected String externalCode;
    @XmlElement(required = true)
    @Basic
    @Column(name = "externalrefid", length = 255)    
    protected String externalRefId;
    @XmlElement(required = true)
    @Basic
    @Column(name = "parentrefid", length = 255)    
    protected String parentRefId;
    @XmlElement(required = true)
    @Basic
    @Column(name = "name_", length = 255)    
    protected String name;
    @XmlElement(required = true)
    @Basic
    @Column(name = "code", length = 255,unique = true)    
    protected String code;
    @XmlElement(required = true)
    @Basic
    @Column(name = "refid", length = 255)    
    protected String refId;
    @XmlElement(required = true)
    @Basic
    @Column(name = "description", length = 255)    
    protected String description;
    @XmlElement(required = true)
    @Basic
    @Column(name = "portalid", length = 255)    
    protected String portalId;
    @XmlElement(required = true)
    @Basic
    @Column(name = "repositoryid", length = 255)    
    protected String repositoryid;
    
    @XmlTransient
    @ManyToOne(targetEntity = Folder.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn
    private Folder docFolder;
    
    @XmlTransient
    @ManyToOne(targetEntity = Note.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn
    private Note note; 
    

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
            boolean lhsIssystem;
            lhsIssystem = this.isIssystem();
            boolean rhsIssystem;
            rhsIssystem = that.isIssystem();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "issystem", lhsIssystem), LocatorUtils.property(thatLocator, "issystem", rhsIssystem), lhsIssystem, rhsIssystem)) {
                return false;
            }
        }
        {
            Date lhsDateCreated;
            lhsDateCreated = this.getDateCreated();
            Date rhsDateCreated;
            rhsDateCreated = that.getDateCreated();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "dateCreated", lhsDateCreated), LocatorUtils.property(thatLocator, "dateCreated", rhsDateCreated), lhsDateCreated, rhsDateCreated)) {
                return false;
            }
        }
        {
            Date lhsDateLastUpdated;
            lhsDateLastUpdated = this.getDateLastUpdated();
            Date rhsDateLastUpdated;
            rhsDateLastUpdated = that.getDateLastUpdated();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "dateLastUpdated", lhsDateLastUpdated), LocatorUtils.property(thatLocator, "dateLastUpdated", rhsDateLastUpdated), lhsDateLastUpdated, rhsDateLastUpdated)) {
                return false;
            }
        }
        {
            long lhsOwnerEntityId;
            lhsOwnerEntityId = this.getOwnerEntityId();
            long rhsOwnerEntityId;
            rhsOwnerEntityId = that.getOwnerEntityId();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "ownerEntityId", lhsOwnerEntityId), LocatorUtils.property(thatLocator, "ownerEntityId", rhsOwnerEntityId), lhsOwnerEntityId, rhsOwnerEntityId)) {
                return false;
            }
        }
        {
            long lhsOwnerEntityTypeId;
            lhsOwnerEntityTypeId = this.getOwnerEntityTypeId();
            long rhsOwnerEntityTypeId;
            rhsOwnerEntityTypeId = that.getOwnerEntityTypeId();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "ownerEntityTypeId", lhsOwnerEntityTypeId), LocatorUtils.property(thatLocator, "ownerEntityTypeId", rhsOwnerEntityTypeId), lhsOwnerEntityTypeId, rhsOwnerEntityTypeId)) {
                return false;
            }
        }
        {
            boolean lhsActive;
            lhsActive = this.isActive();
            boolean rhsActive;
            rhsActive = that.isActive();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "active", lhsActive), LocatorUtils.property(thatLocator, "active", rhsActive), lhsActive, rhsActive)) {
                return false;
            }
        }
        {
            String lhsExternalName;
            lhsExternalName = this.getExternalName();
            String rhsExternalName;
            rhsExternalName = that.getExternalName();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "externalName", lhsExternalName), LocatorUtils.property(thatLocator, "externalName", rhsExternalName), lhsExternalName, rhsExternalName)) {
                return false;
            }
        }
        {
            String lhsExternalCode;
            lhsExternalCode = this.getExternalCode();
            String rhsExternalCode;
            rhsExternalCode = that.getExternalCode();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "externalCode", lhsExternalCode), LocatorUtils.property(thatLocator, "externalCode", rhsExternalCode), lhsExternalCode, rhsExternalCode)) {
                return false;
            }
        }
        {
            String lhsExternalRefId;
            lhsExternalRefId = this.getExternalRefId();
            String rhsExternalRefId;
            rhsExternalRefId = that.getExternalRefId();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "externalRefId", lhsExternalRefId), LocatorUtils.property(thatLocator, "externalRefId", rhsExternalRefId), lhsExternalRefId, rhsExternalRefId)) {
                return false;
            }
        }
        {
            String lhsParentRefId;
            lhsParentRefId = this.getParentRefId();
            String rhsParentRefId;
            rhsParentRefId = that.getParentRefId();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "parentRefId", lhsParentRefId), LocatorUtils.property(thatLocator, "parentRefId", rhsParentRefId), lhsParentRefId, rhsParentRefId)) {
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
            String lhsCode;
            lhsCode = this.getCode();
            String rhsCode;
            rhsCode = that.getCode();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "code", lhsCode), LocatorUtils.property(thatLocator, "code", rhsCode), lhsCode, rhsCode)) {
                return false;
            }
        }
        {
            String lhsRefId;
            lhsRefId = this.getRefId();
            String rhsRefId;
            rhsRefId = that.getRefId();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "refId", lhsRefId), LocatorUtils.property(thatLocator, "refId", rhsRefId), lhsRefId, rhsRefId)) {
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
            String lhsPortalId;
            lhsPortalId = this.getPortalId();
            String rhsPortalId;
            rhsPortalId = that.getPortalId();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "portalId", lhsPortalId), LocatorUtils.property(thatLocator, "portalId", rhsPortalId), lhsPortalId, rhsPortalId)) {
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
            boolean theIssystem;
            theIssystem = this.isIssystem();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "issystem", theIssystem), currentHashCode, theIssystem);
        }
        {
            Date theDateCreated;
            theDateCreated = this.getDateCreated();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "dateCreated", theDateCreated), currentHashCode, theDateCreated);
        }
        {
            Date theDateLastUpdated;
            theDateLastUpdated = this.getDateLastUpdated();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "dateLastUpdated", theDateLastUpdated), currentHashCode, theDateLastUpdated);
        }
        {
            long theOwnerEntityId;
            theOwnerEntityId = this.getOwnerEntityId();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "ownerEntityId", theOwnerEntityId), currentHashCode, theOwnerEntityId);
        }
        {
            long theOwnerEntityTypeId;
            theOwnerEntityTypeId = this.getOwnerEntityTypeId();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "ownerEntityTypeId", theOwnerEntityTypeId), currentHashCode, theOwnerEntityTypeId);
        }
        {
            boolean theActive;
            theActive = this.isActive();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "active", theActive), currentHashCode, theActive);
        }
        {
            String theExternalName;
            theExternalName = this.getExternalName();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "externalName", theExternalName), currentHashCode, theExternalName);
        }
        {
            String theExternalCode;
            theExternalCode = this.getExternalCode();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "externalCode", theExternalCode), currentHashCode, theExternalCode);
        }
        {
            String theExternalRefId;
            theExternalRefId = this.getExternalRefId();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "externalRefId", theExternalRefId), currentHashCode, theExternalRefId);
        }
        {
            String theParentRefId;
            theParentRefId = this.getParentRefId();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "parentRefId", theParentRefId), currentHashCode, theParentRefId);
        }
        {
            String theName;
            theName = this.getName();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "name", theName), currentHashCode, theName);
        }
        {
            String theCode;
            theCode = this.getCode();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "code", theCode), currentHashCode, theCode);
        }
        {
            String theRefId;
            theRefId = this.getRefId();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "refId", theRefId), currentHashCode, theRefId);
        }
        {
            String theDescription;
            theDescription = this.getDescription();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "description", theDescription), currentHashCode, theDescription);
        }
        {
            String thePortalId;
            thePortalId = this.getPortalId();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "portalId", thePortalId), currentHashCode, thePortalId);
        }
        {
            String theRepositoryid;
            theRepositoryid = this.getRepositoryid();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "repositoryid", theRepositoryid), currentHashCode, theRepositoryid);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

}
