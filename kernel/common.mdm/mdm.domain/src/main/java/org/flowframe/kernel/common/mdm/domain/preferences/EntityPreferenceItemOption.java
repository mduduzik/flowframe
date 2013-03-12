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

import org.apache.commons.beanutils.ConvertUtils;
import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;
import org.flowframe.kernel.common.mdm.domain.metamodel.BasicType;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="ffmdmpreferenceitemoption")
public class EntityPreferenceItemOption extends MultitenantBaseEntity {
	private static final long serialVersionUID = 1886230028L;

	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="ffmdmpreferenceitemoption_elements")
	private Set<String> elements = new HashSet<String>();
	
	public EntityPreferenceItemOption() {
	}
	
	public EntityPreferenceItemOption(String name) {
		this.name = name;
	}

	public Set<String> getElements() {
		return elements;
	}

	public void setElements(Set<String> elements) {
		this.elements = elements;
	}	
}
