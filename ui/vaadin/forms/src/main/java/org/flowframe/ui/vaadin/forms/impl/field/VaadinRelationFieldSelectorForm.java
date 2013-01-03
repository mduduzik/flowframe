package org.flowframe.ui.vaadin.forms.impl.field;

import java.util.HashSet;

import org.flowframe.ui.vaadin.forms.impl.ext.VaadinFormToolStrip;
import org.flowframe.ui.vaadin.forms.impl.ext.VaadinFormToolStrip.VaadinFormToolStripButton;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.VerticalLayout;

public class VaadinRelationFieldSelectorForm extends VerticalLayout implements Container.Viewer {
	private static final long serialVersionUID = 1L;
	
	private VaadinFormToolStrip toolStrip;
	private VaadinFormToolStripButton verifyButton;
	private VaadinFormToolStripButton saveButton;
	private VaadinFormToolStripButton resetButton;
	private AbstractSelect selector;
	private Property property;
	private HashSet<ValueChangeListener> listeners;
	
	public VaadinRelationFieldSelectorForm() {
		initialize();
	}
	
	private void initialize() {
		this.listeners = new HashSet<ValueChangeListener>();
		this.toolStrip = new VaadinFormToolStrip();

		this.verifyButton = this.toolStrip.addToolStripButton(VaadinFormToolStrip.TOOLSTRIP_IMG_VERIFY_PNG);
		this.verifyButton.setEnabled(false);
		this.verifyButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				VaadinRelationFieldSelectorForm.this.saveButton.setEnabled(true);
				VaadinRelationFieldSelectorForm.this.verifyButton.setEnabled(false);
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
		
		this.selector = new NativeSelect();
		this.selector.setMultiSelect(false);
		this.selector.setCaption("Select a type:");
		this.selector.setItemCaptionMode(NativeSelect.ITEM_CAPTION_MODE_PROPERTY);
		this.selector.setItemCaptionPropertyId("name");
		this.selector.setImmediate(true);
		this.selector.setEnabled(false);
		this.selector.setWidth("100%");
		this.selector.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				VaadinRelationFieldSelectorForm.this.verifyButton.setEnabled(event.getProperty().getValue() != null);
				VaadinRelationFieldSelectorForm.this.property = event.getProperty();
			}
		});
		
		VerticalLayout selectorLayout = new VerticalLayout();
		selectorLayout.setWidth("100%");
		selectorLayout.setMargin(true);
		selectorLayout.addComponent(this.selector);
		
		setWidth("100%");
		setStyleName("conx-inner-form");
		addComponent(this.toolStrip);
		addComponent(selectorLayout);
	}
	
	@Override
	public void setContainerDataSource(Container container) {
		this.selector.setContainerDataSource(container);
		this.selector.setEnabled(true);
	}
	
	private void saveForm() {
		ValueChangeEvent event = new ValueChangeEvent() {
			private static final long serialVersionUID = 1L;

			@Override
			public Property getProperty() {
				return VaadinRelationFieldSelectorForm.this.property;
			}
		};
		for (ValueChangeListener listener : this.listeners) {
			listener.valueChange(event);
		}
	}
	
	public void addListener(ValueChangeListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(ValueChangeListener listener) {
		this.listeners.remove(listener);
	}

	@Override
	public Container getContainerDataSource() {
		return this.selector.getContainerDataSource();
	}
	
	@SuppressWarnings("rawtypes")
	public Object getEntity() {
		Item item = this.selector.getItem(this.selector.getValue());
		if (item instanceof JPAContainerItem) {
			return ((JPAContainerItem) item).getEntity();
		}
		return null;
	}
}
