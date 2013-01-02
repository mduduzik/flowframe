package org.flowframe.ui.component.domain.form;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.flowframe.ds.domain.DataSourceField;
import org.flowframe.ui.component.domain.AbstractFieldComponent;

@Entity
public class ConfirmActualsFieldSetFieldComponent extends AbstractFieldComponent implements Serializable {
	private static final long serialVersionUID = -6947507271706691279L;

	@OneToOne
	private ConfirmActualsFieldSetComponent fieldSet;
	
	@OneToOne
	private DataSourceField expectedDataSourceField;
	
	@OneToOne
	private DataSourceField actualDataSourceField;
	
	private int ordinal;
	
	public ConfirmActualsFieldSetFieldComponent() {
		super("fieldSetField");
		this.ordinal = -1;
	}
	
	public ConfirmActualsFieldSetFieldComponent(int ordinal) {
		super("fieldSetField");
		this.ordinal = ordinal;
	}
	
	public ConfirmActualsFieldSetFieldComponent(int ordinal, DataSourceField expectedDataSourceField, DataSourceField actualDataSourceField) {
		this(ordinal);
		this.expectedDataSourceField = expectedDataSourceField;
		this.actualDataSourceField = actualDataSourceField;
	}

	public ConfirmActualsFieldSetComponent getFieldSet() {
		return fieldSet;
	}

	public void setFieldSet(ConfirmActualsFieldSetComponent fieldSet) {
		this.fieldSet = fieldSet;
	}

	public DataSourceField getExpectedDataSourceField() {
		return expectedDataSourceField;
	}

	public void setExpectedDataSourceField(DataSourceField dsField) {
		this.expectedDataSourceField = dsField;
	}
	
	public DataSourceField getActualDataSourceField() {
		return actualDataSourceField;
	}

	public void setActualDataSourceField(DataSourceField dsField) {
		this.actualDataSourceField = dsField;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}
	
	public boolean isRequired() {
		if (this.expectedDataSourceField != null) {
			return this.expectedDataSourceField.getRequired();
		}
		return false;
	}
}
