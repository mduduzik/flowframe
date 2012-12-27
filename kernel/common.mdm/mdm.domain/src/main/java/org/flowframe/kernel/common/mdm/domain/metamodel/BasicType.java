package org.flowframe.kernel.common.mdm.domain.metamodel;

import javax.persistence.Entity;
import javax.persistence.metamodel.Type.PersistenceType;

@Entity
public class BasicType extends AbstractType {
	public BasicType(){
		super();
	}	

	public BasicType(String name, Class javaType) {
		super(name, javaType, javaType.getName(), javaType.getSimpleName(),PersistenceType.BASIC);
	}

}
