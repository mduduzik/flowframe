package org.flowframe.ui.vaadin.common.editors.entity.mvp.lineeditor.section.form.simple.view;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

import org.flowframe.ui.component.domain.form.SimpleFormComponent;
import org.flowframe.ui.vaadin.forms.impl.VaadinSimpleForm;
import org.flowframe.ui.vaadin.forms.listeners.IFormChangeListener;
import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.data.Item;
import com.vaadin.ui.VerticalLayout;

public class EntityLineEditorSimpleFormView extends VerticalLayout implements IEntityLineEditorSimpleFormView {
	private static final long serialVersionUID = 1L;

	@UiField
	VerticalLayout mainLayout;
	
	private VaadinSimpleForm form;
	
	public EntityLineEditorSimpleFormView() {
	}
	
	@Override
	public void setItemDataSource(Item item, Collection<?> propertyIds) {
		this.form.setItemDataSource(item, propertyIds);
	}

	@Override
	public void init() {
		setSizeFull();
	}

	@Override
	public void setForm(SimpleFormComponent formComponent) {
		this.form = new VaadinSimpleForm((SimpleFormComponent) formComponent);
		this.form.setSizeFull();
		this.form.setTitle(formComponent.getCaption());
		this.mainLayout.removeAllComponents();
		this.mainLayout.addComponent(form);
	}

	@Override
	public void setItemDataSource(Item item) {
		this.form.setItemDataSource(item);
	}

	@Override
	public void setFormTitle(String title) {
		this.form.setTitle(title);
	}

	@Override
	public void addFormChangeListener(IFormChangeListener listener) {
		this.form.addFormChangeListener(listener);
	}

	@SuppressWarnings("unused")
	@Override
	public void saveForm() {
		try {
			this.form.commit();
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			e.printStackTrace();
		}
	}

	@Override
	public boolean validateForm() {
		return this.form.validateForm();
	}

	@Override
	public void resetForm() {
		this.form.resetForm();
	}

	@Override
	public void resizeForm(int height) {
		this.form.getLayout().setHeight(height + "px");
	}
}
