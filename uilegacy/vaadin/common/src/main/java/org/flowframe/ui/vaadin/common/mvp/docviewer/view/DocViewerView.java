package org.flowframe.ui.vaadin.common.mvp.docviewer.view;

import org.vaadin.mvp.uibinder.IUiBindable;
import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.ui.VerticalLayout;

public class DocViewerView extends VerticalLayout implements IDocViewerView,IUiBindable {
	private static final long serialVersionUID = 1L;

	@UiField
	private VerticalLayout mainLayout;

	public DocViewerView() {
	}

	@Override
	public VerticalLayout getMainLayout() {
		mainLayout.setSizeFull();
		return mainLayout;
	}
}
