package org.flowframe.ui.vaadin.forms.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.flowframe.ui.component.domain.form.DetailFormComponent;
import org.flowframe.ui.component.domain.form.FieldSetComponent;
import org.flowframe.ui.component.domain.form.FieldSetFieldComponent;
import org.flowframe.ui.vaadin.forms.FormMode;
import org.flowframe.ui.vaadin.forms.listeners.IFormChangeListener;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Field;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class VaadinDetailForm extends VaadinForm {
	private static final long serialVersionUID = -639931L;

	private VaadinFormHeader header;
	private VaadinFormAlertPanel alertPanel;
	private Panel innerLayoutPanel;
	private VerticalLayout layout;
	private VerticalLayout innerLayout;
	private DetailFormComponent componentForm;
	private FormMode mode;
	private HashMap<FieldSetComponent, VaadinCollapsibleSectionFormSectionHeader> headers;
	private HashMap<Field, FieldSetFieldComponent> fields;
	private Set<IFormChangeListener> formChangeListeners;

	public VaadinDetailForm(DetailFormComponent componentForm) {
		this.componentForm = componentForm;
		this.innerLayoutPanel = new Panel();
		this.layout = new VerticalLayout();
		this.header = new VaadinFormHeader();
		this.alertPanel = new VaadinFormAlertPanel();
		this.headers = new HashMap<FieldSetComponent, VaadinCollapsibleSectionFormSectionHeader>();
		this.fields = new HashMap<Field, FieldSetFieldComponent>();
		this.innerLayout = new VerticalLayout();
		this.formChangeListeners = new HashSet<IFormChangeListener>();

		initialize();
	}

	@SuppressWarnings("deprecation")
	private void initialize() {
		this.alertPanel.setVisible(false);
		this.alertPanel.addCloseListener(new ClickListener() {
			private static final long serialVersionUID = 5815832688929242745L;

			@Override
			public void click(ClickEvent event) {
				VaadinDetailForm.this.alertPanel.setVisible(false);
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
		setFormFieldFactory(new VaadinJPAFieldFactory());
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
				VaadinFormFieldAugmenter.augment(field, fieldComponent, new ValueChangeListener() {
					private static final long serialVersionUID = -6182433271255560793L;

					@Override
					public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
						onFormChanged();
					}
				});
				VaadinCollapsibleSectionFormSectionHeader header = headers.get(fieldSet);
				if (header == null) {
					header = addFormSection(fieldSet);
				}
				field.setWidth("100%");
				header.getLayout().addComponent(field);
			}
		}
	}

	public void onFormChanged() {
		for (IFormChangeListener listener : this.formChangeListeners) {
			listener.onFormChanged();
		}
	}

	public void addFormChangeListener(IFormChangeListener listener) {
		this.formChangeListeners.add(listener);
	}

	private VaadinCollapsibleSectionFormSectionHeader addFormSection(FieldSetComponent fieldSet) {
		VaadinFormGridLayout content = new VaadinFormGridLayout();
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
		super.setItemDataSource(newDataSource, propertyIds);
	}

	@Override
	public void setItemDataSource(com.vaadin.data.Item newDataSource) {
		this.innerLayout.removeAllComponents();
		this.headers.clear();
		super.setItemDataSource(newDataSource);
	}

	public boolean validateForm() {
		// super.validate();
		// for (final Iterator<Object> i = propertyIds.iterator();
		// i.hasNext();) {
		// (fields.get(i.next())).validate();
		// }
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

	private VaadinCollapsibleSectionFormSectionHeader getFieldHeader(Field field) {
		FieldSetFieldComponent fsf = fields.get(field);
		if (fsf != null) {
			FieldSetComponent fs = fsf.getFieldSet();
			if (fs != null) {
				return headers.get(fs);
			}
		}
		return null;
	}

	public void resetForm() {
		this.alertPanel.setVisible(false);
		setItemDataSource(getItemDataSource());
	}
}
