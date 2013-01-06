package org.flowframe.ui.vaadin.forms.impl;

import java.util.Collection;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;

public class VaadinSearchFormFieldPair extends GridLayout {
	public static final String OPERATOR_LESS_THAN = "Less Than";
	public static final String OPERATOR_GREATER_THAN = "Greater Than";
	public static final String OPERATOR_EQUAL_TO = "Equal To";

	private static final long serialVersionUID = 1352213853748560691L;

	private ComboBox comparisonSelector;
	private Field valueField;
	private Button deleteButton;
	private VaadinSearchForm form;
	private Collection<?> propertyIds;
	private Item item;
	private ComboBox propertySelector;

	public VaadinSearchFormFieldPair(VaadinSearchForm form, Collection<?> propertyIds, Item item) {
		this.item = item;
		this.propertyIds = propertyIds;
		this.form = form;
		this.comparisonSelector = new ComboBox();
		this.valueField = null;
		this.deleteButton = new Button("Remove");
		this.propertySelector = new ComboBox();

		initialize();
	}

	private void updateValueField(Object propertyId) {
		Field newValueField = this.form.getFormFieldFactory().createField(this.item, propertyId, this.form);
		newValueField.setWidth("100%");
		newValueField.setCaption(null);
		this.replaceComponent(this.valueField, newValueField);
		this.valueField = newValueField;
	}

	private void initialize() {
//		this.propertySelector.setCaption("Field");
		this.propertySelector.setWidth("100%");
		this.propertySelector.setInputPrompt("Select a field");
		this.propertySelector.setImmediate(true);
		this.propertySelector.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (VaadinSearchFormFieldPair.this.propertySelector.getValue() != null) {
					updateValueField(VaadinSearchFormFieldPair.this.propertySelector.getValue());
					VaadinSearchFormFieldPair.this.comparisonSelector.setEnabled(true);
				} else {
					VaadinSearchFormFieldPair.this.valueField = new TextField();
					VaadinSearchFormFieldPair.this.valueField.setCaption("Value");
					VaadinSearchFormFieldPair.this.valueField.setEnabled(false);
					VaadinSearchFormFieldPair.this.valueField.setWidth("100%");
				}
			}
		});

		for (Object id : this.propertyIds) {
			this.propertySelector.addItem(id);
		}

		this.valueField = new TextField();
//		this.valueField.setCaption("Value");
		this.valueField.setEnabled(false);
		this.valueField.setWidth("100%");

		this.deleteButton.setCaption("Remove");
		this.deleteButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				VaadinSearchFormFieldPair.this.form.removeFilter(VaadinSearchFormFieldPair.this);
			}
		});

		this.comparisonSelector.setWidth("100%");
//		this.comparisonSelector.setCaption("Comparison");
		this.comparisonSelector.setEnabled(false);
		this.comparisonSelector.setNullSelectionAllowed(false);

		this.comparisonSelector.addItem(OPERATOR_EQUAL_TO);
		this.comparisonSelector.addItem(OPERATOR_GREATER_THAN);
		this.comparisonSelector.addItem(OPERATOR_LESS_THAN);
		this.comparisonSelector.setValue(OPERATOR_EQUAL_TO);

		setSpacing(true);
		setMargin(true, false, false, true);
		setColumns(4);
		setRows(1);
		setWidth("60%");
//		setStyleName("conx-search-form-field-pair");

		addComponent(this.propertySelector, 0, 0, 0, 0);
		addComponent(this.comparisonSelector, 1, 0, 1, 0);
		addComponent(this.valueField, 2, 0, 2, 0);
		addComponent(this.deleteButton, 3, 0, 3, 0);
		setComponentAlignment(this.deleteButton, Alignment.BOTTOM_LEFT);
	}

	public Object getValue() {
		return this.valueField.getValue();
	}
	
	public Object getPropertyId() {
		return this.propertySelector.getValue();
	}
	
	public Object getOperator() {
		return this.comparisonSelector.getValue();
	}

	public void setValue(Object object) {
		this.valueField.setValue(object);
	}
}
