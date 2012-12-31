package org.flowframe.ds.domain.validators;

import javax.persistence.Entity;

/**
 * This validator type applies to string values only. If the value is a string value validation will fail if the strings
 * length falls outside the range specified by validator.max and validator.min. Note that non-string values will always
 * pass validation by this validator type.
 */
@Entity
public class LengthRangeValidator extends Validator {

    public LengthRangeValidator() {
    	typeAsString = "lengthRange";
    }

    /**
     * Set the min.
     *
     * @param min the min
     */
    private Integer min;

    /**
     * Set the max.
     *
     * @param max the max
     */
    private Integer max;
}
