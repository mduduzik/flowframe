package org.flowframe.ds.domain.validators;

import javax.persistence.Entity;

/**
 * Tests whether the value for this field is a floating point number within the range specified. The max and min
 * properties on the validator are used to determine the acceptable range. Note that the errorMessage for this validator
 * will be evaluated as a dynamicString - text within ${...} will be evaluated as JS code when the message is displayed,
 * with max and min available as variables mapped to validator.max and validator.min.
 */
@Entity
public class FloatRangeValidator extends Validator {

    public FloatRangeValidator() {
        typeAsString = "floatRange";
    }

    /**
     * Set the min.
     *
     * @param min the min
     */
    private float min;

     /**
     * Set the max.
     *
     * @param max the max
     */
    private float max;
}
