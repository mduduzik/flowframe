package org.flowframe.bpm.jbpm.ui.pageflow.vaadin.ext.form;

import org.flowframe.ui.component.domain.form.PhysicalAttributeConfirmActualsFieldSetComponent;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;

public class VaadinCollapsiblePhysicalAttributeConfirmActualsFormSectionHeader extends HorizontalLayout {
	private static final long serialVersionUID = 1010002929298L;

	private Embedded arrow;
	private Layout layout;
	private String caption;
	private Label label;

	public VaadinCollapsiblePhysicalAttributeConfirmActualsFormSectionHeader(PhysicalAttributeConfirmActualsFieldSetComponent fieldSet, Layout layout) {
		this.layout = layout;
		this.arrow = new Embedded();
		this.label = new Label(fieldSet.getCaption());

		setWidth("100%");
		setHeight("20px");
		setStyleName("conx-entity-editor-form-separator");

		initialize();
	}

	private void initialize() {
		this.layout.setVisible(true);
		
		HorizontalLayout leftPanel = new HorizontalLayout();
		leftPanel.setHeight("25px");
		leftPanel.setSpacing(true);

		arrow.setStyleName("conx-entity-editor-form-separator-arrow");
		arrow.addStyleName("conx-entity-editor-form-separator-arrow-expanded");
		arrow.setWidth("18px");
		arrow.setHeight("10px");

		label.setStyleName("conx-entity-editor-form-separator-caption");
		label.setHeight("15px");
		label.setCaption(caption);

		leftPanel.addComponent(arrow);
		leftPanel.addComponent(label);
		addComponent(leftPanel);
		setComponentAlignment(leftPanel, Alignment.TOP_LEFT);

		this.addListener(new LayoutClickListener() {
			private static final long serialVersionUID = 1L;

			public void layoutClick(LayoutClickEvent event) {
				if (isExpanded()) {
					setExpanded(false);
				} else {
					setExpanded(true);
				}
			}
		});
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public boolean isExpanded() {
		return layout.isVisible();
	}

	public void setExpanded(boolean isExpanded) {
		if (isExpanded) {
			arrow.addStyleName("conx-entity-editor-form-separator-arrow-expanded");
		} else {
			arrow.removeStyleName("conx-entity-editor-form-separator-arrow-expanded");
		}
		layout.setVisible(isExpanded);
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}
}
