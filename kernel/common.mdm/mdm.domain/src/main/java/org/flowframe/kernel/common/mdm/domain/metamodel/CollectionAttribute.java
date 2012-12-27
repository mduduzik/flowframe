package org.flowframe.kernel.common.mdm.domain.metamodel;

import javax.persistence.Entity;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.PluralAttribute.CollectionType;

@Entity
public class CollectionAttribute extends PluralAttribute {
	
	public CollectionAttribute(){
	}
	
	
	public CollectionAttribute(String name, EntityType elementType, EntityType parentEntityType,PersistentAttributeType at) {
		super(name, elementType,parentEntityType,at);
	}

	/**
	 * {@inheritDoc}
	 */
	public CollectionType getCollectionType() {
		return CollectionType.COLLECTION;
	}
}
