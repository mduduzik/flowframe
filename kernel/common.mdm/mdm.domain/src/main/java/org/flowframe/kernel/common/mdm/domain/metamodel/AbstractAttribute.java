package org.flowframe.kernel.common.mdm.domain.metamodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.metamodel.Type.PersistenceType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="ffsysmmattribute")
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
public abstract class AbstractAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateCreated = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateLastUpdated = new Date();
    
	protected String name;
	protected transient Class javaType;
	
	protected String entityJavaType;
    protected String entityJavaSimpleType;
    
    protected PersistenceType persistenceType;
    
	@OneToOne
	private EntityType entityType;
	
	@ManyToOne
	private EntityType parentEntityType;	

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType elementType) {
		this.entityType = elementType;
	}

	public EntityType getParentEntityType() {
		return parentEntityType;
	}

	public void setParentEntityType(EntityType parentEntityType) {
		this.parentEntityType = parentEntityType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class getJavaType() throws ClassNotFoundException {
		if (this.javaType == null)
		{
			if (entityJavaType.equals("byte")) return byte.class;
			if (entityJavaType.equals("short")) return short.class;
			if (entityJavaType.equals("int")) return int.class;
			if (entityJavaType.equals("long")) return long.class;
			if (entityJavaType.equals("char")) return char.class;
			if (entityJavaType.equals("float")) return float.class;
			if (entityJavaType.equals("double")) return double.class;
			if (entityJavaType.equals("boolean")) return boolean.class;
			if (entityJavaType.equals("void")) return void.class;
			else
				return Class.forName(entityJavaType);
		}
		return this.javaType;
	}

	public void setJavaType(Class javaType) {
		this.javaType = javaType;
	}

	public String getEntityJavaType() {
		return entityJavaType;
	}

	public void setEntityJavaType(String entityJavaType) {
		this.entityJavaType = entityJavaType;
	}

	public String getEntityJavaSimpleType() {
		return entityJavaSimpleType;
	}

	public void setEntityJavaSimpleType(String entityJavaSimpleType) {
		this.entityJavaSimpleType = entityJavaSimpleType;
	}
	
	

	public PersistenceType getPersistenceType() {
		return persistenceType;
	}

	public void setPersistenceType(PersistenceType persistenceType) {
		this.persistenceType = persistenceType;
	}

	public AbstractAttribute(String name, Class javaType,
			String entityJavaType, String entityJavaSimpleType) {
		super();
		this.name = name;
		this.javaType = javaType;
		this.entityJavaType = entityJavaType;
		this.entityJavaSimpleType = entityJavaSimpleType;
	}
	
	public AbstractAttribute(String name) {
		super();
		this.name = name;
	}

	public AbstractAttribute() {
	}	
}
