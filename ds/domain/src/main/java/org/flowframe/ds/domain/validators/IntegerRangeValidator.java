package org.flowframe.ds.domain.validators;

import javax.persistence.Entity;

/**
 * Tests whether the value for this field is a whole number within the range specified. The max and min properties on
 * the validator are used to determine the acceptable range..
 */
@Entity
public class IntegerRangeValidator extends Validator {

    public IntegerRangeValidator() {
        typeAsString = "integerRange";
    }

    /**
     * Set the min.
     *
     * @param min the min
     */
    private int min;


    /**
     * Set the max.
     *
     * @param max the max
     */
    private int max;
}
