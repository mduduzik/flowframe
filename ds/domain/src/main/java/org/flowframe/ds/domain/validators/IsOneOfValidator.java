package org.flowframe.ds.domain.validators;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;

/**
 * Tests whether the value for this field matches any value from an arbitrary list of acceptable values. The set of
 * acceptable values is specified via the list property on the validator, which should be set to an array of values. If
 * validator.list is not supplied, the valueMap for the field will be used. If there is no valueMap, not providing
 * validator.list is an error.
 */
@Entity
public class IsOneOfValidator extends Validator {

    public IsOneOfValidator() {
    	typeAsString = "isOneOf";
    }

    /**
     * Set the list.
     *
     * @param list the list
     */
    @Embedded
    private Set<String> list = new HashSet<String>(); 

}
