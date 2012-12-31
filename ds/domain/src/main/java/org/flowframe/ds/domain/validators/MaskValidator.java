package org.flowframe.ds.domain.validators;

import javax.persistence.Entity;

/**
 * Validate against a regular expression mask, specified as validator.mask. If validation is successful a transformation
 * can also be specified via the validator.transformTo property. This should be set to a string in the standard format
 * for string replacement via the native JavaScript replace()  method.
 */
@Entity
public class MaskValidator extends Validator {

    public MaskValidator() {
    	typeAsString = "mask";
    }

    /**
     * Set the mask.  Eg : ^\s*(1?)\s*\(?\s*(\d{3})\s*\)?\s*-?\s*(\d{3})\s*-?\s*(\d{4})\s*$
     *
     * @param mask the mask
     */
    private String mask;


    /**
     * Set the transformTo. Eg : $1($2) $3 - $4
     *
     * @param transformTo the transformTo
     */
    private String transformTo;

}
