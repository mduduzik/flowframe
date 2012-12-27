package org.flowframe.kernel.common.mdm.domain.metamodel;

import javax.persistence.Entity;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.Type.PersistenceType;

/**
 * Subclass used to simply instantiation of singular attributes representing an entity's
 * identifier.
 */

@Entity
public class IdentifierAttribute extends SingularAttribute {
	public IdentifierAttribute(){
	}
	
	public IdentifierAttribute(
			String name,
			Class javaType,
			PersistenceType attributeType,
			PersistentAttributeType persistentAttributeType) {
		super( name, javaType, true, false, false, persistentAttributeType );
	}
}