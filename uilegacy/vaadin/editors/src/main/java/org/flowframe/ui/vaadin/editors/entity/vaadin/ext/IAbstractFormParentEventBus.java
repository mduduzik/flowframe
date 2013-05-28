package org.flowframe.ui.vaadin.editors.entity.vaadin.ext;

public abstract interface IAbstractFormParentEventBus {
	public abstract void saveForm();
	public abstract void validateForm();
	public abstract void resetForm();
	public abstract void resizeForm(int newHeight);
	public abstract void formChanged();
	public abstract void formValidated();
}
