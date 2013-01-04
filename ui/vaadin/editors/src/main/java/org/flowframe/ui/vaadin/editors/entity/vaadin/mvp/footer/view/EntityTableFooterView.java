package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.footer.view;

import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class EntityTableFooterView extends VerticalLayout implements IEntityTableFooterView {
	private static final long serialVersionUID = 1L;
	
	@UiField
	HorizontalLayout mainLayout;

	public EntityTableFooterView() {
		setSizeFull();
	}

	@Override
	public void init() {
	}
}
