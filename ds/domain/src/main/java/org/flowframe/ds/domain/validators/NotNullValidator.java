package org.flowframe.ds.domain.validators;

import javax.persistence.Entity;

@Entity
public class NotNullValidator extends Validator {
	
	public NotNullValidator() {
		typeAsString = "notnull";
	}
}
