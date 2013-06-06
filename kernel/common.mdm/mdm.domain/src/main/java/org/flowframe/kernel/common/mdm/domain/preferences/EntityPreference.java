package org.flowframe.kernel.common.mdm.domain.preferences;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="ffmdmpreference")
public class EntityPreference extends MultitenantBaseEntity {
	private static final long serialVersionUID = 1112120191L;
	
	@XmlTransient
	@Transient
	private Map<String,EntityPreferenceItem> keyToItemMap = null;
	
	@XmlTransient
	@OneToMany(mappedBy = "parentEntityPreference", fetch=FetchType.EAGER,cascade = CascadeType.ALL, targetEntity = EntityPreferenceItem.class)
    private Set<EntityPreferenceItem> items = new java.util.HashSet<EntityPreferenceItem>();

	public EntityPreference() {
	}
	
	public void setItems(Set<EntityPreferenceItem> items) {
		this.items = items;
	}

	public Set<EntityPreferenceItem> getItems() {
		return items;
	}
	
	@Transient
	public Collection<String> getVisibleItemsByKeyName() {
		Collection<String> keys = new HashSet<String>();
		for (EntityPreferenceItem epi : getItems()) {
			if (!epi.getHidden())
				keys.add(epi.getPreferenceKey());
		}
		return keys;
	}
	
	@Transient
	public EntityPreferenceItem getItemByKeyName(String keyName) {
		if (keyToItemMap == null)
		{
			keyToItemMap = new HashMap<String,EntityPreferenceItem>();
			for (EntityPreferenceItem epi : getItems()) {
				if (!epi.getHidden())
					keyToItemMap.put(epi.getPreferenceKey(),epi);
			}			
		}
		
		return keyToItemMap.get(keyName);
	}
	
	@SuppressWarnings("unchecked")
	@Transient
	public <T> T getItemTypedValue(String keyName, T defaultValue) throws ClassNotFoundException {
		EntityPreferenceItem item = getItemByKeyName(keyName);
		if (item == null)
			return defaultValue;
		else
			return (T)item.getTypedValue();
	}	
	
	@Transient
	public <T> void setItemTypedValue(String keyName, T newValue) throws ClassNotFoundException {
		EntityPreferenceItem item = getItemByKeyName(keyName);
		item.setTypedValue(newValue);
	}		
}
