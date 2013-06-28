
package org.flowframe.erp.integration.adaptors.services.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for fileEntry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fileEntry">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services.stripe.adaptors.integration.erp.flowframe.org/}abstractDocument">
 *       &lt;sequence>
 *         &lt;element name="columnBitmask" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="companyId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="contentStream" type="{http://services.stripe.adaptors.integration.erp.flowframe.org/}inputStream" minOccurs="0"/>
 *         &lt;element name="createDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="custom1ImageId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="custom2ImageId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="docType" type="{http://services.stripe.adaptors.integration.erp.flowframe.org/}docType" minOccurs="0"/>
 *         &lt;element name="extension" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="extraSettings" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fileEntryId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="fileEntryTypeId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="folder" type="{http://services.stripe.adaptors.integration.erp.flowframe.org/}folder" minOccurs="0"/>
 *         &lt;element name="folderId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="groupId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="largeImageId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="mimeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="originalCompanyId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="originalFileEntryTypeId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="originalFolderId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="originalGroupId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="originalMimeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="originalName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="originalTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="originalUserId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="originalUuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="readCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="repositoryId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="setOriginalCompanyId" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="setOriginalFileEntryTypeId" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="setOriginalFolderId" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="setOriginalGroupId" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="setOriginalUserId" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="smallImageId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userUuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="versionUserId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="versionUserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="versionUserUuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fileEntry", propOrder = {
    "columnBitmask",
    "companyId",
    "contentStream",
    "createDate",
    "custom1ImageId",
    "custom2ImageId",
    "description",
    "docType",
    "extension",
    "extraSettings",
    "fileEntryId",
    "fileEntryTypeId",
    "folder",
    "folderId",
    "groupId",
    "largeImageId",
    "mimeType",
    "modifiedDate",
    "name",
    "originalCompanyId",
    "originalFileEntryTypeId",
    "originalFolderId",
    "originalGroupId",
    "originalMimeType",
    "originalName",
    "originalTitle",
    "originalUserId",
    "originalUuid",
    "readCount",
    "repositoryId",
    "setOriginalCompanyId",
    "setOriginalFileEntryTypeId",
    "setOriginalFolderId",
    "setOriginalGroupId",
    "setOriginalUserId",
    "size",
    "smallImageId",
    "title",
    "userId",
    "userName",
    "userUuid",
    "uuid",
    "version",
    "versionUserId",
    "versionUserName",
    "versionUserUuid"
})
public class FileEntry
    extends AbstractDocument
{

    protected long columnBitmask;
    protected long companyId;
    protected InputStream contentStream;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createDate;
    protected long custom1ImageId;
    protected long custom2ImageId;
    protected String description;
    protected DocType docType;
    protected String extension;
    protected String extraSettings;
    protected long fileEntryId;
    protected long fileEntryTypeId;
    protected Folder folder;
    protected long folderId;
    protected long groupId;
    protected long largeImageId;
    protected String mimeType;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    protected String name;
    protected long originalCompanyId;
    protected long originalFileEntryTypeId;
    protected long originalFolderId;
    protected long originalGroupId;
    protected String originalMimeType;
    protected String originalName;
    protected String originalTitle;
    protected long originalUserId;
    protected String originalUuid;
    protected int readCount;
    protected long repositoryId;
    protected boolean setOriginalCompanyId;
    protected boolean setOriginalFileEntryTypeId;
    protected boolean setOriginalFolderId;
    protected boolean setOriginalGroupId;
    protected boolean setOriginalUserId;
    protected long size;
    protected long smallImageId;
    protected String title;
    protected long userId;
    protected String userName;
    protected String userUuid;
    protected String uuid;
    protected String version;
    protected long versionUserId;
    protected String versionUserName;
    protected String versionUserUuid;

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
     * Gets the value of the contentStream property.
     * 
     * @return
     *     possible object is
     *     {@link InputStream }
     *     
     */
    public InputStream getContentStream() {
        return contentStream;
    }

    /**
     * Sets the value of the contentStream property.
     * 
     * @param value
     *     allowed object is
     *     {@link InputStream }
     *     
     */
    public void setContentStream(InputStream value) {
        this.contentStream = value;
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
     * Gets the value of the custom1ImageId property.
     * 
     */
    public long getCustom1ImageId() {
        return custom1ImageId;
    }

    /**
     * Sets the value of the custom1ImageId property.
     * 
     */
    public void setCustom1ImageId(long value) {
        this.custom1ImageId = value;
    }

    /**
     * Gets the value of the custom2ImageId property.
     * 
     */
    public long getCustom2ImageId() {
        return custom2ImageId;
    }

    /**
     * Sets the value of the custom2ImageId property.
     * 
     */
    public void setCustom2ImageId(long value) {
        this.custom2ImageId = value;
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
     * Gets the value of the docType property.
     * 
     * @return
     *     possible object is
     *     {@link DocType }
     *     
     */
    public DocType getDocType() {
        return docType;
    }

    /**
     * Sets the value of the docType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocType }
     *     
     */
    public void setDocType(DocType value) {
        this.docType = value;
    }

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtension(String value) {
        this.extension = value;
    }

    /**
     * Gets the value of the extraSettings property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtraSettings() {
        return extraSettings;
    }

    /**
     * Sets the value of the extraSettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtraSettings(String value) {
        this.extraSettings = value;
    }

    /**
     * Gets the value of the fileEntryId property.
     * 
     */
    public long getFileEntryId() {
        return fileEntryId;
    }

    /**
     * Sets the value of the fileEntryId property.
     * 
     */
    public void setFileEntryId(long value) {
        this.fileEntryId = value;
    }

    /**
     * Gets the value of the fileEntryTypeId property.
     * 
     */
    public long getFileEntryTypeId() {
        return fileEntryTypeId;
    }

    /**
     * Sets the value of the fileEntryTypeId property.
     * 
     */
    public void setFileEntryTypeId(long value) {
        this.fileEntryTypeId = value;
    }

    /**
     * Gets the value of the folder property.
     * 
     * @return
     *     possible object is
     *     {@link Folder }
     *     
     */
    public Folder getFolder() {
        return folder;
    }

    /**
     * Sets the value of the folder property.
     * 
     * @param value
     *     allowed object is
     *     {@link Folder }
     *     
     */
    public void setFolder(Folder value) {
        this.folder = value;
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
     * Gets the value of the largeImageId property.
     * 
     */
    public long getLargeImageId() {
        return largeImageId;
    }

    /**
     * Sets the value of the largeImageId property.
     * 
     */
    public void setLargeImageId(long value) {
        this.largeImageId = value;
    }

    /**
     * Gets the value of the mimeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Sets the value of the mimeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMimeType(String value) {
        this.mimeType = value;
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
     * Gets the value of the originalFileEntryTypeId property.
     * 
     */
    public long getOriginalFileEntryTypeId() {
        return originalFileEntryTypeId;
    }

    /**
     * Sets the value of the originalFileEntryTypeId property.
     * 
     */
    public void setOriginalFileEntryTypeId(long value) {
        this.originalFileEntryTypeId = value;
    }

    /**
     * Gets the value of the originalFolderId property.
     * 
     */
    public long getOriginalFolderId() {
        return originalFolderId;
    }

    /**
     * Sets the value of the originalFolderId property.
     * 
     */
    public void setOriginalFolderId(long value) {
        this.originalFolderId = value;
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
     * Gets the value of the originalMimeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalMimeType() {
        return originalMimeType;
    }

    /**
     * Sets the value of the originalMimeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalMimeType(String value) {
        this.originalMimeType = value;
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
     * Gets the value of the originalTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * Sets the value of the originalTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalTitle(String value) {
        this.originalTitle = value;
    }

    /**
     * Gets the value of the originalUserId property.
     * 
     */
    public long getOriginalUserId() {
        return originalUserId;
    }

    /**
     * Sets the value of the originalUserId property.
     * 
     */
    public void setOriginalUserId(long value) {
        this.originalUserId = value;
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
     * Gets the value of the readCount property.
     * 
     */
    public int getReadCount() {
        return readCount;
    }

    /**
     * Sets the value of the readCount property.
     * 
     */
    public void setReadCount(int value) {
        this.readCount = value;
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
     * Gets the value of the setOriginalFileEntryTypeId property.
     * 
     */
    public boolean isSetOriginalFileEntryTypeId() {
        return setOriginalFileEntryTypeId;
    }

    /**
     * Sets the value of the setOriginalFileEntryTypeId property.
     * 
     */
    public void setSetOriginalFileEntryTypeId(boolean value) {
        this.setOriginalFileEntryTypeId = value;
    }

    /**
     * Gets the value of the setOriginalFolderId property.
     * 
     */
    public boolean isSetOriginalFolderId() {
        return setOriginalFolderId;
    }

    /**
     * Sets the value of the setOriginalFolderId property.
     * 
     */
    public void setSetOriginalFolderId(boolean value) {
        this.setOriginalFolderId = value;
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
     * Gets the value of the setOriginalUserId property.
     * 
     */
    public boolean isSetOriginalUserId() {
        return setOriginalUserId;
    }

    /**
     * Sets the value of the setOriginalUserId property.
     * 
     */
    public void setSetOriginalUserId(boolean value) {
        this.setOriginalUserId = value;
    }

    /**
     * Gets the value of the size property.
     * 
     */
    public long getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     */
    public void setSize(long value) {
        this.size = value;
    }

    /**
     * Gets the value of the smallImageId property.
     * 
     */
    public long getSmallImageId() {
        return smallImageId;
    }

    /**
     * Sets the value of the smallImageId property.
     * 
     */
    public void setSmallImageId(long value) {
        this.smallImageId = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
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

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the versionUserId property.
     * 
     */
    public long getVersionUserId() {
        return versionUserId;
    }

    /**
     * Sets the value of the versionUserId property.
     * 
     */
    public void setVersionUserId(long value) {
        this.versionUserId = value;
    }

    /**
     * Gets the value of the versionUserName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersionUserName() {
        return versionUserName;
    }

    /**
     * Sets the value of the versionUserName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersionUserName(String value) {
        this.versionUserName = value;
    }

    /**
     * Gets the value of the versionUserUuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersionUserUuid() {
        return versionUserUuid;
    }

    /**
     * Sets the value of the versionUserUuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersionUserUuid(String value) {
        this.versionUserUuid = value;
    }

}
