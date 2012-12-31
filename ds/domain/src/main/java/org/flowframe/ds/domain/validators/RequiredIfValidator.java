package org.flowframe.ds.domain.validators;

import javax.persistence.Entity;

/**
 * <br>RequiredIf type validators should be specified with an <code>expression</code> which takes three parameters: <ul>
 * <li>item - the DynamicForm item on which the error occurred (may be null)</li> <li>validator - a pointer to the
 * validator object</li> <li>value - the value of the field in question</li> </ul>
 * <p/>
 * When validation is perfomed, the expression will be evaluated - if it returns <code>true</code>, the field will be
 * treated as a required field, so validation will fail if the field has no value.
 */
@Entity
public class RequiredIfValidator extends Validator {

    public RequiredIfValidator() {
    	typeAsString = "requiredIf";
    }

    public RequiredIfValidator(RequiredIfFunction expression) {
        this();
        //setExpression(expression);
    }
}
