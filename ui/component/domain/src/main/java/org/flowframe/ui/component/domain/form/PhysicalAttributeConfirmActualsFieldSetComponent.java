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
public class PhysicalAttributeConfirmActualsFieldSetComponent extends AbstractComponent {
	private static final long serialVersionUID = -2451618330566408888L;

	@Transient
	private Map<String, PhysicalAttributeConfirmActualsFieldSetFieldComponent> fieldSetFieldMap = null;

	@OneToOne
	private FormComponent form;

	@OneToMany(mappedBy = "fieldSet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<PhysicalAttributeConfirmActualsFieldSetFieldComponent> fields = new HashSet<PhysicalAttributeConfirmActualsFieldSetFieldComponent>();

	public PhysicalAttributeConfirmActualsFieldSetComponent() {
		super("confirmactualsfieldset");
	}

	public Set<PhysicalAttributeConfirmActualsFieldSetFieldComponent> getFields() {
		return fields;
	}

	public FormComponent getForm() {
		return form;
	}

	public void setForm(FormComponent form) {
		this.form = form;
	}

	public Map<String, PhysicalAttributeConfirmActualsFieldSetFieldComponent> getFieldSetFieldMap() {
		if (fieldSetFieldMap == null) {
			fieldSetFieldMap = new HashMap<String, PhysicalAttributeConfirmActualsFieldSetFieldComponent>();
			for (PhysicalAttributeConfirmActualsFieldSetFieldComponent field : getFields()) {
				putConfirmActualsFieldSetField(field);
				// fieldSetFieldMap.put(field.getExpectedDataSourceField().getName(),
				// field);
				// fieldSetFieldMap.put(field.getActualDataSourceField().getName(),
				// field);
			}
		}
		return fieldSetFieldMap;
	}

	private String getExpectedDataSourceFieldName(PhysicalAttributeConfirmActualsFieldSetFieldComponent field) {
		if (field.getExpectedDataSourceField().getValueXPath() == null) {
			return field.getExpectedDataSourceField().getName();
		} else {
			return field.getExpectedDataSourceField().getJPAPath();
		}
	}
	
	private String getExpectedUnitDataSourceFieldName(PhysicalAttributeConfirmActualsFieldSetFieldComponent field) {
		if (field.getExpectedUnitDataSourceField().getValueXPath() == null) {
			return field.getExpectedUnitDataSourceField().getName();
		} else {
			return field.getExpectedUnitDataSourceField().getJPAPath();
		}
	}

	private String getActualDataSourceFieldName(PhysicalAttributeConfirmActualsFieldSetFieldComponent field) {
		if (field.getActualDataSourceField().getValueXPath() == null) {
			return field.getActualDataSourceField().getName();
		} else {
			return field.getActualDataSourceField().getJPAPath();
		}
	}
	
	private String getActualUnitDataSourceFieldName(PhysicalAttributeConfirmActualsFieldSetFieldComponent field) {
		if (field.getActualUnitDataSourceField().getValueXPath() == null) {
			return field.getActualUnitDataSourceField().getName();
		} else {
			return field.getActualUnitDataSourceField().getJPAPath();
		}
	}

	private void putConfirmActualsFieldSetField(PhysicalAttributeConfirmActualsFieldSetFieldComponent field) {
		String expectedDataSourceFieldName = getExpectedDataSourceFieldName(field), 
				expectedUnitDataSourceFieldName = getExpectedUnitDataSourceFieldName(field), 
				actualDataSourceFieldName = getActualDataSourceFieldName(field), 
				actualUnitDataSourceFieldName = getActualUnitDataSourceFieldName(field);
		if (expectedDataSourceFieldName != null && actualDataSourceFieldName != null) {
			fieldSetFieldMap.put(expectedDataSourceFieldName, field);
			fieldSetFieldMap.put(expectedUnitDataSourceFieldName, field);
			fieldSetFieldMap.put(actualDataSourceFieldName, field);
			fieldSetFieldMap.put(actualUnitDataSourceFieldName, field);
		}
	}

	public PhysicalAttributeConfirmActualsFieldSetFieldComponent getFieldSetField(String fieldName) {
		return getFieldSetFieldMap().get(fieldName);
	}

	public boolean isExpected(Object propertyId) {
		PhysicalAttributeConfirmActualsFieldSetFieldComponent field = getFieldSetFieldMap().get(propertyId);
		if (getExpectedDataSourceFieldName(field).equals(propertyId)) {
			return true;
		}
		return false;
	}
	
	public boolean isExpectedUnit(Object propertyId) {
		PhysicalAttributeConfirmActualsFieldSetFieldComponent field = getFieldSetFieldMap().get(propertyId);
		if (getExpectedUnitDataSourceFieldName(field).equals(propertyId)) {
			return true;
		}
		return false;
	}
	
	public boolean isActual(Object propertyId) {
		PhysicalAttributeConfirmActualsFieldSetFieldComponent field = getFieldSetFieldMap().get(propertyId);
		if (getActualDataSourceFieldName(field).equals(propertyId)) {
			return true;
		}
		return false;
	}
	
	public boolean isActualUnit(Object propertyId) {
		PhysicalAttributeConfirmActualsFieldSetFieldComponent field = getFieldSetFieldMap().get(propertyId);
		if (getActualUnitDataSourceFieldName(field).equals(propertyId)) {
			return true;
		}
		return false;
	}
}
