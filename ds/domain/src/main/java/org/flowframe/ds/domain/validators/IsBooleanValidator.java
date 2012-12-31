package org.flowframe.ds.domain.validators;

import javax.persistence.Entity;

/**
 * Validation will fail if this field is non-empty and has a non-boolean value.
 */
@Entity
public class IsBooleanValidator extends Validator {

    public IsBooleanValidator() {
    	typeAsString = "isBoolean";
    }

}
