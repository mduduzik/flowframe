package org.flowframe.kernel.common.mdm.domain.role;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ffsyssecrole")
public class Role extends AbstractRole{
	private long roleId;
	private long companyId;
	private long originalCompanyId;
	private boolean setOriginalCompanyId;
	private long classNameId;
	private long originalClassNameId;
	private boolean setOriginalClassNameId;
	private long classPK;
	private long originalClassPK;
	private boolean setOriginalClassPK;
	private String name;
	private String originalName;
	private String title;
	private String titleCurrentLanguageId;
	private String description;
	private String descriptionCurrentLanguageId;
	private int type;
	private int originalType;
	private boolean setOriginalType;
	private String subtype;
	private String originalSubtype;
	private long columnBitmask;
	
	public Role(){
	}
	
	public Role(String name)
	{
		this.name = name;
	}
	
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
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
	public long getClassNameId() {
		return classNameId;
	}
	public void setClassNameId(long classNameId) {
		this.classNameId = classNameId;
	}
	public long getOriginalClassNameId() {
		return originalClassNameId;
	}
	public void setOriginalClassNameId(long originalClassNameId) {
		this.originalClassNameId = originalClassNameId;
	}
	public boolean isSetOriginalClassNameId() {
		return setOriginalClassNameId;
	}
	public void setSetOriginalClassNameId(boolean setOriginalClassNameId) {
		this.setOriginalClassNameId = setOriginalClassNameId;
	}
	public long getClassPK() {
		return classPK;
	}
	public void setClassPK(long classPK) {
		this.classPK = classPK;
	}
	public long getOriginalClassPK() {
		return originalClassPK;
	}
	public void setOriginalClassPK(long originalClassPK) {
		this.originalClassPK = originalClassPK;
	}
	public boolean isSetOriginalClassPK() {
		return setOriginalClassPK;
	}
	public void setSetOriginalClassPK(boolean setOriginalClassPK) {
		this.setOriginalClassPK = setOriginalClassPK;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitleCurrentLanguageId() {
		return titleCurrentLanguageId;
	}
	public void setTitleCurrentLanguageId(String titleCurrentLanguageId) {
		this.titleCurrentLanguageId = titleCurrentLanguageId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescriptionCurrentLanguageId() {
		return descriptionCurrentLanguageId;
	}
	public void setDescriptionCurrentLanguageId(String descriptionCurrentLanguageId) {
		this.descriptionCurrentLanguageId = descriptionCurrentLanguageId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getOriginalType() {
		return originalType;
	}
	public void setOriginalType(int originalType) {
		this.originalType = originalType;
	}
	public boolean isSetOriginalType() {
		return setOriginalType;
	}
	public void setSetOriginalType(boolean setOriginalType) {
		this.setOriginalType = setOriginalType;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	public String getOriginalSubtype() {
		return originalSubtype;
	}
	public void setOriginalSubtype(String originalSubtype) {
		this.originalSubtype = originalSubtype;
	}
	public long getColumnBitmask() {
		return columnBitmask;
	}
	public void setColumnBitmask(long columnBitmask) {
		this.columnBitmask = columnBitmask;
	}
}
