package org.flowframe.ui.component.domain.form;

import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.flowframe.ds.domain.DataSource;
import org.flowframe.ds.domain.DataSourceField;
import org.flowframe.ui.component.domain.layout.AbstractLayoutComponent;

@Entity
public class SimpleFormComponent extends FormComponent {
	private static final long serialVersionUID = -2009961101456261981L;
	
	@OneToOne(cascade=CascadeType.ALL)
	private FieldSetComponent fieldSet;

	public SimpleFormComponent() {
		super("simpleForm");
	}
	
	public SimpleFormComponent(DataSource ds) {
		this();
		this.setDataSource(ds);
	}
	
	public SimpleFormComponent(DataSource ds, String caption) {
		this(ds);
		this.setCaption(caption);
	}
	
	public SimpleFormComponent(DataSource ds, String caption, AbstractLayoutComponent layout) {
		this(ds, caption);
		this.fieldSet.setLayout(layout);
	}
	
	public SimpleFormComponent(DataSource ds, String caption, AbstractLayoutComponent layout, Set<FieldSetFieldComponent> fields) {
		this(ds, caption, layout);
		this.fieldSet.setFields(fields);
	}
	
	public Set<FieldSetFieldComponent> getFields() {
		return this.fieldSet.getFields();
	}

	public void setFields(Set<FieldSetFieldComponent> fields) {
		this.fieldSet.setFields(fields);
	}

	public Map<String, DataSourceField> getFieldMap() {
		return fieldSet.getFieldMap();
	}

	public Boolean hasField(String fieldName) {
		return fieldSet.getFieldMap().keySet().contains(fieldName);
	}

	public DataSourceField getField(String fieldName) {
		return fieldSet.getFieldMap().get(fieldName);
	}

	public AbstractLayoutComponent getLayout() {
		return fieldSet.getLayout();
	}

	public void setLayout(AbstractLayoutComponent layout) {
		this.fieldSet.setLayout(layout);
	}
	
	@Override
	public String getCaption() {
		if (this.fieldSet == null) {
			return super.getCaption();
		} else {
			return this.fieldSet.getCaption();
		}
	}
	
	@Override
	public void setCaption(String caption) {
		if (this.fieldSet != null) {
			super.setCaption(caption);
		}
	}

	public FieldSetComponent getFieldSet() {
		return fieldSet;
	}

	public void setFieldSet(FieldSetComponent fieldSet) {
		this.fieldSet = fieldSet;
	}
}
