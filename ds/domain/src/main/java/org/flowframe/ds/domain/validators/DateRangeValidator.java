package org.flowframe.ds.domain.validators;

import java.util.Date;

import javax.persistence.Entity;

/**
 * Tests whether the value for a date field is within the range specified. Range is inclusive, and is specified via
 * validator.min and validator.max, which should be dates.
 * <p/>
 * Note that the errorMessage for this validator will be evaluated as a dynamicString - text within ${...} will be
 * evaluated as JS code when the message is displayed, with max and min available as variables mapped to validator.max
 * and validator.min.
 */
@Entity
public class DateRangeValidator extends Validator {
	


    public DateRangeValidator() {
        typeAsString = "dateRange";
    }

    /**
     * Set the min.
     *
     * @param min the min
     */
	private Date min;
	

    /**
     * Set the max.
     *
     * @param max the max
     */
	private Date max;

}
