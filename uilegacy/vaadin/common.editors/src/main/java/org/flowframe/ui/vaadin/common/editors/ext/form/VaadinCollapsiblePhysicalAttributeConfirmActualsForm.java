package org.flowframe.ui.vaadin.common.editors.ext.form;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import org.flowframe.ui.component.domain.form.CollapsiblePhysicalAttributeConfirmActualsFormComponent;
import org.flowframe.ui.component.domain.form.PhysicalAttributeConfirmActualsFieldSetComponent;
import org.flowframe.ui.component.domain.form.PhysicalAttributeConfirmActualsFieldSetFieldComponent;
import org.flowframe.ui.vaadin.forms.FormMode;
import org.flowframe.ui.vaadin.forms.impl.VaadinForm;
import org.flowframe.ui.vaadin.forms.impl.VaadinFormAlertPanel;
import org.flowframe.ui.vaadin.forms.impl.VaadinFormAlertPanel.AlertType;
import org.flowframe.ui.vaadin.forms.impl.VaadinFormFieldAugmenter;
import org.flowframe.ui.vaadin.forms.impl.VaadinFormHeader;

import com.vaadin.data.Buffered;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class VaadinCollapsiblePhysicalAttributeConfirmActualsForm extends VaadinForm {
	private static final long serialVersionUID = -96772300028363L;

	private VaadinFormHeader header;
	private VaadinFormAlertPanel alertPanel;
	private Panel innerLayoutPanel;
	private VerticalLayout layout;
	private VerticalLayout innerLayout;
	private FormMode mode;
	private HashMap<PhysicalAttributeConfirmActualsFieldSetComponent, VaadinCollapsiblePhysicalAttributeConfirmActualsFormSectionHeader> headers;
	private HashMap<Field, PhysicalAttributeConfirmActualsFieldSetFieldComponent> fields;
	private HashMap<PhysicalAttributeConfirmActualsFieldSetFieldComponent, Integer> fieldIndexMap;

	public VaadinCollapsiblePhysicalAttributeConfirmActualsForm(CollapsiblePhysicalAttributeConfirmActualsFormComponent componentForm) {
		super.setComponentModel(componentForm);
		this.innerLayoutPanel = new Panel();
		this.layout = new VerticalLayout();
		this.header = new VaadinFormHeader();
		this.alertPanel = new VaadinFormAlertPanel();
		this.headers = new HashMap<PhysicalAttributeConfirmActualsFieldSetComponent, VaadinCollapsiblePhysicalAttributeConfirmActualsFormSectionHeader>();
		this.fields = new HashMap<Field, PhysicalAttributeConfirmActualsFieldSetFieldComponent>();
		this.innerLayout = new VerticalLayout();
		this.fieldIndexMap = new HashMap<PhysicalAttributeConfirmActualsFieldSetFieldComponent, Integer>();

		initialize();
	}

	@SuppressWarnings("deprecation")
	private void initialize() {
		this.alertPanel.setVisible(false);
		this.alertPanel.addCloseListener(new ClickListener() {
			private static final long serialVersionUID = 5815832688929242745L;

			@Override
			public void click(ClickEvent event) {
				VaadinCollapsiblePhysicalAttributeConfirmActualsForm.this.alertPanel.setVisible(false);
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
		setFormMode(FormMode.EDITING);
		setLayout(layout);
		// False so that commit() must be called explicitly
		setWriteThrough(false);
		// Disallow invalid data from acceptance by the container
		setInvalidCommitted(false);
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

	@Override
	protected void attachField(Object propertyId, Field field) {
		PhysicalAttributeConfirmActualsFieldSetComponent fieldSet = ((CollapsiblePhysicalAttributeConfirmActualsFormComponent) getComponentModel()).getFieldSetForField((String) propertyId);
		if (fieldSet != null) {
			PhysicalAttributeConfirmActualsFieldSetFieldComponent fieldComponent = fieldSet.getFieldSetField((String) propertyId);
			if (fieldComponent != null) {
				fields.put(field, fieldComponent);
				field.setWidth("100%");
				VaadinFormFieldAugmenter.augment(field, fieldComponent);
				if (this.getComponentModel().isReadOnly()) {
					field.setReadOnly(true);
				}
				VaadinCollapsiblePhysicalAttributeConfirmActualsFormSectionHeader header = headers.get(fieldSet);
				if (header == null) {
					header = addFormSection(fieldSet);
				}

				GridLayout gridLayout = (GridLayout) header.getLayout();
				Integer index = fieldIndexMap.get(fieldComponent);
				if (index == null) {
					index = getNextIndex(gridLayout);
					fieldIndexMap.put(fieldComponent, index);
				}
				allocateIndex(gridLayout, index);

				Label label = new Label(fieldComponent.getCaption());
				label.setStyleName("conx-confirm-actuals-field-caption");
				label.setWidth("100%");
				addCaptionLabel(gridLayout, index, label);

				if (fieldSet.isExpected(propertyId)) {
					field.setEnabled(false);
					field.setCaption(null);
					addExpectedField(gridLayout, index, field);
				} else if (fieldSet.isActual(propertyId)) {
					field.setCaption(null);
					addActualField(gridLayout, index, field);
				} else if (fieldSet.isActualUnit(propertyId)) {
					field.setCaption(null);
					addActualUnitField(gridLayout, index, field);
				} else if (fieldSet.isExpectedUnit(propertyId)) {
					field.setCaption(null);
					field.setEnabled(false);
					addExpectedUnitField(gridLayout, index, field);
				}
			}
		}
	}
	
	@Override
	protected void fireFormChangedEvent() {
//		this.saveButton.setEnabled(false);
//		this.verifyButton.setEnabled(true);
		
		super.fireFormChangedEvent();
	}

	private int getNextIndex(GridLayout innerLayout) {
		GridLayout expectedLayout = (GridLayout) innerLayout.getComponent(1, 0);
		if (expectedLayout != null) {
			return expectedLayout.getRows();
		} else {
			return -1;
		}
	}

	private void addCaptionLabel(GridLayout innerLayout, int index, Label label) {
		GridLayout captionLayout = (GridLayout) innerLayout.getComponent(0, 0);
		if (captionLayout != null) {
			if (captionLayout.getComponent(0, index) == null) {
				captionLayout.addComponent(label, 0, index, 0, index);
			}
		}
	}

	private void addExpectedField(GridLayout innerLayout, int index, Field field) {
		GridLayout expectedLayout = (GridLayout) innerLayout.getComponent(1, 0);
		if (expectedLayout != null) {
			if (expectedLayout.getComponent(0, index) == null) {
				expectedLayout.addComponent(field, 0, index, 0, index);
			}
		}
	}
	
	private void addExpectedUnitField(GridLayout innerLayout, int index, Field field) {
		GridLayout expectedLayout = (GridLayout) innerLayout.getComponent(1, 0);
		if (expectedLayout != null) {
			if (expectedLayout.getComponent(1, index) == null) {
				expectedLayout.addComponent(field, 1, index, 1, index);
			}
		}
	}

	private void addActualField(GridLayout innerLayout, int index, Field field) {
		GridLayout actualLayout = (GridLayout) innerLayout.getComponent(2, 0);
		if (actualLayout != null) {
			if (actualLayout.getComponent(0, index) == null) {
				actualLayout.addComponent(field, 0, index, 0, index);
			}
		}
	}
	
	private void addActualUnitField(GridLayout innerLayout, int index, Field field) {
		GridLayout actualLayout = (GridLayout) innerLayout.getComponent(2, 0);
		if (actualLayout != null) {
			if (actualLayout.getComponent(1, index) == null) {
				actualLayout.addComponent(field, 1, index, 1, index);
			}
		}
	}

	private void allocateIndex(GridLayout innerLayout, int index) {
		GridLayout captionLayout = (GridLayout) innerLayout.getComponent(0, 0);
		GridLayout expectedLayout = (GridLayout) innerLayout.getComponent(1, 0);
		GridLayout actualLayout = (GridLayout) innerLayout.getComponent(2, 0);
		if (captionLayout != null && expectedLayout != null && actualLayout != null) {
			if (captionLayout.getRows() <= index) {
				captionLayout.setRows(index + 1);
			}
			if (expectedLayout.getRows() <= index) {
				expectedLayout.setRows(index + 1);
			}
			if (actualLayout.getRows() <= index) {
				actualLayout.setRows(index + 1);
			}
		}
	}

	private VaadinCollapsiblePhysicalAttributeConfirmActualsFormSectionHeader addFormSection(PhysicalAttributeConfirmActualsFieldSetComponent fieldSet) {
		GridLayout gridLayout = new GridLayout(3, 1);
		gridLayout.setWidth("70%");
		gridLayout.setSpacing(true);
		gridLayout.setStyleName("conx-entity-editor-form");
		gridLayout.setMargin(true, true, false, true);

		Embedded placeholder = new Embedded();
		placeholder.setHeight("22px");
		placeholder.setWidth("1px");

		GridLayout captionLayout = new GridLayout(1, 1);
		captionLayout.setWidth("100%");
		captionLayout.setSpacing(true);
		captionLayout.addComponent(placeholder, 0, 0, 0, 0);
		GridLayout expectedLayout = new GridLayout(2, 1);
		expectedLayout.setWidth("100%");
		expectedLayout.setSpacing(true);
		expectedLayout.addComponent(new VaadinConfirmActualsFormSectionHeader("Expected"), 0, 0, 1, 0);
		GridLayout actualLayout = new GridLayout(2, 1);
		actualLayout.setWidth("100%");
		actualLayout.setSpacing(true);
		actualLayout.addComponent(new VaadinConfirmActualsFormSectionHeader("Actual"), 0, 0, 1, 0);

		gridLayout.addComponent(captionLayout, 0, 0, 0, 0);
		gridLayout.addComponent(expectedLayout, 1, 0, 1, 0);
		gridLayout.addComponent(actualLayout, 2, 0, 2, 0);
		gridLayout.setColumnExpandRatio(0, 0.166f);
		gridLayout.setColumnExpandRatio(1, 0.417f);
		gridLayout.setColumnExpandRatio(2, 0.417f);

		VaadinCollapsiblePhysicalAttributeConfirmActualsFormSectionHeader header = new VaadinCollapsiblePhysicalAttributeConfirmActualsFormSectionHeader(fieldSet, gridLayout);
		innerLayout.addComponent(header);
		innerLayout.addComponent(header.getLayout());
		headers.put(fieldSet, header);
		return header;
	}

	public FormMode getFormMode() {
		return mode;
	}

	public void setFormMode(FormMode mode) {
		this.mode = mode;
		switch (mode) {
		case CREATING:
			this.header.setAction("Creating");
			break;
		case EDITING:
			this.header.setAction("Editing");
			break;
		}
	}

	public void setTitle(String title) {
		this.header.setTitle(title);
	}

	@Override
	public void setItemDataSource(com.vaadin.data.Item newDataSource, Collection<?> propertyIds) {
		this.innerLayout.removeAllComponents();
		this.headers.clear();
		this.alertPanel.setVisible(false);
		super.setItemDataSource(newDataSource, propertyIds);
	}

	@Override
	public void setItemDataSource(com.vaadin.data.Item newDataSource) {
		this.innerLayout.removeAllComponents();
		this.headers.clear();
		this.alertPanel.setVisible(false);
		super.setItemDataSource(newDataSource);
	}

	@Override
	public boolean validateForm() {
		boolean firstErrorFound = false;
		Set<PhysicalAttributeConfirmActualsFieldSetComponent> formFieldHeaders = headers.keySet();
		for (PhysicalAttributeConfirmActualsFieldSetComponent fieldSet : formFieldHeaders) {
			headers.get(fieldSet).removeStyleName("conx-form-header-error");
		}
		Set<Field> formFields = fields.keySet();
		for (Field field : formFields) {
			VaadinCollapsiblePhysicalAttributeConfirmActualsFormSectionHeader formFieldHeader = getFieldHeader(field);
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
//			this.verifyButton.setEnabled(false);
			return false;
		} else {
			this.alertPanel.setVisible(false);
//			this.verifyButton.setEnabled(false);
//			this.saveButton.setEnabled(true);
			this.alertPanel.setAlertType(AlertType.SUCCESS);
			this.alertPanel.setMessage(this.header.getTitle() + " is valid.");
			this.alertPanel.setVisible(true);
			return true;
		}
	}

	private VaadinCollapsiblePhysicalAttributeConfirmActualsFormSectionHeader getFieldHeader(Field field) {
		PhysicalAttributeConfirmActualsFieldSetFieldComponent fsf = fields.get(field);
		if (fsf != null) {
			PhysicalAttributeConfirmActualsFieldSetComponent fs = fsf.getFieldSet();
			if (fs != null) {
				return headers.get(fs);
			}
		}
		return null;
	}

	@Override
	public void resetForm() {
		this.alertPanel.setVisible(false);
		setItemDataSource(getItemDataSource());
//		this.saveButton.setEnabled(false);
//		this.verifyButton.setEnabled(false);
//		this.resetButton.setEnabled(false);
	}

	public Object getItemEntity() {
		return null;
	}
}
