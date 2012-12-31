package org.flowframe.ds.domain.validators;

import javax.persistence.Entity;

/**
 * Tests whether the value for this field matches the value of some other field. The field to compare against is
 * specified via the otherField property on the validator object (should be set to a field name). Note this validator
 * type is only supported for items being edited within a DynamicForm - it cannot be applied to a ListGrid field.
 */
@Entity
public class MatchesFieldValidator extends Validator {

    public MatchesFieldValidator() {
    	typeAsString = "matchesField";
    }

    /**
     * Set the otherField.
     *
     * @param otherField the otherField
     */
    private String otherField;
}
