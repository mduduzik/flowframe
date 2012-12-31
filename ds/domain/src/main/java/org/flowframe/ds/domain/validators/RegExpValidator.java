package org.flowframe.ds.domain.validators;

import javax.persistence.Entity;

/**
 * Regexp type validators will determine whether the value specified matches a given regular expression. The expression
 * should be specified on the validator object as the <i>expression</i> property.
 */
@Entity
public class RegExpValidator extends Validator {

    public RegExpValidator() {
    	typeAsString = "regexp";
    }

    /**
     * Constructor.
     *
     * @param expression the regular expression
     */
    public RegExpValidator(String expression) {
        this();
        this.expression = expression;
    }

    /**
     * Set the expression.
     *
     * @param expression the expression
     */
    private String expression;
}
