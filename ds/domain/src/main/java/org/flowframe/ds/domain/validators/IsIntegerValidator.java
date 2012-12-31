package org.flowframe.ds.domain.validators;

import javax.persistence.Entity;

/**
 * Tests whether the value for this field is a whole number. If validator.convertToInteger is true, float values will be
 * converted into integers and validation will succeed.
 */
@Entity
public class IsIntegerValidator extends Validator {

    public IsIntegerValidator() {
    	typeAsString = "isInteger";
    }

    /**
     * Set the convertToInteger.
     *
     * @param convertToInteger the convertToInteger
     */
    private boolean convertToInteger = true;

}
