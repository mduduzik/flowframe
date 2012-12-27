package org.flowframe.kernel.common.mdm.domain.documentlibrary;

import java.io.InputStream;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sysdlfileentry")
public class FileEntry extends AbstractDocument {
	
	@Transient
	private InputStream	contentStream;	
	
	@OneToOne
	private Folder folder;
	
	@ManyToOne
	private DocType docType;
	
	private String uuid;
	private String originalUuid;
	private long fileEntryId;
	private long groupId;
	private long originalGroupId;
	private boolean setOriginalGroupId;
	private long companyId;
	private long originalCompanyId;
	private boolean setOriginalCompanyId;
	private long userId;
	private String userUuid;
	private long originalUserId;
	private boolean setOriginalUserId;
	private String userName;
	private long versionUserId;
	private String versionUserUuid;
	private String versionUserName;
	private Date createDate;
	private Date modifiedDate;
	private long repositoryId;
	private long folderId;
	private long originalFolderId;
	private boolean setOriginalFolderId;
	private String name;
	private String originalName;
	private String extension;
	private String mimeType;
	private String originalMimeType;
	private String title;
	private String originalTitle;
	private String description;
	private String extraSettings;
	private long fileEntryTypeId;
	private long originalFileEntryTypeId;
	private boolean setOriginalFileEntryTypeId;
	private String version;
	private long size;
	private int readCount;
	private long smallImageId;
	private long largeImageId;
	private long custom1ImageId;
	private long custom2ImageId;
	private long columnBitmask;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getOriginalUuid() {
		return originalUuid;
	}
	public void setOriginalUuid(String originalUuid) {
		this.originalUuid = originalUuid;
	}
	public long getFileEntryId() {
		return fileEntryId;
	}
	public void setFileEntryId(long fileEntryId) {
		this.fileEntryId = fileEntryId;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public long getOriginalGroupId() {
		return originalGroupId;
	}
	public void setOriginalGroupId(long originalGroupId) {
		this.originalGroupId = originalGroupId;
	}
	public boolean isSetOriginalGroupId() {
		return setOriginalGroupId;
	}
	public void setSetOriginalGroupId(boolean setOriginalGroupId) {
		this.setOriginalGroupId = setOriginalGroupId;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public long getOriginalCompanyId() {
		return originalCompanyId;
	}
	public void setOriginalCompanyId(long originalCompanyId) {
		this.originalCompanyId = originalCompanyId;
	}
	public boolean isSetOriginalCompanyId() {
		return setOriginalCompanyId;
	}
	public void setSetOriginalCompanyId(boolean setOriginalCompanyId) {
		this.setOriginalCompanyId = setOriginalCompanyId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserUuid() {
		return userUuid;
	}
	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}
	public long getOriginalUserId() {
		return originalUserId;
	}
	public void setOriginalUserId(long originalUserId) {
		this.originalUserId = originalUserId;
	}
	public boolean isSetOriginalUserId() {
		return setOriginalUserId;
	}
	public void setSetOriginalUserId(boolean setOriginalUserId) {
		this.setOriginalUserId = setOriginalUserId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getVersionUserId() {
		return versionUserId;
	}
	public void setVersionUserId(long versionUserId) {
		this.versionUserId = versionUserId;
	}
	public String getVersionUserUuid() {
		return versionUserUuid;
	}
	public void setVersionUserUuid(String versionUserUuid) {
		this.versionUserUuid = versionUserUuid;
	}
	public String getVersionUserName() {
		return versionUserName;
	}
	public void setVersionUserName(String versionUserName) {
		this.versionUserName = versionUserName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public long getRepositoryId() {
		return repositoryId;
	}
	public void setRepositoryId(long repositoryId) {
		this.repositoryId = repositoryId;
	}
	public long getFolderId() {
		return folderId;
	}
	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}
	public long getOriginalFolderId() {
		return originalFolderId;
	}
	public void setOriginalFolderId(long originalFolderId) {
		this.originalFolderId = originalFolderId;
	}
	public boolean isSetOriginalFolderId() {
		return setOriginalFolderId;
	}
	public void setSetOriginalFolderId(boolean setOriginalFolderId) {
		this.setOriginalFolderId = setOriginalFolderId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getOriginalMimeType() {
		return originalMimeType;
	}
	public void setOriginalMimeType(String originalMimeType) {
		this.originalMimeType = originalMimeType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOriginalTitle() {
		return originalTitle;
	}
	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getExtraSettings() {
		return extraSettings;
	}
	public void setExtraSettings(String extraSettings) {
		this.extraSettings = extraSettings;
	}
	public long getFileEntryTypeId() {
		return fileEntryTypeId;
	}
	public void setFileEntryTypeId(long fileEntryTypeId) {
		this.fileEntryTypeId = fileEntryTypeId;
	}
	public long getOriginalFileEntryTypeId() {
		return originalFileEntryTypeId;
	}
	public void setOriginalFileEntryTypeId(long originalFileEntryTypeId) {
		this.originalFileEntryTypeId = originalFileEntryTypeId;
	}
	public boolean isSetOriginalFileEntryTypeId() {
		return setOriginalFileEntryTypeId;
	}
	public void setSetOriginalFileEntryTypeId(boolean setOriginalFileEntryTypeId) {
		this.setOriginalFileEntryTypeId = setOriginalFileEntryTypeId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public long getSmallImageId() {
		return smallImageId;
	}
	public void setSmallImageId(long smallImageId) {
		this.smallImageId = smallImageId;
	}
	public long getLargeImageId() {
		return largeImageId;
	}
	public void setLargeImageId(long largeImageId) {
		this.largeImageId = largeImageId;
	}
	public long getCustom1ImageId() {
		return custom1ImageId;
	}
	public void setCustom1ImageId(long custom1ImageId) {
		this.custom1ImageId = custom1ImageId;
	}
	public long getCustom2ImageId() {
		return custom2ImageId;
	}
	public void setCustom2ImageId(long custom2ImageId) {
		this.custom2ImageId = custom2ImageId;
	}
	public long getColumnBitmask() {
		return columnBitmask;
	}
	public void setColumnBitmask(long columnBitmask) {
		this.columnBitmask = columnBitmask;
	}
	public Folder getFolder() {
		return folder;
	}
	public void setFolder(Folder folder) {
		this.folder = folder;
	}
	public DocType getDocType() {
		return docType;
	}
	public void setDocType(DocType docType) {
		this.docType = docType;
	}
	public InputStream getContentStream() {
		return contentStream;
	}
	public void setContentStream(InputStream contentStream) {
		this.contentStream = contentStream;
	}
}
