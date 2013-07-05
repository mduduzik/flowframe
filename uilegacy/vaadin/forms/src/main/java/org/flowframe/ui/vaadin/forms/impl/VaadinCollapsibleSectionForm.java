package org.flowframe.ui.vaadin.forms.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import org.flowframe.ui.component.domain.form.CollapseableSectionFormComponent;
import org.flowframe.ui.component.domain.form.FieldSetComponent;
import org.flowframe.ui.component.domain.form.FieldSetFieldComponent;
import org.flowframe.ui.vaadin.forms.FormMode;
import org.flowframe.ui.vaadin.forms.impl.VaadinFormAlertPanel.AlertType;

import com.vaadin.data.Buffered;
import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Field;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class VaadinCollapsibleSectionForm extends VaadinForm {
	private static final long serialVersionUID = -639931L;

	private VaadinFormHeader header;
	private VaadinFormAlertPanel alertPanel;
	private Panel innerLayoutPanel;
	private VerticalLayout layout;
	private VerticalLayout innerLayout;
	private CollapseableSectionFormComponent componentForm;
	private FormMode mode;
	private HashMap<FieldSetComponent, VaadinCollapsibleSectionFormSectionHeader> headers;
	private HashMap<Field, FieldSetFieldComponent> fields;

	public VaadinCollapsibleSectionForm(CollapseableSectionFormComponent componentForm) {
		this.setComponentModel(componentForm);
		this.componentForm = componentForm;
		this.innerLayoutPanel = new Panel();
		this.layout = new VerticalLayout();
		this.header = new VaadinFormHeader();
		this.alertPanel = new VaadinFormAlertPanel();
		this.headers = new HashMap<FieldSetComponent, VaadinCollapsibleSectionFormSectionHeader>();
		this.fields = new HashMap<Field, FieldSetFieldComponent>();
		this.innerLayout = new VerticalLayout();

		initialize();
	}

	@SuppressWarnings("deprecation")
	private void initialize() {
		this.alertPanel.setVisible(false);
		this.alertPanel.addCloseListener(new ClickListener() {
			private static final long serialVersionUID = 5815832688929242745L;

			@Override
			public void click(ClickEvent event) {
				VaadinCollapsibleSectionForm.this.alertPanel.setVisible(false);
			}
		});

		this.innerLayout.setWidth("100%");
		this.innerLayout.setSpacing(true);
		this.innerLayout.setMargin(true);
		this.innerLayout.setHeight(-1, UNITS_PIXELS);

		this.innerLayoutPanel = new Panel();
		this.innerLayoutPanel.setSizeFull();
		this.innerLayoutPanel.getLayout().setMargin(false, true, false, true);
		this.innerLayoutPanel.setStyleName("light");
		this.innerLayoutPanel.addComponent(innerLayout);

		this.layout.setWidth("100%");
		this.layout.setStyleName("conx-entity-editor-form");
		this.layout.addComponent(header);
		this.layout.addComponent(alertPanel);
		this.layout.addComponent(innerLayoutPanel);
		this.layout.setExpandRatio(innerLayoutPanel, 1.0f);

		setImmediate(true);
		setLayout(layout);
		// False so that commit() must be called explicitly
		setWriteThrough(false);
		// Disallow invalid data from acceptance by the container
		setInvalidCommitted(false);
	}

	@Override
	protected void attachField(Object propertyId, Field field) {
		FieldSetComponent fieldSet = componentForm.getFieldSetForField((String) propertyId);
		if (fieldSet != null) {
			FieldSetFieldComponent fieldComponent = fieldSet.getFieldSetField((String) propertyId);
			if (fieldComponent != null) {
				fields.put(field, fieldComponent);
				VaadinFormFieldAugmenter.augment(field, fieldComponent);
				if (this.getComponentModel().isReadOnly()) {
					field.setReadOnly(true);
				}
				VaadinCollapsibleSectionFormSectionHeader header = headers.get(fieldSet);
				if (header == null) {
					header = addFormSection(fieldSet);
				}
				field.setWidth("100%");
				((VaadinFormGridLayout) header.getLayout()).addField(field);
			}
		}
	}

	private VaadinCollapsibleSectionFormSectionHeader addFormSection(FieldSetComponent fieldSet) {
		VaadinFormGridLayout content = new VaadinFormGridLayout(this.componentForm.getColumnsLimit());
		content.setMargin(false, true, false, true);
		VaadinCollapsibleSectionFormSectionHeader header = new VaadinCollapsibleSectionFormSectionHeader(fieldSet, content);
		innerLayout.addComponent(header);
		innerLayout.addComponent(content);
		headers.put(fieldSet, header);
		return header;
	}

	@Override
	public void setTitle(String title) {
		this.header.setTitle(title);
	}

	@Override
	public void setItemDataSource(com.vaadin.data.Item newDataSource, Collection<?> propertyIds) {
		this.innerLayout.removeAllComponents();
		this.headers.clear();
		this.fields.clear();
		super.setItemDataSource(newDataSource, propertyIds);
	}

	@Override
	public void setItemDataSource(com.vaadin.data.Item newDataSource) {
		this.innerLayout.removeAllComponents();
		this.headers.clear();
		this.fields.clear();
		super.setItemDataSource(newDataSource);
	}

	public boolean validateForm() {
		boolean firstErrorFound = false;
		Set<FieldSetComponent> formFieldHeaders = headers.keySet();
		for (FieldSetComponent fieldSet : formFieldHeaders) {
			headers.get(fieldSet).removeStyleName("conx-form-header-error");
		}
		Set<Field> formFields = fields.keySet();
		for (Field field : formFields) {
			VaadinCollapsibleSectionFormSectionHeader formFieldHeader = getFieldHeader(field);
			if (formFieldHeader != null) {
				try {
					field.validate();
					field.removeStyleName("conx-form-field-error");
				} catch (InvalidValueException e) {
					field.addStyleName("conx-form-field-error");
					formFieldHeader.addStyleName("conx-form-header-error");
					if (!firstErrorFound) {						
						this.alertPanel.setMessage(e.getMessage());
						this.alertPanel.setVisible(true);
						firstErrorFound = true;
					}
				}
			}
		}
		if (firstErrorFound) {
			return false;
		} else {
			this.alertPanel.setVisible(false);
			return true;
		}
	}
	
	@Override
	public boolean saveForm() {
		LinkedList<SourceException> problems = null;

		// Only commit on valid state if so requested
		if (!isInvalidCommitted() && !isValid()) {
			validate();
		}

		Set<Field> fieldSet = fields.keySet();

		// Try to commit all
		for (Field field : fieldSet) {
			try {
				// Commit only non-readonly fields.
				if (!field.isReadOnly()) {
					field.commit();
				}
			} catch (final Buffered.SourceException e) {
				if (problems == null) {
					problems = new LinkedList<SourceException>();
				}
				problems.add(e);
			}
		}

		// No problems occurred
		if (problems == null) {
			Item item = this.getItemDataSource();
			if (item.getItemProperty("name") != null)
				this.header.setTitle(item.getItemProperty("name").getValue().toString());
			
			this.alertPanel.setAlertType(AlertType.SUCCESS);
			this.alertPanel.setMessage(this.header.getTitle() + " was saved successfully.");
			this.alertPanel.setVisible(true);
			return true;
		} else {
			this.alertPanel.setAlertType(AlertType.ERROR);
			this.alertPanel.setMessage(problems.iterator().next().getMessage());
			this.alertPanel.setVisible(true);
			return false;
		}
	}

	private VaadinCollapsibleSectionFormSectionHeader getFieldHeader(Field field) {
		FieldSetFieldComponent fsf = fields.get(field);
		Collection<FieldSetComponent> fieldSets = this.headers.keySet();
		for (FieldSetComponent fieldSet : fieldSets) {
			if (fieldSet.getFieldSetField(getPropertyId(fsf.getDataSourceField())) != null) {
				return this.headers.get(fieldSet);
			}
		}

		return null;
	}
	
	public void resetForm() {
		this.alertPanel.setVisible(false);
		setItemDataSource(getItemDataSource());
	}
	
	public CollapseableSectionFormComponent getComponentModel() {
		return this.componentForm;
	}
}
