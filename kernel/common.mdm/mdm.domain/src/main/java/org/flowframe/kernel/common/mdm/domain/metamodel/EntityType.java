package org.flowframe.kernel.common.mdm.domain.metamodel;

import javax.persistence.Entity;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.Type.PersistenceType;

@Entity
public class EntityType extends AbstractIdentifiableType {
	private  String jpaEntityName;
	
	public EntityType() {
		super();
	}
	
	public EntityType(String name, Class javaType,
			AbstractManagedType superType,
			SingularAttribute id, SingularAttribute version,
			String jpaEntityName) {
		super(name, javaType, superType, id, version,PersistenceType.ENTITY);
		this.jpaEntityName = jpaEntityName;
	}

	public EntityType(String name, Class javaType, 
			AbstractManagedType superType,
			String jpaEntityName,PersistentAttributeType persistentAttributeType) {
		super(name, javaType,superType,PersistenceType.ENTITY);
		this.jpaEntityName = jpaEntityName;
	}

	public String getJpaEntityName() {
		return jpaEntityName;
	}

	public void setJpaEntityName(String jpaEntityName) {
		this.jpaEntityName = jpaEntityName;
	}
	
	public String getNameAsDataSourceName()
	{
		return this.name.substring(0,1).toLowerCase()+this.name.substring(1);
	}
}
