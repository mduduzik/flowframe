package org.flowframe.kernel.common.mdm.domain.preferences;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="ffmdmpreference")
public class EntityPreference extends MultitenantBaseEntity {
	private static final long serialVersionUID = 1112120191L;
	
	@OneToMany(mappedBy = "parentEntityPreference", cascade = CascadeType.ALL, targetEntity = EntityPreferenceItem.class)
    private Set<EntityPreferenceItem> items = new java.util.HashSet<EntityPreferenceItem>();

	public EntityPreference() {
	}
	
	public void setItems(Set<EntityPreferenceItem> items) {
		this.items = items;
	}

	public Set<EntityPreferenceItem> getItems() {
		return items;
	}
}
