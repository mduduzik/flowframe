package org.flowframe.ds.domain.validators;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.flowframe.ds.domain.DataSourceField;
import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;


/**
 * A validator describes a check that should be performed on a value the user is trying to save. <p> Validators are
 * specified for DataSource fields via the {@link com.smartgwt.client.data.DataSourceField#getValidators validators}
 * property.  Validators that need not be run on the server can also be specified for a specific {@link
 * com.smartgwt.client.widgets.form.fields.FormItem} or {@link com.smartgwt.client.widgets.grid.ListGridField}. <p> Smart
 * GWT supports a powerful library of {@link com.smartgwt.client.types.ValidatorType ValidatorTypes} which have identical
 * behavior on both the client and the server.   <p>  Beyond this, custom validators can be defined on the client and
 * custom validation logic added on the server.  Note that the <code>regexp</code> and <code>mask</code> validator types
 * are very flexible and can be used to perform virtually any kind of formatting check that doesn't involve some large
 * external dataset. <p> Custom validators can be reused on the client by adding them to the global validator list, via the
 * {@link com.smartgwt.client.widgets.form.validator.Validator#addValidator Validator.addValidator} method.
 * @see com.smartgwt.client.types.ValidatorType
 */
@Entity
@Table(name="sysdsvalidator")
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
public abstract class Validator extends MultitenantBaseEntity {

    public Validator(){
        
    }

    // ********************* Properties / Attributes ***********************

	/**
     * Indicates this validator runs on the client only. <p> Normally, if the server is trying to run validators and finds a
     * validator that it can't execute, for safety reasons validation is considered to have failed.  Use this flag to
     * explicitly mark a validator that only needs to run on the client.
     *
     * @param clientOnly clientOnly Default value is false
     */
    private Boolean clientOnly = false;

  

    /**
     * Normally, all validators defined for a field will be run even if one of the validators has already failed.  However, if
     * <code>stopIfFalse</code> is set, validation will not proceed beyond this validator if the check fails. <P> This is
     * useful to prevent expensive validators from being run unnecessarily, or to allow custom validators that don't need to be
     * robust about handling every conceivable type of value.
     *
     * @param stopIfFalse stopIfFalse Default value is false
     */
    private Boolean stopIfFalse = false;

    /**
     * Indicates that if this validator is not passed, the user should not be allowed to exit the field - focus will be forced
     * back into the field until the error is corrected. <p> This property defaults to {@link
     * com.smartgwt.client.widgets.form.fields.FormItem#getStopOnError stopOnError} if unset. <p> Enabling this property also
     * implies {@link com.smartgwt.client.widgets.form.fields.FormItem#getValidateOnExit validateOnExit} is automatically
     * enabled. If this is a server-based validator, setting this property also implies that {@link
     * com.smartgwt.client.widgets.form.fields.FormItem#getSynchronousValidation synchronousValidation} is forced on.
     *
     * @param stopOnError stopOnError Default value is null
     */
    private Boolean stopOnError = true;
 

    /**
     * If true, validator will be validated when each item's "change" handler is fired as well as when the entire form is
     * submitted or validated. If false, this validator will not fire on the item's "change" handler. <p> Note that this
     * property can also be set at the form/grid or field level; If true at any level and not explicitly false on the
     * validator, the validator will be fired on change - displaying errors and rejecting the change on validation failure.
     *
     * @param validateOnChange validateOnChange Default value is null
     */
    private Boolean validateOnChange = true;

  
    /**
     * Text to display if the value does not pass this validation check. <P> If unspecified, default error messages
     * exist for all built-in validators, and a generic message will be used for a custom validator that is not passed.
     *
     * @param errorMessage errorMessage Default value is null
     */
    private String errorMessage;
    

    /**
     * User-defined list of fields on which this validator depends. Primarily used for validators of type "custom" but can also
     * be used to supplement {@link com.smartgwt.client.widgets.form.validator.Validator#getApplyWhen applyWhen} criteria.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param dependentFields dependentFields Default value is null
     */
    @Embedded
    private Set<DataSourceField> dependentFields = new HashSet<DataSourceField>(); 


    /**
     * Type of the validator. This can be one of the built-in
     *  {@link com.smartgwt.client.types.ValidatorType}. Note that a validator of type
     *  <i>ValidatorType.CUSTOM</i> may be used to support custom validation behavior.
     * @param type validator type
     */
    private ValidatorType type;
    
    protected String typeAsString;
    
    public ValidatorType getType() {
		return type;
	}

    public void setType(ValidatorType type) {
		this.type = type;
	}

	public Set<DataSourceField> getDependentFields() {
		return dependentFields;
	}

	public void setDependentFields(Set<DataSourceField> dependentFields) {
		this.dependentFields = dependentFields;
	}
}



