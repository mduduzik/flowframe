package org.flowframe.ui.vaadin.forms.impl.field;

import java.util.Collection;

import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.Field;
import com.vaadin.ui.VerticalLayout;

public class VaadinPlaceHolderField extends VerticalLayout implements Field {
	private static final long serialVersionUID = 1L;
	
	private Property propertyDataSource;
	private int tabIndex;
	
	public VaadinPlaceHolderField() {
		setWidth("100%");
		setHeight("25px");
		setReadOnly(true);
	}

	@Override
	public boolean isInvalidCommitted() {
		return false;
	}

	@Override
	public void setInvalidCommitted(boolean isCommitted) {
	}

	@Override
	public void commit() throws SourceException, InvalidValueException {
	}

	@Override
	public void discard() throws SourceException {
	}

	@Override
	public boolean isWriteThrough() {
		return false;
	}

	@Override
	public void setWriteThrough(boolean writeThrough) throws SourceException, InvalidValueException {
	}

	@Override
	public boolean isReadThrough() {
		return false;
	}

	@Override
	public void setReadThrough(boolean readThrough) throws SourceException {
	}

	@Override
	public boolean isModified() {
		return false;
	}

	@Override
	public void addValidator(Validator validator) {
		
	}

	@Override
	public void removeValidator(Validator validator) {
		
	}

	@Override
	public Collection<Validator> getValidators() {
		return null;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public void validate() throws InvalidValueException {
	}

	@Override
	public boolean isInvalidAllowed() {
		return false;
	}

	@Override
	public void setInvalidAllowed(boolean invalidValueAllowed) throws UnsupportedOperationException {
	}

	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public void setValue(Object newValue) throws ReadOnlyException, ConversionException {
	}

	@Override
	public Class<?> getType() {
		return null;
	}

	@Override
	public void addListener(ValueChangeListener listener) {
	}

	@Override
	public void removeListener(ValueChangeListener listener) {
	}

	@Override
	public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
	}

	@Override
	public void setPropertyDataSource(Property newDataSource) {
		this.propertyDataSource = newDataSource;
	}

	@Override
	public Property getPropertyDataSource() {
		return this.propertyDataSource;
	}

	@Override
	public int getTabIndex() {
		return this.tabIndex;
	}

	@Override
	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	@Override
	public boolean isRequired() {
		return false;
	}

	@Override
	public void setRequired(boolean required) {
	}

	@Override
	public void setRequiredError(String requiredMessage) {
	}

	@Override
	public String getRequiredError() {
		return "";
	}
	
	@Override
	public void focus() {
		
	}
}
