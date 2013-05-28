package org.flowframe.ui.vaadin.forms.impl;

import org.flowframe.ui.component.domain.AbstractFieldComponent;

import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Field;

public class VaadinFormFieldAugmenter {
	public static void augment(final Field field, final ValueChangeListener valueChangeListener) {
		field.addListener(valueChangeListener);
	}
	
	public static void augment(Field field, AbstractFieldComponent componentModel) {
		if (componentModel.isReadOnly()) {
			field.setEnabled(false);
		} 
		if (componentModel.isRequired()) {
			field.setRequired(true);
			field.setRequiredError(field.getCaption() + " is a required field.");
		}
		if (field instanceof AbstractComponent) {
			((AbstractComponent) field).setImmediate(true);
		}
		if (componentModel.getDataSourceField() != null && componentModel.getDataSourceField().getTitle() != null) {
			field.setCaption(componentModel.getDataSourceField().getTitle());
		}
		
		if (field instanceof AbstractField) {
			((AbstractField) field).setValidationVisible(false);
		}
	}
	
	public static void augment(final Field field, AbstractFieldComponent componentModel, final ValueChangeListener valueChangeListener) {
		augment(field, componentModel);
		augment(field, valueChangeListener);
	}
}
