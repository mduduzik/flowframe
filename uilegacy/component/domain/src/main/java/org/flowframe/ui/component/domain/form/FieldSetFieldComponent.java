package org.flowframe.ui.component.domain.form;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.flowframe.ds.domain.DataSourceField;
import org.flowframe.ui.component.domain.AbstractFieldComponent;

@Entity
public class FieldSetFieldComponent extends AbstractFieldComponent implements Serializable {
	private static final long serialVersionUID = -4371216577505004553L;

	@OneToOne
	private FieldSetComponent fieldSet;
	
	@OneToOne
	private DataSourceField field;
	
	private int ordinal;
	
	public FieldSetFieldComponent() {
		super("fieldSetField");
		this.ordinal = -1;
	}
	
	public FieldSetFieldComponent(int ordinal) {
		super("fieldSetField");
		this.ordinal = ordinal;
	}
	
	public FieldSetFieldComponent(int ordinal, DataSourceField field) {
		this(ordinal);
		this.field = field;
	}
	
	public FieldSetFieldComponent(int ordinal, DataSourceField field, FieldSetComponent fieldSet) {
		this(ordinal, field);
		this.fieldSet = fieldSet;
	}

	public FieldSetComponent getFieldSet() {
		return fieldSet;
	}

	public void setFieldSet(FieldSetComponent fieldSet) {
		this.fieldSet = fieldSet;
	}

	public DataSourceField getDataSourceField() {
		return field;
	}

	public void setDataSourceField(DataSourceField dsField) {
		this.field = dsField;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}
	
	public boolean isRequired() {
		if (this.field != null) {
			return this.field.getRequired();
		}
		return false;
	}
}
