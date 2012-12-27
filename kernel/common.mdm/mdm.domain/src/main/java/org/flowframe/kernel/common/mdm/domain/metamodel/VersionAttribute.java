package org.flowframe.kernel.common.mdm.domain.metamodel;

import javax.persistence.Entity;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.Type.PersistenceType;

/**
 * Subclass used to simply instantiation of singular attributes representing an entity's
 * version.
 */

@Entity
public class VersionAttribute extends SingularAttribute {
	public VersionAttribute(){
	}
	
	public VersionAttribute(
			String name,
			Class javaType,
			PersistenceType attributeType,
			PersistentAttributeType persistentAttributeType) {
		super( name, javaType, false, true, false, persistentAttributeType );
	}
}
