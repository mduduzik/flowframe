package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.detail.form.view;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

import org.vaadin.mvp.uibinder.annotation.UiField;

import com.conx.logistics.kernel.ui.components.domain.form.ConXDetailForm;
import com.conx.logistics.kernel.ui.forms.vaadin.impl.VaadinDetailForm;
import com.conx.logistics.kernel.ui.forms.vaadin.listeners.IFormChangeListener;
import com.vaadin.data.Item;
import com.vaadin.ui.VerticalLayout;

public class EntityFormView extends VerticalLayout implements IEntityFormView {
	private static final long serialVersionUID = 1L;

	@UiField
	VerticalLayout mainLayout;

	private VaadinDetailForm form;

	public EntityFormView() {
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
	public void setForm(ConXDetailForm formComponent) {
		this.form = new VaadinDetailForm(formComponent);
		this.form.setSizeFull();
		this.form.setTitle(formComponent.getCaption());
		this.mainLayout.removeAllComponents();
		this.mainLayout.addComponent(this.form);
	}

	@Override
	public void setFormTitle(String title) {
		this.form.setTitle(title);
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
		this.form.setItemDataSource(this.form.getItemDataSource());
	}

	@Override
	public void setItemDataSource(Item item) {
		this.form.setItemDataSource(item);
	}

	@Override
	public void addFormChangeListener(IFormChangeListener listener) {
		this.form.addFormChangeListener(listener);
	}

	@Override
	public void resizeForm(int height) {
		this.form.getLayout().setHeight(height + "px");
	}
}
