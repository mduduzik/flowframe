package org.flowframe.ds.domain.validators;

import javax.persistence.Entity;

/**
 * Tests whether the value for this field is a valid floating point number.
 */
@Entity
public class IsFloatValidator extends Validator {

    public IsFloatValidator() {
    	typeAsString = "isFloat";
    }

}
