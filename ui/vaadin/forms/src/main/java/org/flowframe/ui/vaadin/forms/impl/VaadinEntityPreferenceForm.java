package org.flowframe.ui.vaadin.forms.impl;

import java.util.Collection;

import org.flowframe.ui.vaadin.common.item.PreferenceItem.EntityPreferenceProperty;

import com.vaadin.data.Item;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class VaadinEntityPreferenceForm extends Form {
	private static final long serialVersionUID = 1985860905705407999L;
	private static final String EMPTY_STRING = "";
	private VerticalLayout outerLayout;
	private Panel innerLayoutPanel;
	private GridLayout innerLayout;
	
	public VaadinEntityPreferenceForm() {
		this.innerLayout = new GridLayout();
		this.innerLayout.setColumns(2);
		this.innerLayout.setRows(1);
		this.innerLayout.setSpacing(true);
		this.innerLayout.setWidth("100%");
		this.innerLayout.setColumnExpandRatio(0, 0.25f);
		this.innerLayout.setColumnExpandRatio(1, 0.75f);
		this.innerLayout.setMargin(true);
		
		this.innerLayoutPanel = new Panel();
		this.innerLayoutPanel.setSizeFull();
		this.innerLayoutPanel.setStyleName("light");
		this.innerLayoutPanel.addComponent(this.innerLayout);
		
		this.outerLayout = new VerticalLayout();
		this.outerLayout.setSizeFull();
		this.outerLayout.addComponent(this.innerLayoutPanel);
		this.outerLayout.setExpandRatio(this.innerLayoutPanel, 1.0f);
		
		this.setLayout(this.outerLayout);
	}
	
	@Override
    protected void attachField(Object propertyId, Field field) {
		EntityPreferenceProperty property = (EntityPreferenceProperty)field.getPropertyDataSource();
		field.setWidth("100%");
		Label label = new Label(property.getLabel());
		label.setSizeUndefined();
		field.setCaption(null);
		if (field instanceof TextField) {
			((TextField) field).setNullRepresentation(EMPTY_STRING);
		}
		
		this.innerLayout.addComponent(label);
		this.innerLayout.addComponent(field);
		this.innerLayout.setComponentAlignment(label, Alignment.MIDDLE_RIGHT);
    }
	
	@Override
	public void setItemDataSource(Item newDataSource, Collection<?> propertyIds) {
		this.innerLayout.removeAllComponents();
		super.setItemDataSource(newDataSource, propertyIds);
	}
}
