package org.flowframe.ui.vaadin.common.editors.entity.ext.header;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Embedded;

public class EntityEditorBreadCrumbSeparator extends Embedded {
	private static final long serialVersionUID = 5053710099980198438L;
	
	private static final String BREADCRUMB_SEPARATOR_PNG = "breadcrumb/img/conx-bread-crumb-arrow.png";

	public EntityEditorBreadCrumbSeparator() {
		setIcon(new ThemeResource(BREADCRUMB_SEPARATOR_PNG));
		setStyleName("conx-entity-editor-bread-crumb-separator");
		setHeight("18px");
		setWidth("15px");
	}

}
