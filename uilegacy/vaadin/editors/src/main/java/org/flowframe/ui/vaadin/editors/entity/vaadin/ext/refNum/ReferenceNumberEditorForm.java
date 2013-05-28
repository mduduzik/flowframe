package org.flowframe.ui.vaadin.editors.entity.vaadin.ext.refNum;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.flowframe.ui.vaadin.forms.FormMode;
import org.flowframe.ui.vaadin.forms.impl.VaadinFormHeader;
import org.flowframe.ui.vaadin.forms.listeners.IFormChangeListener;

import com.vaadin.data.Property;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Form;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ReferenceNumberEditorForm extends Form {
	private static final long serialVersionUID = -1298561365125628304L;

	private VaadinFormHeader header;
	private VerticalLayout layout;
	private VerticalLayout innerLayout;
	private Panel innerLayoutPanel;
	private Set<IFormChangeListener> listeners;
	private FormMode mode;

	public ReferenceNumberEditorForm() {
		this.header = new VaadinFormHeader();
		this.layout = new VerticalLayout();
		this.innerLayout = new VerticalLayout();
		this.innerLayoutPanel = new Panel();
		this.listeners = new HashSet<IFormChangeListener>();

		initialize();
	}

	@SuppressWarnings("deprecation")
	private void initialize() {
		this.header = new VaadinFormHeader();
		this.header.setTitle("Reference Number");

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
		this.layout.addComponent(innerLayoutPanel);
		this.layout.setExpandRatio(innerLayoutPanel, 1.0f);

		setMode(FormMode.CREATING);
		setLayout(layout);
		// False so that commit() must be called explicitly
		setWriteThrough(false);
		// Disallow invalid data from acceptance by the container
		setInvalidCommitted(false);
	}

	@Override
	protected void attachField(Object propertyId, com.vaadin.ui.Field field) {
		if (propertyId == null || field == null) {
			return;
		}
		if ("value".equals(propertyId)) {
			field.setWidth("100%");
			field.setHeight("75px");
			if (field instanceof TextField) {
				((TextField) field).addListener(new TextChangeListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void textChange(TextChangeEvent event) {
						fireFieldChangedEvent();
					}
				});
			}
			innerLayout.addComponent(field);
		} else if ("type".equals(propertyId)) {
			field.setWidth("30%");
			if (field instanceof AbstractSelect) {
				((AbstractSelect) field).setImmediate(true);
				((AbstractSelect) field).addListener(new ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					
					@Override
					public void valueChange(Property.ValueChangeEvent event) {
						fireFieldChangedEvent();
					}
				});
			}
			innerLayout.addComponentAsFirst(field);
		} else {
			innerLayout.addComponent(field);
		}
	}
	
	private void fireFieldChangedEvent() {
		for (IFormChangeListener listener : listeners) {
			listener.onFormChanged();
		}
	}

	public FormMode getMode() {
		return mode;
	}

	public void setMode(FormMode mode) {
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

	@Override
	public void setItemDataSource(com.vaadin.data.Item newDataSource, Collection<?> propertyIds) {
		innerLayout.removeAllComponents();
		super.setItemDataSource(newDataSource, propertyIds);
	}
	
	public void addListener(IFormChangeListener listener) {
		this.listeners.add(listener);
	}
}
