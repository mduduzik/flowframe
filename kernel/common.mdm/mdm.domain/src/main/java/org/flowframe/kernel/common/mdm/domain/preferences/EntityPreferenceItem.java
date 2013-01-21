package org.flowframe.kernel.common.mdm.domain.preferences;

import javax.persistence.CascadeType;
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

	@ManyToOne(targetEntity = EntityPreference.class, cascade = CascadeType.ALL)
	private EntityPreference parentEntityPreference;
	
	private String preferenceKey;
	private String preferenceValue;
	
	public EntityPreferenceItem() {
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
}
