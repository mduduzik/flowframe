package org.flowframe.ds.domain.validators;

import javax.persistence.Entity;

/**
 * Determine whether a string value does not contain some substring specified via validator.substring.
 */
@Entity
public class DoesntContainValidator extends Validator {
	
	private String substring;

    public DoesntContainValidator() {
        typeAsString = "doesntContain";
    }
}
