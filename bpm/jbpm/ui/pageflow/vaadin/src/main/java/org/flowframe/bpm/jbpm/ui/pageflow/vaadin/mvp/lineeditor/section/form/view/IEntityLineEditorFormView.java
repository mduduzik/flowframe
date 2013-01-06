package org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.section.form.view;

import org.vaadin.mvp.uibinder.IUiBindable;

import org.flowframe.ui.vaadin.forms.impl.VaadinForm;
import org.flowframe.ui.vaadin.forms.listeners.IFormChangeListener;

public interface IEntityLineEditorFormView extends IUiBindable {
	public void setForm(VaadinForm form) throws Exception;
	public VaadinForm getForm() throws Exception;
	public void addListener(IFormChangeListener listener) throws Exception;
}