package org.flowframe.kernel.common.mdm.domain.metamodel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.metamodel.Type.PersistenceType;

@Entity
public class BasicAttribute
		extends AbstractAttribute
		implements Serializable {
	private boolean isIdentifier;
	private boolean isVersion;
	private boolean isOptional;
	private boolean isId;
	
	public BasicAttribute() {
	}
	
	public BasicAttribute(String string, Class class1, EntityType targetEntityType) {
		super();
		super.persistenceType = PersistenceType.BASIC;
		this.isId = false;
	}

	public BasicAttribute(
			boolean isid,
			String name,
			Class javaType,
			EntityType parentEntityType) {
		super(name, javaType,javaType.getName(),javaType.getSimpleName());
		setParentEntityType(parentEntityType);
		this.persistenceType = PersistenceType.BASIC;
		this.isId = isid;
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
	public PersistenceType getType() {
		return persistenceType;
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

	public void setId(boolean isId) {
		this.isId = isId;
	}
	
	
}
