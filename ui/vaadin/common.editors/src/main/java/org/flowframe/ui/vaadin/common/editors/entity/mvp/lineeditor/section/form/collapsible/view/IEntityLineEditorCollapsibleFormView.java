package org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor.section.form.collapsible.view;

import java.util.Collection;

import org.flowframe.ui.component.domain.form.CollapseableSectionFormComponent;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.IEntityEditorComponentView;
import org.flowframe.ui.vaadin.forms.listeners.IFormChangeListener;

import com.vaadin.data.Item;

public interface IEntityLineEditorCollapsibleFormView extends IEntityEditorComponentView {
	public void setItemDataSource(Item item);
	public void setItemDataSource(Item item, Collection<?> propertyIds);
	public void setForm(CollapseableSectionFormComponent formComponent);
	public void setFormTitle(String title);
	public void addFormChangeListener(IFormChangeListener listener);
	public void saveForm();
	public boolean validateForm();
	public void resetForm();
	public void resizeForm(int height);
}