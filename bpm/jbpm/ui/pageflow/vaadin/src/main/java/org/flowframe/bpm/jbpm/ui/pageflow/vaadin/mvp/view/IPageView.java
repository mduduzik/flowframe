package org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.view;

import org.vaadin.mvp.uibinder.IUiBindable;

import com.vaadin.ui.Component;

public interface IPageView extends IUiBindable {
	public void setContent(Component content);
	public Component getContent();
}
