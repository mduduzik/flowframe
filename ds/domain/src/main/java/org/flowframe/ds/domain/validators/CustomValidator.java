package org.flowframe.ds.domain.validators;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.flowframe.ds.domain.DataSourceField;

@MappedSuperclass
public abstract class CustomValidator extends Validator {

    public CustomValidator() {
        typeAsString = "custom";
    }

    @ManyToOne
    protected DataSourceField dataSourceField;

    /**
     * Add custom validation logic by overriding this method. Access to the FormItem or DataSourceField on which the validator was
     * declared can be obtained by the {@link #getFormItem()} and {@link #getDataSourceField()} methods respectively and the field values for
     * record being validated can be obtained by calling {@link #getRecord()}.
     *
     * @param value value to validate
     * @return true if valid
     */
    protected abstract boolean condition(Object value);

    /**
     * FormItem on which this validator was declared. May be null if the item is a DataSourceField in which case {@link #getDataSourceField()} should be called.
     *
     * NOTE: FormItem will not be available during a save performed without a form (eg programmatic save) or if the field.
     *
     * @return FormItem on which this validator was declared.
     */


    /**
     * DataSourceField on which this validator was declared. May be null if the item is a FormItem in which case {@link #getFormItem()} should be called.
     *
     * @return DataSourceField on which this validator was declared.
     */
    public DataSourceField getDataSourceField() {
        return dataSourceField;
    }
}
