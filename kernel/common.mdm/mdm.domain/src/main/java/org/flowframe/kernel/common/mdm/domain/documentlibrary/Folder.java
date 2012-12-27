package org.flowframe.kernel.common.mdm.domain.documentlibrary;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sysdlfolder")
public class Folder extends AbstractDocument {

	@OneToMany(targetEntity=FileEntry.class, mappedBy="folder",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Set<FileEntry> files = new HashSet<FileEntry>();
	
	private String uuid;
	private String originalUuid;
	private long folderId;
	private long groupId;
	private long originalGroupId;
	private boolean setOriginalGroupId;
	private long companyId;
	private long originalCompanyId;
	private boolean setOriginalCompanyId;
	private long userId;
	private String userUuid;
	private String userName;
	private Date createDate;
	private Date modifiedDate;
	private long repositoryId;
	private long originalRepositoryId;
	private boolean setOriginalRepositoryId;
	private boolean mountPoint;
	private boolean originalMountPoint;
	private boolean setOriginalMountPoint;
	private long parentFolderId;
	private long originalParentFolderId;
	private boolean setOriginalParentFolderId;
	private String name;
	private String originalName;
	private String description;
	private Date lastPostDate;
	private long defaultFileEntryTypeId;
	private boolean overrideFileEntryTypes;
	private long columnBitmask;
	public Set<FileEntry> getFiles() {
		return files;
	}
	public void setFiles(Set<FileEntry> files) {
		this.files = files;
	}
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
	public long getFolderId() {
		return folderId;
	}
	public void setFolderId(long folderId) {
		this.folderId = folderId;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public long getOriginalRepositoryId() {
		return originalRepositoryId;
	}
	public void setOriginalRepositoryId(long originalRepositoryId) {
		this.originalRepositoryId = originalRepositoryId;
	}
	public boolean isSetOriginalRepositoryId() {
		return setOriginalRepositoryId;
	}
	public void setSetOriginalRepositoryId(boolean setOriginalRepositoryId) {
		this.setOriginalRepositoryId = setOriginalRepositoryId;
	}
	public boolean isMountPoint() {
		return mountPoint;
	}
	public void setMountPoint(boolean mountPoint) {
		this.mountPoint = mountPoint;
	}
	public boolean isOriginalMountPoint() {
		return originalMountPoint;
	}
	public void setOriginalMountPoint(boolean originalMountPoint) {
		this.originalMountPoint = originalMountPoint;
	}
	public boolean isSetOriginalMountPoint() {
		return setOriginalMountPoint;
	}
	public void setSetOriginalMountPoint(boolean setOriginalMountPoint) {
		this.setOriginalMountPoint = setOriginalMountPoint;
	}
	public long getParentFolderId() {
		return parentFolderId;
	}
	public void setParentFolderId(long parentFolderId) {
		this.parentFolderId = parentFolderId;
	}
	public long getOriginalParentFolderId() {
		return originalParentFolderId;
	}
	public void setOriginalParentFolderId(long originalParentFolderId) {
		this.originalParentFolderId = originalParentFolderId;
	}
	public boolean isSetOriginalParentFolderId() {
		return setOriginalParentFolderId;
	}
	public void setSetOriginalParentFolderId(boolean setOriginalParentFolderId) {
		this.setOriginalParentFolderId = setOriginalParentFolderId;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getLastPostDate() {
		return lastPostDate;
	}
	public void setLastPostDate(Date lastPostDate) {
		this.lastPostDate = lastPostDate;
	}
	public long getDefaultFileEntryTypeId() {
		return defaultFileEntryTypeId;
	}
	public void setDefaultFileEntryTypeId(long defaultFileEntryTypeId) {
		this.defaultFileEntryTypeId = defaultFileEntryTypeId;
	}
	public boolean isOverrideFileEntryTypes() {
		return overrideFileEntryTypes;
	}
	public void setOverrideFileEntryTypes(boolean overrideFileEntryTypes) {
		this.overrideFileEntryTypes = overrideFileEntryTypes;
	}
	public long getColumnBitmask() {
		return columnBitmask;
	}
	public void setColumnBitmask(long columnBitmask) {
		this.columnBitmask = columnBitmask;
	}
}
