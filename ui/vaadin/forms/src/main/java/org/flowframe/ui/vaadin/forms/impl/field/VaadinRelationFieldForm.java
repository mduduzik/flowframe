package org.flowframe.ui.vaadin.forms.impl.field;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.flowframe.ui.vaadin.forms.impl.VaadinFormAlertPanel;
import org.flowframe.ui.vaadin.forms.impl.VaadinJPAFieldFactory;
import org.flowframe.ui.vaadin.forms.impl.ext.VaadinFormToolStrip;
import org.flowframe.ui.vaadin.forms.impl.ext.VaadinFormToolStrip.VaadinFormToolStripButton;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.MouseEvents;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class VaadinRelationFieldForm extends Form {
	private static final long serialVersionUID = -63922931L;

	private VaadinFormToolStrip toolStrip;
	private VaadinFormToolStripButton verifyButton;
	private VaadinFormToolStripButton saveButton;
	private VaadinFormToolStripButton resetButton;
	private VaadinFormAlertPanel alertPanel;
	private VerticalLayout layout;
	private GridLayout innerLayout;
	private Set<Field> fields;
	private int nextRowIndex = 0;
	private VaadinFormToolStripButton editButton;
	private ValueChangeListener listener;

	public VaadinRelationFieldForm() {
		this.layout = new VerticalLayout();
		this.innerLayout = new GridLayout(2, 1);
		this.alertPanel = new VaadinFormAlertPanel();
		this.fields = new HashSet<Field>();

		initialize();
	}

	private void initialize() {
		this.toolStrip = new VaadinFormToolStrip();

		this.verifyButton = this.toolStrip.addToolStripButton(VaadinFormToolStrip.TOOLSTRIP_IMG_VERIFY_PNG);
		this.verifyButton.setEnabled(false);
		this.verifyButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				validateForm();
			}
		});
		
		this.editButton = this.toolStrip.addToolStripButton(VaadinFormToolStrip.TOOLSTRIP_IMG_EDIT_PNG);
		this.editButton.setEnabled(true);
		this.editButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				VaadinRelationFieldForm.this.setReadOnly(false);
				VaadinRelationFieldForm.this.innerLayout.setSpacing(true);
				VaadinRelationFieldForm.this.editButton.setEnabled(false);
			}
		});

		this.saveButton = this.toolStrip.addToolStripButton(VaadinFormToolStrip.TOOLSTRIP_IMG_SAVE_PNG);
		this.saveButton.setEnabled(false);
		this.saveButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				saveForm();
			}
		});

		this.resetButton = this.toolStrip.addToolStripButton(VaadinFormToolStrip.TOOLSTRIP_IMG_RESET_PNG);
		this.resetButton.setEnabled(false);
		this.resetButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				resetForm();
			}
		});
		
		this.alertPanel.setVisible(false);
		this.alertPanel.addCloseListener(new MouseEvents.ClickListener() {
			private static final long serialVersionUID = 5815832688929242745L;

			@Override
			public void click(MouseEvents.ClickEvent event) {
				VaadinRelationFieldForm.this.alertPanel.setVisible(false);
			}
		});

		this.innerLayout.setWidth("100%");
		this.innerLayout.setMargin(true);

		this.layout.setSizeFull();
		this.layout.addStyleName("conx-inner-form");
		this.layout.addComponent(toolStrip);
		this.layout.addComponent(alertPanel);
		this.layout.addComponent(innerLayout);
		
		this.listener = new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				onFormChanged(event);
			}
		};
		
		setImmediate(true);
		setSizeFull();
		setFormFieldFactory(new VaadinJPAFieldFactory());
		setLayout(layout);
		// False so that commit() must be called explicitly
		setWriteThrough(false);
		// Disallow invalid data from acceptance by the container
		setInvalidCommitted(false);
	}
	
	private void saveForm() {
		try {
			this.commit();
			this.setReadOnly(true);
			this.innerLayout.setSpacing(false);
			this.resetButton.setEnabled(false);
			this.saveButton.setEnabled(false);
			this.editButton.setEnabled(true);
		} catch (SourceException e) {
			this.alertPanel.setMessage(e.getMessage());
			this.alertPanel.setVisible(true);
		} catch (InvalidValueException e) {
			this.alertPanel.setMessage(e.getMessage());
			this.alertPanel.setVisible(true);
		}
	}
	
	private void onFormChanged(Property.ValueChangeEvent event) {
		this.saveButton.setEnabled(false);
		this.verifyButton.setEnabled(true);
		this.resetButton.setEnabled(true);
	}
	
	@Override
	protected void attachField(Object propertyId, Field field) {
        if (propertyId == null || field == null) {
            return;
        }
        
        if (this.innerLayout.getRows() >= nextRowIndex) {
        	this.innerLayout.setRows(nextRowIndex + 1);
        }
        
        if (field instanceof TextField) {
        	((TextField) field).addListener(new TextChangeListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void textChange(TextChangeEvent event) {
					VaadinRelationFieldForm.this.listener.valueChange(null);
				}
			});
        } else {
        	field.addListener(listener);
        }
        
        if (field instanceof AbstractComponent) {
        	((AbstractComponent) field).setImmediate(true);
        }
        
        field.setWidth("100%");
        this.fields.add(field);
        Label caption = new Label(field.getCaption());
        caption.setStyleName("conx-inner-form-field-caption");
        this.innerLayout.addComponent(caption, 0, nextRowIndex, 0, nextRowIndex);
        this.innerLayout.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);
        field.setCaption(null);
        this.innerLayout.addComponent(field, 1, nextRowIndex, 1, nextRowIndex);
        this.nextRowIndex++;
    }

	public void resetForm() {
		this.alertPanel.setVisible(false);
		this.discard();
		this.setReadOnly(true);
		this.saveButton.setEnabled(false);
		this.verifyButton.setEnabled(false);
		this.editButton.setEnabled(true);
		this.resetButton.setEnabled(false);
	}
	
	@Override
	public void setItemDataSource(Item newDataSource) {
		this.innerLayout.removeAllComponents();
		this.fields.clear();
		this.nextRowIndex = 0;
		super.setItemDataSource(newDataSource);
	}
	
	@Override
	public void setItemDataSource(Item newDataSource, Collection<?> propertyIds) {
		this.innerLayout.removeAllComponents();
		this.fields.clear();
		this.nextRowIndex = 0;
		super.setItemDataSource(newDataSource, propertyIds);
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
			this.verifyButton.setEnabled(false);
			return false;
		} else {
			this.alertPanel.setVisible(false);
			this.verifyButton.setEnabled(false);
			this.saveButton.setEnabled(true);
			return true;
		}
	}
}