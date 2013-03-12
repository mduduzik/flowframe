package org.flowframe.ui.vaadin.common.editors.pageflow.ext.form;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.flowframe.ui.component.domain.form.ConfirmActualsFieldSetComponent;
import org.flowframe.ui.component.domain.form.ConfirmActualsFieldSetFieldComponent;
import org.flowframe.ui.component.domain.form.ConfirmActualsFormComponent;
import org.flowframe.ui.vaadin.forms.FormMode;
import org.flowframe.ui.vaadin.forms.impl.VaadinForm;
import org.flowframe.ui.vaadin.forms.impl.VaadinFormAlertPanel;
import org.flowframe.ui.vaadin.forms.impl.VaadinFormFieldAugmenter;
import org.flowframe.ui.vaadin.forms.impl.VaadinFormHeader;
import org.flowframe.ui.vaadin.forms.impl.VaadinJPAFieldFactory;

import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.MouseEvents;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class VaadinConfirmActualsForm extends VaadinForm {
	private static final long serialVersionUID = -639931L;

	private VaadinFormHeader header;
	private VaadinFormAlertPanel alertPanel;
	private VerticalLayout layout;
	private Panel innerLayoutPanel;
	private GridLayout innerLayout;
	private GridLayout captionLayout;
	private GridLayout expectedLayout;
	private GridLayout actualLayout;
	private ConfirmActualsFormComponent componentForm;
	private Set<Field> fields;
	private HashMap<ConfirmActualsFieldSetFieldComponent, Integer> fieldIndexMap;
	private int nextIndex;
	private Embedded placeholder;

	private FormMode mode;

	public VaadinConfirmActualsForm(ConfirmActualsFormComponent componentForm) {
		this.header = new VaadinFormHeader();
		this.layout = new VerticalLayout();
		this.innerLayout = new GridLayout(3, 1);
		this.captionLayout = new GridLayout(1, 1);
		this.expectedLayout = new GridLayout(1, 1);
		this.actualLayout = new GridLayout(1, 1);
		this.alertPanel = new VaadinFormAlertPanel();
		this.componentForm = componentForm;
		this.fields = new HashSet<Field>();
		this.fieldIndexMap = new HashMap<ConfirmActualsFieldSetFieldComponent, Integer>();
		this.nextIndex = 1;
		this.placeholder = new Embedded();

		initialize();
	}

	@SuppressWarnings("deprecation")
	private void initialize() {
		this.placeholder.setHeight("22px");
		this.placeholder.setWidth("1px");
		
		this.alertPanel.setVisible(false);
		this.alertPanel.addCloseListener(new MouseEvents.ClickListener() {
			private static final long serialVersionUID = 5815832688929242745L;

			@Override
			public void click(MouseEvents.ClickEvent event) {
				VaadinConfirmActualsForm.this.alertPanel.setVisible(false);
			}
		});
		
		this.captionLayout.setWidth("100%");
		this.captionLayout.setSpacing(true);

		this.expectedLayout.setWidth("100%");
		this.expectedLayout.setSpacing(true);

		this.actualLayout.setWidth("100%");
		this.actualLayout.setSpacing(true);

		this.innerLayout.setWidth("65%");
		this.innerLayout.setSpacing(true);
		this.innerLayout.setMargin(true);
		this.innerLayout.addComponent(captionLayout, 0, 0, 0, 0);
		this.innerLayout.addComponent(expectedLayout, 1, 0, 1, 0);
		this.innerLayout.addComponent(actualLayout, 2, 0, 2, 0);
		this.innerLayout.setColumnExpandRatio(0, 0.166f);
		this.innerLayout.setColumnExpandRatio(1, 0.417f);
		this.innerLayout.setColumnExpandRatio(2, 0.417f);

		this.innerLayoutPanel = new Panel();
		this.innerLayoutPanel.setSizeFull();
		this.innerLayoutPanel.getLayout().setMargin(false, true, false, true);
		this.innerLayoutPanel.setStyleName("light");
		this.innerLayoutPanel.addComponent(innerLayout);

		this.layout.setSizeFull();
		this.layout.setStyleName("conx-entity-editor-form");
		this.layout.addComponent(header);
		this.layout.addComponent(alertPanel);
		this.layout.addComponent(innerLayoutPanel);
		this.layout.setExpandRatio(innerLayoutPanel, 1.0f);

		setImmediate(true);
		setSizeFull();
		setFormMode(FormMode.EDITING);
		setFormFieldFactory(new VaadinJPAFieldFactory());
		setLayout(layout);
		// False so that commit() must be called explicitly
		setWriteThrough(false);
		// Disallow invalid data from acceptance by the container
		setInvalidCommitted(false);
	}

	public FormMode getMode() {
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
	protected void attachField(Object propertyId, com.vaadin.ui.Field field) {
		if (propertyId == null || field == null) {
			return;
		}
		ConfirmActualsFieldSetComponent fieldSet = componentForm.getFieldSet();
		if (fieldSet != null) {
			ConfirmActualsFieldSetFieldComponent fieldComponent = fieldSet.getFieldSetField((String) propertyId);
			if (fieldComponent != null) {
				Integer index = fieldIndexMap.get(fieldComponent);
				if (index == null) {
					index = nextIndex;
					nextIndex++;
					fieldIndexMap.put(fieldComponent, index);
				}

				fields.add(field);
				field.setWidth("100%");
				VaadinFormFieldAugmenter.augment(field, fieldComponent);

				if (fieldSet.isExpected(propertyId)) {
					if (index >= this.expectedLayout.getRows()) {
						this.expectedLayout.setRows(index + 1);
					}
					
					if (index >= this.captionLayout.getRows()) {
						this.captionLayout.setRows(index + 1);
					}
					
					Label label = new Label(field.getCaption());
					label.setStyleName("conx-confirm-actuals-field-caption");
					label.setWidth("100%");
					captionLayout.addComponent(label, 0, index, 0, index);
					
					field.setEnabled(false);
					field.setCaption(null);
					expectedLayout.addComponent(field, 0, index, 0, index);
				} else {
					if (index >= this.actualLayout.getRows()) {
						this.actualLayout.setRows(index + 1);
					}
					
					field.setCaption(null);
					actualLayout.addComponent(field, 0, index, 0, index);
				}
			}
		}
	}

	@Override
	public void setItemDataSource(Item newDataSource, Collection<?> propertyIds) {
		this.nextIndex = 1;
		this.fieldIndexMap.clear();
		this.captionLayout.removeAllComponents();
		this.captionLayout.addComponent(this.placeholder, 0, 0, 0, 0);
		this.expectedLayout.removeAllComponents();
		this.expectedLayout.addComponent(new VaadinConfirmActualsFormSectionHeader("Expected"), 0, 0, 0, 0);
		this.actualLayout.removeAllComponents();
		this.actualLayout.addComponent(new VaadinConfirmActualsFormSectionHeader("Actual"), 0, 0, 0, 0);
		this.fields.clear();
		super.setItemDataSource(newDataSource, propertyIds);
	}

	@Override
	public void setItemDataSource(Item newDataSource) {
		this.nextIndex = 1;
		this.fieldIndexMap.clear();
		this.captionLayout.removeAllComponents();
		this.captionLayout.addComponent(this.placeholder, 0, 0, 0, 0);
		this.expectedLayout.removeAllComponents();
		this.expectedLayout.addComponent(new VaadinConfirmActualsFormSectionHeader("Expected"), 0, 0, 0, 0);
		this.actualLayout.removeAllComponents();
		this.actualLayout.addComponent(new VaadinConfirmActualsFormSectionHeader("Actual"), 0, 0, 0, 0);
		this.fields.clear();
		super.setItemDataSource(newDataSource);
	}

	public FormMode getFormMode() {
		return mode;
	}
	
	public void resetForm() {
		this.alertPanel.setVisible(false);
		setItemDataSource(getItemDataSource());
	}

	public boolean validateForm() {
		boolean firstErrorFound = false;
		for (Field field : fields) {
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
	
	public Object getItemEntity() {
		return null;
	}

	public ConfirmActualsFormComponent getComponentModel() {
		return this.componentForm;
	}
}
