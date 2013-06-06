package org.flowframe.kernel.common.mdm.domain.preferences;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.beanutils.ConvertUtils;
import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;
import org.flowframe.kernel.common.mdm.domain.metamodel.BasicType;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="ffmdmpreferenceitem")
public class EntityPreferenceItem extends MultitenantBaseEntity {
	private static final long serialVersionUID = 1886230028L;
	
	@XmlTransient
	@ManyToOne(targetEntity = EntityPreference.class, cascade = CascadeType.ALL)
	private EntityPreference parentEntityPreference;
	
	private String preferenceKey;
	private String preferenceValue;
	
	@ManyToOne(targetEntity = BasicType.class, fetch=FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn
	private BasicType basicType;
	
	@ManyToOne(targetEntity = EntityPreferenceItemOption.class, fetch=FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn
	private EntityPreferenceItemOption option;	
	
	private int ordinal = 0;
	private Boolean readOnly = false;
	private Boolean hidden = false;
	private int size = 0;//In case of String type
	
	public EntityPreferenceItem() {
	}
	
	public EntityPreferenceItem(String preferenceKey,
			String preferenceValue,
			BasicType basicType,
			int ordinal) {
		this.preferenceKey = preferenceKey;
		this.preferenceValue = preferenceValue;
		this.basicType = basicType;
		this.ordinal = ordinal;
	}	
	
	public EntityPreference getParentEntityPreference() {
		return parentEntityPreference;
	}
	
	public void setParentEntityPreference(EntityPreference parentEntityPreference) {
		this.parentEntityPreference = parentEntityPreference;
	}

	public String getPreferenceKey() {
		return preferenceKey;
	}

	public void setPreferenceKey(String preferenceKey) {
		this.preferenceKey = preferenceKey;
	}

	public String getPreferenceValue() {
		return preferenceValue;
	}

	public void setPreferenceValue(String preferenceValue) {
		this.preferenceValue = preferenceValue;
	}

	public BasicType getBasicType() {
		return basicType;
	}

	public void setBasicType(BasicType basicType) {
		this.basicType = basicType;
	}
	
	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}
	

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public EntityPreferenceItemOption getOption() {
		return option;
	}

	public void setOption(EntityPreferenceItemOption option) {
		this.option = option;
	}

	@Transient
	public Object getTypedValue() throws ClassNotFoundException {
		if (getPreferenceValue() == null)
			return null;
		else
			return ConvertUtils.convert(getPreferenceValue(),this.basicType.getJavaType());
	}
	
	@Transient
	public void setTypedValue(Object typedValue) throws ClassNotFoundException {
		setPreferenceValue(ConvertUtils.convert(typedValue));
	}	
}
