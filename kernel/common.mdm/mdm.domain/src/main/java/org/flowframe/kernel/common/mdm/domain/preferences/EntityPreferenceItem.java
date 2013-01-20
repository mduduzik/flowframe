package org.flowframe.kernel.common.mdm.domain.preferences;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="ffmdmpreferenceitem")
public class EntityPreferenceItem extends MultitenantBaseEntity {
	private static final long serialVersionUID = 1886230028L;

	@ManyToOne(targetEntity = EntityPreference.class)
	private EntityPreference parentEntityPreference;
	
	private String key;
	private String value;
	
	public EntityPreference getParentEntityPreference() {
		return parentEntityPreference;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setParentEntityPreference(EntityPreference parentEntityPreference) {
		this.parentEntityPreference = parentEntityPreference;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
