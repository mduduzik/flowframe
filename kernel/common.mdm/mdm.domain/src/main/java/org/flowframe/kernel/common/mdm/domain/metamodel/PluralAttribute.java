package org.flowframe.kernel.common.mdm.domain.metamodel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.Type.PersistenceType;

@Entity
public abstract class PluralAttribute
		extends AbstractAttribute
		implements Serializable {
	
	@Enumerated(EnumType.STRING)
	private PersistentAttributeType attributeType;

	/**
	 * {@inheritDoc}
	 */
	public boolean isAssociation() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isCollection() {
		return true;
	}
	
	public PluralAttribute(){
		super(null);
	}
	
	public PluralAttribute(String name, EntityType elementType, EntityType parentEntityType, PersistentAttributeType at) {
		super(name);
		setEntityType(elementType);
		setParentEntityType(parentEntityType);
		this.attributeType = at;
		this.persistenceType = PersistenceType.ENTITY;
	}

	public PersistentAttributeType getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(PersistentAttributeType attributeType) {
		this.attributeType = attributeType;
	}	
}
