package org.flowframe.kernel.common.mdm.domain.metamodel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.Type.PersistenceType;

@Entity
public class SingularAttribute
		extends AbstractAttribute
		implements Serializable {
	private boolean isIdentifier;
	private boolean isVersion;
	private boolean isOptional;
	@Enumerated(EnumType.STRING)
	private PersistentAttributeType attributeType;
	
	public SingularAttribute(){
	}

	public SingularAttribute(
			String name,
			Class javaType,
			boolean isIdentifier,
			boolean isVersion,
			boolean isOptional,
			PersistentAttributeType attributeType) {
		super(name, javaType,javaType.getName(),javaType.getSimpleName());
		this.isIdentifier = isIdentifier;
		this.isVersion = isVersion;
		this.isOptional = isOptional;
		this.attributeType = attributeType;
		this.persistenceType = PersistenceType.ENTITY;
	}
	
	public SingularAttribute(
			String name,
			Class javaType,
			boolean isIdentifier,
			boolean isVersion,
			boolean isOptional,
			PersistentAttributeType attributeType,
			EntityType entityType) {
		super(name, javaType,javaType.getName(),javaType.getSimpleName());
		super.setEntityType(entityType);
		this.isIdentifier = isIdentifier;
		this.isVersion = isVersion;
		this.isOptional = isOptional;
		this.attributeType = attributeType;
		this.persistenceType = PersistenceType.ENTITY;
	}
	

	/**
	 * {@inheritDoc}
	 */
	public boolean isId() {
		return isIdentifier;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isVersion() {
		return isVersion;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isOptional() {
		return isOptional;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isAssociation() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isCollection() {
		return false;
	}

	public PersistentAttributeType getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(PersistentAttributeType attributeType) {
		this.attributeType = attributeType;
	}
}
