package org.flowframe.kernel.common.mdm.domain.metamodel;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.metamodel.Type.PersistenceType;

@Entity
public abstract class AbstractIdentifiableType extends AbstractManagedType {

	@OneToOne
	private SingularAttribute idAttribute;

	@OneToOne
	private SingularAttribute versionAttribute;

	@OneToMany
	private Set<SingularAttribute> idClassAttributes = new HashSet<SingularAttribute>();

	
	public AbstractIdentifiableType() {
		super();
	}
	
	public AbstractIdentifiableType(String name, Class javaType,
			AbstractManagedType superType,PersistenceType persistentType) {
		super(name, javaType, superType,persistentType);
	}
	
	public AbstractIdentifiableType(String name, Class javaType,
			AbstractManagedType superType,
			SingularAttribute id, SingularAttribute version,PersistenceType persistentType) {
		super(name, javaType, superType,persistentType);
		this.idAttribute = id;
		this.versionAttribute = version;		
	}


}
