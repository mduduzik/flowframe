package org.flowframe.ui.component.domain.form;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.flowframe.ds.domain.DataSourceField;
import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.layout.AbstractLayoutComponent;

@Entity
public class FieldSetComponent extends AbstractComponent {
	private static final long serialVersionUID = -806259542376394951L;

	@Transient
	private Map<String, DataSourceField> fieldMap = null;

	@Transient
	private Map<String, FieldSetFieldComponent> fieldSetFieldMap = null;

	@OneToOne
	private FormComponent form;

	@OneToMany(mappedBy = "fieldSet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<FieldSetFieldComponent> fields = new HashSet<FieldSetFieldComponent>();

	@OneToOne
	private AbstractLayoutComponent layout;

	private int ordinal;

	public FieldSetComponent() {
		super("fieldSet");
		this.ordinal = -1;
	}

	public FieldSetComponent(int ordinal) {
		super("fieldSet");
		this.ordinal = ordinal;
	}

	public FieldSetComponent(int ordinal, String caption, AbstractLayoutComponent layout) {
		this(ordinal);
		this.setCaption(caption);
		this.setLayout(layout);
	}

	public FieldSetComponent(int ordinal, String caption, Set<FieldSetFieldComponent> fields, AbstractLayoutComponent layout) {
		this(ordinal, caption, layout);
		this.fields = fields;
	}

	public Set<FieldSetFieldComponent> getFields() {
		return fields;
	}

	public void setFields(Set<FieldSetFieldComponent> fields) {
		this.fields = fields;
	}

	public Map<String, DataSourceField> getFieldMap() {
		if (fieldMap == null) {
			fieldMap = new HashMap<String, DataSourceField>();
			for (FieldSetFieldComponent field : getFields()) {
				fieldMap.put(field.getDataSourceField().getName(), field.getDataSourceField());
			}
		}
		return fieldMap;
	}

	public Map<String, FieldSetFieldComponent> getFieldSetFieldMap() {
		if (fieldSetFieldMap == null) {
			fieldSetFieldMap = new HashMap<String, FieldSetFieldComponent>();
			for (FieldSetFieldComponent field : getFields()) {
				if (field.getDataSourceField().isNestedAttribute()) {
					fieldSetFieldMap.put(field.getDataSourceField().getJPAPath(), field);
				} else {
					fieldSetFieldMap.put(field.getDataSourceField().getName(), field);
				}
			}
		}
		return fieldSetFieldMap;
	}

	public Boolean hasField(String fieldName) {
		return getFieldMap().keySet().contains(fieldName);
	}

	public DataSourceField getField(String fieldName) {
		return getFieldMap().get(fieldName);
	}

	public FieldSetFieldComponent getFieldSetField(String fieldName) {
		return getFieldSetFieldMap().get(fieldName);
	}

	public AbstractLayoutComponent getLayout() {
		return layout;
	}

	public void setLayout(AbstractLayoutComponent layout) {
		this.layout = layout;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public FormComponent getForm() {
		return form;
	}

	public void setForm(FormComponent form) {
		this.form = form;
	}
}
