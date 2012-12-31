package org.flowframe.ds.domain.validators;

import javax.persistence.Entity;

/**
 * Tests whether the value for this field is a floating point number with the appropriate number of decimal places -
 * specified in validator.precision  If the value is of higher precision, if validator.roundToPrecision is specified,
 * the value will be rounded to the specified number of decimal places and validation will pass, otherwise validation
 * will fail.
 */
@Entity
public class FloatPrecisionValidator extends Validator {

    public FloatPrecisionValidator() {
        typeAsString = "floatPrecision";
    }

    /**
     * Set the precision.
     *
     * @param precision the precision
     */
    private int precision;


    /**
     * Set the roundToPrecision.
     *
     * @param roundToPrecision the roundToPrecision
     */
    private int roundToPrecision;
}
