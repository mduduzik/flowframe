package org.flowframe.ui.component.domain.form;

import javax.persistence.Entity;

import org.flowframe.ds.domain.DataSourceField;
import org.flowframe.ui.component.domain.AbstractFieldComponent;

@Entity
public abstract class AbstractTextFieldComponent extends AbstractFieldComponent {
    /**
     * Null representation.
     */
    private String nullRepresentation = "null";
    /**
     * Is setting to null from non-null value allowed by setting with null
     * representation .
     */
    private boolean nullSettingAllowed = false;
    /**
     * Maximum character count in text field.
     */
    private int maxLength = -1;

    /**
     * Number of visible columns in the TextField.
     */
    private int columns = 0;

    /**
     * The prompt to display in an empty field. Null when disabled.
     */
    private String inputPrompt = null;

	public String getNullRepresentation() {
		return nullRepresentation;
	}

	public void setNullRepresentation(String nullRepresentation) {
		this.nullRepresentation = nullRepresentation;
	}

	public boolean isNullSettingAllowed() {
		return nullSettingAllowed;
	}

	public void setNullSettingAllowed(boolean nullSettingAllowed) {
		this.nullSettingAllowed = nullSettingAllowed;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public String getInputPrompt() {
		return inputPrompt;
	}

	public void setInputPrompt(String inputPrompt) {
		this.inputPrompt = inputPrompt;
	}

	public AbstractTextFieldComponent(String typeId) {
		super(typeId);
	}
	
}
