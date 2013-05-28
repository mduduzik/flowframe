package org.flowframe.ui.vaadin.forms.impl.field;

import java.util.HashSet;
import java.util.Set;

import com.vaadin.addon.jpacontainer.fieldfactory.OneToOneForm;
import com.vaadin.data.Property;
import com.vaadin.ui.Field;

public class VaadinJPAOneToOneForm extends OneToOneForm  {
	private static final long serialVersionUID = 1L;
	
	private Set<Field> fields;
	private ValueChangeListener listener;
	private Set<ValueChangeListener> subscribers;

	public VaadinJPAOneToOneForm() {
		super();
		this.fields = new HashSet<Field>();
		this.subscribers = new HashSet<Property.ValueChangeListener>();
		this.listener = new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				onFormChanged(event);
			}
		};
	}
	
	private void onFormChanged(Property.ValueChangeEvent event) {
		for (ValueChangeListener subscriber : subscribers) {
			subscriber.valueChange(event);
		}
	}
	
	@Override
	public void addListener(ValueChangeListener listener) {
		this.subscribers.add(listener);
	}
	
	@Override
	public void removeListener(ValueChangeListener listener) {
		this.subscribers.remove(listener);
	}
	
	@Override
	protected void attachField(Object propertyId, Field field) {
		super.attachField(propertyId, field);
		field.addListener(listener);
		this.fields.add(field);
	}

	public Set<Field> getFields() {
		return fields;
	}
}
