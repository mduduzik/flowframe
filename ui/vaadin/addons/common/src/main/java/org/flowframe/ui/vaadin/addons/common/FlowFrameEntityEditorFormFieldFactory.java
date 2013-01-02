package org.flowframe.ui.vaadin.addons.common;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;

public class FlowFrameEntityEditorFormFieldFactory extends DefaultFieldFactory {
	private static final long serialVersionUID = -835746547857641L;

	public Field createField(Item item, Object propertyId, Component uiContext) {
		Field field = super.createField(item, propertyId, uiContext);
		field.setWidth("80%");
		return field;
	}

}
