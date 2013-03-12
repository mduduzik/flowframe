package org.flowframe.ui.vaadin.common.editors.pageflow.ext.form;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class VaadinConfirmActualsFormSectionHeader extends HorizontalLayout {
	private static final long serialVersionUID = -6428249611638991639L;
	
	private Label label;

	public VaadinConfirmActualsFormSectionHeader(String caption) {
		this.label = new Label();
		this.label.setValue(caption);

		setWidth("100%");
		setHeight("20px");
		setStyleName("conx-entity-editor-form-separator");

		initialize();
	}

	private void initialize() {
		HorizontalLayout leftPanel = new HorizontalLayout();
		leftPanel.setHeight("25px");
		leftPanel.setSpacing(true);

		label.setStyleName("conx-entity-editor-form-separator-caption");
		label.setHeight("15px");

		leftPanel.addComponent(label);
		addComponent(leftPanel);
		setComponentAlignment(leftPanel, Alignment.TOP_LEFT);
	}
}
