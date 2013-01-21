package org.flowframe.ui.vaadin.editors.entity.vaadin.ext.preferences;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.flowframe.ui.vaadin.forms.impl.VaadinFormAlertPanel;
import org.flowframe.ui.vaadin.forms.impl.VaadinFormHeader;
import org.flowframe.ui.vaadin.forms.listeners.IFormChangeListener;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class PreferencesEditorForm extends Form {
	private static final long serialVersionUID = -1298112872125628304L;

	private VaadinFormHeader header;
	private VaadinFormAlertPanel alertPanel;
	private VerticalLayout layout;
	private VerticalLayout innerLayout;
	private Set<IFormChangeListener> listeners;
	
	private Set<Field> fields;

	public PreferencesEditorForm() {
		this.header = new VaadinFormHeader();
		this.layout = new VerticalLayout();
		this.innerLayout = new VerticalLayout();
		this.listeners = new HashSet<IFormChangeListener>();

		initialize();
	}

	private void initialize() {
		this.fields = new HashSet<Field>();
		
		this.header = new VaadinFormHeader();
		this.header.setTitle("Preference Entry");
		
		this.alertPanel = new VaadinFormAlertPanel();
		this.alertPanel.setVisible(false);

		this.innerLayout.setWidth("100%");
		this.innerLayout.setSpacing(true);
		this.innerLayout.setMargin(true);
		this.innerLayout.setHeight(-1, UNITS_PIXELS);

		this.layout.setWidth("100%");
		this.layout.setStyleName("conx-entity-editor-form");
		this.layout.addComponent(this.header);
		this.layout.addComponent(this.alertPanel);
		this.layout.addComponent(this.innerLayout);
		this.layout.setExpandRatio(this.innerLayout, 1.0f);

		this.header.setAction("Creating");
		
		setLayout(layout);
		setWriteThrough(false);
		setInvalidCommitted(false);
	}
	
	public boolean validateForm() {
		boolean firstErrorFound = false;
		for (Field field : this.fields) {
			try {
				field.validate();
				field.removeStyleName("conx-form-field-error");
			} catch (InvalidValueException e) {
				field.addStyleName("conx-form-field-error");
				if (!firstErrorFound) {
					this.alertPanel.setMessage(e.getMessage());
					this.alertPanel.setVisible(true);
					firstErrorFound = true;
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
	
	public boolean saveForm() {
		boolean firstErrorFound = false;
		for (Field field : this.fields) {
			try {
				field.commit();
				field.removeStyleName("conx-form-field-error");
			} catch (InvalidValueException e) {
				field.addStyleName("conx-form-field-error");
				if (!firstErrorFound) {
					this.alertPanel.setMessage(e.getMessage());
					this.alertPanel.setVisible(true);
					firstErrorFound = true;
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
	protected void attachField(Object propertyId, com.vaadin.ui.Field field) {
		assert (propertyId != null) : "The provided propertyId was null.";
		assert (field != null) : "The provided propertyId was null.";

		if ("preferenceValue".equals(propertyId)) {
			field.setWidth("100%");
			field.setCaption("Value");
			field.setHeight("75px");
			
			assert (field instanceof TextField) : "The \"value\" field must be a TextField.";
			((TextField) field).addListener(new TextChangeListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void textChange(TextChangeEvent event) {
					fireFieldChangedEvent();
				}
			});
			innerLayout.addComponent(field);
			this.fields.add(field);
		} else if ("preferenceKey".equals(propertyId)) {
			field.setWidth("30%");
			field.setCaption("Name");
			assert (field instanceof TextField) : "The \"value\" field must be a TextField.";
			((TextField) field).addListener(new TextChangeListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void textChange(TextChangeEvent event) {
					fireFieldChangedEvent();
				}
			});
			
			innerLayout.addComponentAsFirst(field);
			this.fields.add(field);
		}
	}

	private void fireFieldChangedEvent() {
		for (IFormChangeListener listener : listeners) {
			listener.onFormChanged();
		}
	}
	
	public void setActionCaption(String caption) {
		this.header.setAction(caption);
	}

	@Override
	public void setItemDataSource(com.vaadin.data.Item newDataSource, Collection<?> propertyIds) {
		innerLayout.removeAllComponents();
		super.setItemDataSource(newDataSource, propertyIds);
	}

	public void addListener(IFormChangeListener listener) {
		this.listeners.add(listener);
	}
}
