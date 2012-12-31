package org.flowframe.ds.domain.validators;

import javax.persistence.Entity;

/**
 * Determine whether a string value contains some substring multiple times. The substring to check for is specified via
 * validator.substring. The validator.operator property allows you to specify how to test the number of substring
 * occurrances. Valid values for this property are ==, !=, <, <=, >, >=.The number of matches to check for is specified
 * via validator.count.
 */
@Entity
public class StringCountValidator extends Validator {

    public StringCountValidator() {
    	typeAsString = "requiredIf";
    }

    /**
     * Set the substring.
     *
     * @param substring the substring
     */
    private String substring;
    /**
     * Set the operator.
     *
     * @param operator the operator
     */
    private String operator;

    /**
     * Set the count.
     *
     * @param count the count
     */
    private int count;
}
