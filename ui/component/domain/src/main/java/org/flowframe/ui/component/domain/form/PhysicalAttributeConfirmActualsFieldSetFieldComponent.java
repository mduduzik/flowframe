package org.flowframe.ui.component.domain.form;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.flowframe.ds.domain.DataSourceField;
import org.flowframe.ui.component.domain.AbstractFieldComponent;

@Entity
public class PhysicalAttributeConfirmActualsFieldSetFieldComponent extends AbstractFieldComponent implements Serializable {
	private static final long serialVersionUID = -6947507271706691279L;

	@OneToOne
	private PhysicalAttributeConfirmActualsFieldSetComponent fieldSet;
	
	@OneToOne
	private DataSourceField expectedDataSourceField;
	
	@OneToOne
	private DataSourceField expectedUnitDataSourceField;
	
	@OneToOne
	private DataSourceField actualDataSourceField;
	
	@OneToOne
	private DataSourceField actualUnitDataSourceField;
	
	private int ordinal;
	
	public PhysicalAttributeConfirmActualsFieldSetFieldComponent() {
		super("fieldSetField");
		this.ordinal = -1;
	}
	
	public PhysicalAttributeConfirmActualsFieldSetFieldComponent(int ordinal) {
		super("fieldSetField");
		this.ordinal = ordinal;
	}
	
	public PhysicalAttributeConfirmActualsFieldSetFieldComponent(int ordinal, DataSourceField expectedDataSourceField, DataSourceField expectedUnitDataSourceField, DataSourceField actualDataSourceField, DataSourceField actualUnitDataSourceField) {
		this(ordinal);
		this.expectedDataSourceField = expectedDataSourceField;
		this.expectedUnitDataSourceField = expectedUnitDataSourceField;
		this.actualDataSourceField = actualDataSourceField;
		this.actualUnitDataSourceField = actualUnitDataSourceField;
	}

	public PhysicalAttributeConfirmActualsFieldSetComponent getFieldSet() {
		return fieldSet;
	}

	public void setFieldSet(PhysicalAttributeConfirmActualsFieldSetComponent fieldSet) {
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

	public DataSourceField getExpectedUnitDataSourceField() {
		return expectedUnitDataSourceField;
	}

	public void setExpectedUnitDataSourceField(DataSourceField expectedUnitDataSourceField) {
		this.expectedUnitDataSourceField = expectedUnitDataSourceField;
	}

	public DataSourceField getActualUnitDataSourceField() {
		return actualUnitDataSourceField;
	}

	public void setActualUnitDataSourceField(DataSourceField actualUnitDataSourceField) {
		this.actualUnitDataSourceField = actualUnitDataSourceField;
	}
}
