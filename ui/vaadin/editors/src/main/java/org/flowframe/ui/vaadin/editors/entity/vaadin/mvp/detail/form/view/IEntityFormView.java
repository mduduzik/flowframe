package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.detail.form.view;

import java.util.Collection;

import org.flowframe.ui.component.domain.form.DetailFormComponent;
import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.IEntityEditorComponentView;
import org.flowframe.ui.vaadin.forms.listeners.IFormChangeListener;

import com.vaadin.data.Item;

public interface IEntityFormView extends IEntityEditorComponentView {
	public void setItemDataSource(Item item);
	public void setItemDataSource(Item item, Collection<?> propertyIds);
	public void setForm(DetailFormComponent formComponent);
	public void setFormTitle(String title);
	public void addFormChangeListener(IFormChangeListener listener);
	public void saveForm();
	public boolean validateForm();
	public void resetForm();
	public void resizeForm(int height);
}