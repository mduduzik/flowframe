package org.flowframe.ds.domain.validators;

import javax.persistence.Entity;

/**
 * Determine whether a string value contains some substring specified via {@link #setSubstring(String) substring}.
 */
@Entity
public class ContainsValidator extends Validator {
	
	private String substring;

    public ContainsValidator() {
        typeAsString = "contains";
    }
    
    public ContainsValidator(String substring) {
        this();
        this.substring = substring;
    }    
    
}
