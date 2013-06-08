package org.flowframe.ui.pageflow.vaadin.mvp.embedded.view;

import org.vaadin.mvp.uibinder.IUiBindable;
import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.ui.VerticalLayout;

public class EmbeddedAppView extends VerticalLayout implements IEmbeddedAppView,IUiBindable {
	private static final long serialVersionUID = 1L;

	@UiField
	private VerticalLayout mainLayout;

	public EmbeddedAppView() {
	}

	@Override
	public VerticalLayout getMainLayout() {
		mainLayout.setSizeFull();
		return mainLayout;
	}
}
