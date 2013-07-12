package org.flowframe.ui.pageflow.vaadin.mvp.editor.form.view;

import org.vaadin.mvp.uibinder.annotation.UiField;

import org.flowframe.ui.pageflow.vaadin.ext.mvp.IVaadinDataComponent;
import org.flowframe.ui.vaadin.forms.impl.VaadinForm;
import org.flowframe.ui.vaadin.forms.listeners.IFormChangeListener;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.VerticalLayout;

public class EditorFormView extends VerticalLayout implements IEditorFormView,
		IVaadinDataComponent {
	private static final long serialVersionUID = 1L;

	@UiField
	VerticalLayout mainLayout;

	private VaadinForm form;

	public EditorFormView() {
		setSizeFull();
	}

	@Override
	public void setForm(VaadinForm form) throws Exception {
		try {
			this.mainLayout.replaceComponent(this.form, form);
			this.form = form;
			this.mainLayout.setExpandRatio(this.form, 1.0f);
		} catch (NullPointerException e) {
			throw new Exception(
					"The view was never bound, so the form could not be set.");
		}
	}

	@Override
	public void addListener(IFormChangeListener listener) throws Exception {
		if (this.form != null) {
			this.form.addListener(listener);
		} else {
			throw new Exception(
					"Could not call addListener since the form was never set.");
		}
	}

	@Override
	public VaadinForm getForm() throws Exception {
		return this.form;
	}

	@Override
	public Object getData() {
		if (form != null) {
			Item item = this.form.getItemDataSource();
			if (item instanceof BeanItem) {
				return ((BeanItem<?>) item).getBean();
			} else if (item instanceof JPAContainerItem) {
				return ((JPAContainerItem<?>) item).getEntity();
			}
		}
		return null;
	}

	@Override
	public boolean validate() {
		boolean valid = false;
		try {
			valid = this.form.validateForm();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return valid;
	}
	
	
}
