package org.flowframe.ui.component.domain;

import javax.persistence.Entity;

import org.flowframe.ds.domain.DataSource;
import org.flowframe.ds.domain.DataSourceField;

@Entity
public abstract class AbstractFieldComponent extends AbstractComponent implements Sizeable {

    /**
     * Connected data-source field.
     */
    private DataSourceField dataSourceField = null;

    /**
     * Auto commit mode.
     */
    private boolean writeThroughMode = true;

    /**
     * Reads the value from data-source, when it is not modified.
     */
    private boolean readThroughMode = true;

    /**
     * Is the field modified but not committed.
     */
    private boolean modified = false;

    /**
     * Flag to indicate that the field is currently committing its value to the
     * datasource.
     */
    private boolean committingValueToDataSource = false;


    /**
     * Are the invalid values allowed in fields ?
     */
    private boolean invalidAllowed = true;

    /**
     * Are the invalid values committed ?
     */
    private boolean invalidCommitted = false;

    /**
     * The tab order number of this field.
     */
    private int tabIndex = 0;

    /**
     * Required field.
     */
    private boolean required = false;

    /**
     * The error message for the exception that is thrown when the field is
     * required but empty.
     */
    private String requiredError = "";

    /**
     * Is automatic validation enabled.
     */
    private boolean validationVisible = true;
    
    public AbstractFieldComponent() {
    	super("field");
    }
    
    public AbstractFieldComponent(String typeId) {
    	super(typeId);
    }    

	public AbstractFieldComponent(DataSourceField dataSourceField,
			boolean required, String requiredError, boolean validationVisible) {
		this();
		this.dataSourceField = dataSourceField;
		this.required = required;
		this.requiredError = requiredError;
		this.validationVisible = validationVisible;
	}

	public DataSourceField getDataSourceField() {
		return dataSourceField;
	}

	public void setDataSourceField(DataSourceField dataSourceField) {
		this.dataSourceField = dataSourceField;
	}

	public boolean isWriteThroughMode() {
		return writeThroughMode;
	}

	public void setWriteThroughMode(boolean writeThroughMode) {
		this.writeThroughMode = writeThroughMode;
	}

	public boolean isReadThroughMode() {
		return readThroughMode;
	}

	public void setReadThroughMode(boolean readThroughMode) {
		this.readThroughMode = readThroughMode;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public boolean isCommittingValueToDataSource() {
		return committingValueToDataSource;
	}

	public void setCommittingValueToDataSource(boolean committingValueToDataSource) {
		this.committingValueToDataSource = committingValueToDataSource;
	}

	public boolean isInvalidAllowed() {
		return invalidAllowed;
	}

	public void setInvalidAllowed(boolean invalidAllowed) {
		this.invalidAllowed = invalidAllowed;
	}

	public boolean isInvalidCommitted() {
		return invalidCommitted;
	}

	public void setInvalidCommitted(boolean invalidCommitted) {
		this.invalidCommitted = invalidCommitted;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getRequiredError() {
		return requiredError;
	}

	public void setRequiredError(String requiredError) {
		this.requiredError = requiredError;
	}

	public boolean isValidationVisible() {
		return validationVisible;
	}

	public void setValidationVisible(boolean validationVisible) {
		this.validationVisible = validationVisible;
	}
}
