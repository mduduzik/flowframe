
package org.flowframe.erp.integration.adaptors.stripe.services;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for folder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="folder">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services.stripe.adaptors.integration.erp.flowframe.org/}abstractDocument">
 *       &lt;sequence>
 *         &lt;element name="columnBitmask" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="companyId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="createDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="defaultFileEntryTypeId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="files" type="{http://services.stripe.adaptors.integration.erp.flowframe.org/}fileEntry" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="folderId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="groupId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="lastPostDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="modifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="mountPoint" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="originalCompanyId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="originalGroupId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="originalMountPoint" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="originalName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="originalParentFolderId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="originalRepositoryId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="originalUuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="overrideFileEntryTypes" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="parentFolderId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="repositoryId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="setOriginalCompanyId" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="setOriginalGroupId" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="setOriginalMountPoint" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="setOriginalParentFolderId" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="setOriginalRepositoryId" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userUuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "folder", propOrder = {
    "columnBitmask",
    "companyId",
    "createDate",
    "defaultFileEntryTypeId",
    "description",
    "files",
    "folderId",
    "groupId",
    "lastPostDate",
    "modifiedDate",
    "mountPoint",
    "name",
    "originalCompanyId",
    "originalGroupId",
    "originalMountPoint",
    "originalName",
    "originalParentFolderId",
    "originalRepositoryId",
    "originalUuid",
    "overrideFileEntryTypes",
    "parentFolderId",
    "repositoryId",
    "setOriginalCompanyId",
    "setOriginalGroupId",
    "setOriginalMountPoint",
    "setOriginalParentFolderId",
    "setOriginalRepositoryId",
    "userId",
    "userName",
    "userUuid",
    "uuid"
})
public class Folder
    extends AbstractDocument
{

    protected long columnBitmask;
    protected long companyId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createDate;
    protected long defaultFileEntryTypeId;
    protected String description;
    @XmlElement(nillable = true)
    protected List<FileEntry> files;
    protected long folderId;
    protected long groupId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastPostDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    protected boolean mountPoint;
    protected String name;
    protected long originalCompanyId;
    protected long originalGroupId;
    protected boolean originalMountPoint;
    protected String originalName;
    protected long originalParentFolderId;
    protected long originalRepositoryId;
    protected String originalUuid;
    protected boolean overrideFileEntryTypes;
    protected long parentFolderId;
    protected long repositoryId;
    protected boolean setOriginalCompanyId;
    protected boolean setOriginalGroupId;
    protected boolean setOriginalMountPoint;
    protected boolean setOriginalParentFolderId;
    protected boolean setOriginalRepositoryId;
    protected long userId;
    protected String userName;
    protected String userUuid;
    protected String uuid;

    /**
     * Gets the value of the columnBitmask property.
     * 
     */
    public long getColumnBitmask() {
        return columnBitmask;
    }

    /**
     * Sets the value of the columnBitmask property.
     * 
     */
    public void setColumnBitmask(long value) {
        this.columnBitmask = value;
    }

    /**
     * Gets the value of the companyId property.
     * 
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * Sets the value of the companyId property.
     * 
     */
    public void setCompanyId(long value) {
        this.companyId = value;
    }

    /**
     * Gets the value of the createDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreateDate() {
        return createDate;
    }

    /**
     * Sets the value of the createDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreateDate(XMLGregorianCalendar value) {
        this.createDate = value;
    }

    /**
     * Gets the value of the defaultFileEntryTypeId property.
     * 
     */
    public long getDefaultFileEntryTypeId() {
        return defaultFileEntryTypeId;
    }

    /**
     * Sets the value of the defaultFileEntryTypeId property.
     * 
     */
    public void setDefaultFileEntryTypeId(long value) {
        this.defaultFileEntryTypeId = value;
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
     * Gets the value of the files property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the files property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFiles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FileEntry }
     * 
     * 
     */
    public List<FileEntry> getFiles() {
        if (files == null) {
            files = new ArrayList<FileEntry>();
        }
        return this.files;
    }

    /**
     * Gets the value of the folderId property.
     * 
     */
    public long getFolderId() {
        return folderId;
    }

    /**
     * Sets the value of the folderId property.
     * 
     */
    public void setFolderId(long value) {
        this.folderId = value;
    }

    /**
     * Gets the value of the groupId property.
     * 
     */
    public long getGroupId() {
        return groupId;
    }

    /**
     * Sets the value of the groupId property.
     * 
     */
    public void setGroupId(long value) {
        this.groupId = value;
    }

    /**
     * Gets the value of the lastPostDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastPostDate() {
        return lastPostDate;
    }

    /**
     * Sets the value of the lastPostDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastPostDate(XMLGregorianCalendar value) {
        this.lastPostDate = value;
    }

    /**
     * Gets the value of the modifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifiedDate() {
        return modifiedDate;
    }

    /**
     * Sets the value of the modifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifiedDate(XMLGregorianCalendar value) {
        this.modifiedDate = value;
    }

    /**
     * Gets the value of the mountPoint property.
     * 
     */
    public boolean isMountPoint() {
        return mountPoint;
    }

    /**
     * Sets the value of the mountPoint property.
     * 
     */
    public void setMountPoint(boolean value) {
        this.mountPoint = value;
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
     * Gets the value of the originalCompanyId property.
     * 
     */
    public long getOriginalCompanyId() {
        return originalCompanyId;
    }

    /**
     * Sets the value of the originalCompanyId property.
     * 
     */
    public void setOriginalCompanyId(long value) {
        this.originalCompanyId = value;
    }

    /**
     * Gets the value of the originalGroupId property.
     * 
     */
    public long getOriginalGroupId() {
        return originalGroupId;
    }

    /**
     * Sets the value of the originalGroupId property.
     * 
     */
    public void setOriginalGroupId(long value) {
        this.originalGroupId = value;
    }

    /**
     * Gets the value of the originalMountPoint property.
     * 
     */
    public boolean isOriginalMountPoint() {
        return originalMountPoint;
    }

    /**
     * Sets the value of the originalMountPoint property.
     * 
     */
    public void setOriginalMountPoint(boolean value) {
        this.originalMountPoint = value;
    }

    /**
     * Gets the value of the originalName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalName() {
        return originalName;
    }

    /**
     * Sets the value of the originalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalName(String value) {
        this.originalName = value;
    }

    /**
     * Gets the value of the originalParentFolderId property.
     * 
     */
    public long getOriginalParentFolderId() {
        return originalParentFolderId;
    }

    /**
     * Sets the value of the originalParentFolderId property.
     * 
     */
    public void setOriginalParentFolderId(long value) {
        this.originalParentFolderId = value;
    }

    /**
     * Gets the value of the originalRepositoryId property.
     * 
     */
    public long getOriginalRepositoryId() {
        return originalRepositoryId;
    }

    /**
     * Sets the value of the originalRepositoryId property.
     * 
     */
    public void setOriginalRepositoryId(long value) {
        this.originalRepositoryId = value;
    }

    /**
     * Gets the value of the originalUuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalUuid() {
        return originalUuid;
    }

    /**
     * Sets the value of the originalUuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalUuid(String value) {
        this.originalUuid = value;
    }

    /**
     * Gets the value of the overrideFileEntryTypes property.
     * 
     */
    public boolean isOverrideFileEntryTypes() {
        return overrideFileEntryTypes;
    }

    /**
     * Sets the value of the overrideFileEntryTypes property.
     * 
     */
    public void setOverrideFileEntryTypes(boolean value) {
        this.overrideFileEntryTypes = value;
    }

    /**
     * Gets the value of the parentFolderId property.
     * 
     */
    public long getParentFolderId() {
        return parentFolderId;
    }

    /**
     * Sets the value of the parentFolderId property.
     * 
     */
    public void setParentFolderId(long value) {
        this.parentFolderId = value;
    }

    /**
     * Gets the value of the repositoryId property.
     * 
     */
    public long getRepositoryId() {
        return repositoryId;
    }

    /**
     * Sets the value of the repositoryId property.
     * 
     */
    public void setRepositoryId(long value) {
        this.repositoryId = value;
    }

    /**
     * Gets the value of the setOriginalCompanyId property.
     * 
     */
    public boolean isSetOriginalCompanyId() {
        return setOriginalCompanyId;
    }

    /**
     * Sets the value of the setOriginalCompanyId property.
     * 
     */
    public void setSetOriginalCompanyId(boolean value) {
        this.setOriginalCompanyId = value;
    }

    /**
     * Gets the value of the setOriginalGroupId property.
     * 
     */
    public boolean isSetOriginalGroupId() {
        return setOriginalGroupId;
    }

    /**
     * Sets the value of the setOriginalGroupId property.
     * 
     */
    public void setSetOriginalGroupId(boolean value) {
        this.setOriginalGroupId = value;
    }

    /**
     * Gets the value of the setOriginalMountPoint property.
     * 
     */
    public boolean isSetOriginalMountPoint() {
        return setOriginalMountPoint;
    }

    /**
     * Sets the value of the setOriginalMountPoint property.
     * 
     */
    public void setSetOriginalMountPoint(boolean value) {
        this.setOriginalMountPoint = value;
    }

    /**
     * Gets the value of the setOriginalParentFolderId property.
     * 
     */
    public boolean isSetOriginalParentFolderId() {
        return setOriginalParentFolderId;
    }

    /**
     * Sets the value of the setOriginalParentFolderId property.
     * 
     */
    public void setSetOriginalParentFolderId(boolean value) {
        this.setOriginalParentFolderId = value;
    }

    /**
     * Gets the value of the setOriginalRepositoryId property.
     * 
     */
    public boolean isSetOriginalRepositoryId() {
        return setOriginalRepositoryId;
    }

    /**
     * Sets the value of the setOriginalRepositoryId property.
     * 
     */
    public void setSetOriginalRepositoryId(boolean value) {
        this.setOriginalRepositoryId = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     */
    public void setUserId(long value) {
        this.userId = value;
    }

    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Gets the value of the userUuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserUuid() {
        return userUuid;
    }

    /**
     * Sets the value of the userUuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserUuid(String value) {
        this.userUuid = value;
    }

    /**
     * Gets the value of the uuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the value of the uuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuid(String value) {
        this.uuid = value;
    }

}
