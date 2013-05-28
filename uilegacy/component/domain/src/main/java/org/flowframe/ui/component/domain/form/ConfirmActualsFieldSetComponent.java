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

import org.flowframe.ui.component.domain.AbstractComponent;

@Entity
public class ConfirmActualsFieldSetComponent extends AbstractComponent {
	private static final long serialVersionUID = -2451618330566408888L;

	@Transient
	private Map<String, ConfirmActualsFieldSetFieldComponent> fieldSetFieldMap = null;

	@OneToOne
	private FormComponent form;

	@OneToMany(mappedBy = "fieldSet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ConfirmActualsFieldSetFieldComponent> fields = new HashSet<ConfirmActualsFieldSetFieldComponent>();

	public ConfirmActualsFieldSetComponent() {
		super("confirmactualsfieldset");
	}

	public Set<ConfirmActualsFieldSetFieldComponent> getFields() {
		return fields;
	}

	public FormComponent getForm() {
		return form;
	}

	public void setForm(FormComponent form) {
		this.form = form;
	}

	public Map<String, ConfirmActualsFieldSetFieldComponent> getFieldSetFieldMap() {
		if (fieldSetFieldMap == null) {
			fieldSetFieldMap = new HashMap<String, ConfirmActualsFieldSetFieldComponent>();
			for (ConfirmActualsFieldSetFieldComponent field : getFields()) {
				putConfirmActualsFieldSetField(field);
				// fieldSetFieldMap.put(field.getExpectedDataSourceField().getName(),
				// field);
				// fieldSetFieldMap.put(field.getActualDataSourceField().getName(),
				// field);
			}
		}
		return fieldSetFieldMap;
	}

	private String getExpectedDataSourceFieldName(ConfirmActualsFieldSetFieldComponent field) {
		if (field.getExpectedDataSourceField().getValueXPath() == null) {
			return field.getExpectedDataSourceField().getName();
		} else {
			return field.getExpectedDataSourceField().getJPAPath();
		}
	}

	private String getActualDataSourceFieldName(ConfirmActualsFieldSetFieldComponent field) {
		if (field.getActualDataSourceField().getValueXPath() == null) {
			return field.getActualDataSourceField().getName();
		} else {
			return field.getActualDataSourceField().getJPAPath();
		}
	}

	private void putConfirmActualsFieldSetField(ConfirmActualsFieldSetFieldComponent field) {
		String expectedDataSourceFieldName = getExpectedDataSourceFieldName(field), actualDataSourceFieldName = getActualDataSourceFieldName(field);
		if (expectedDataSourceFieldName != null && actualDataSourceFieldName != null) {
			fieldSetFieldMap.put(expectedDataSourceFieldName, field);
			fieldSetFieldMap.put(actualDataSourceFieldName, field);
		}
	}

	public ConfirmActualsFieldSetFieldComponent getFieldSetField(String fieldName) {
		return getFieldSetFieldMap().get(fieldName);
	}

	public boolean isExpected(Object propertyId) {
		for (ConfirmActualsFieldSetFieldComponent field : getFields()) {
			if (getExpectedDataSourceFieldName(field).equals(propertyId)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isActual(Object propertyId) {
		for (ConfirmActualsFieldSetFieldComponent field : getFields()) {
			if (getActualDataSourceFieldName(field).equals(propertyId)) {
				return true;
			}
		}
		return false;
	}
}
