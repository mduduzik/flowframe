package org.flowframe.ui.pageflow.vaadin.mvp.editor.form.view;

import org.vaadin.mvp.uibinder.IUiBindable;

import org.flowframe.ui.vaadin.forms.impl.VaadinForm;
import org.flowframe.ui.vaadin.forms.listeners.IFormChangeListener;

public interface IEditorFormView extends IUiBindable {
	public void setForm(VaadinForm form) throws Exception;
	public VaadinForm getForm() throws Exception;
	public void addListener(IFormChangeListener listener) throws Exception;
}
