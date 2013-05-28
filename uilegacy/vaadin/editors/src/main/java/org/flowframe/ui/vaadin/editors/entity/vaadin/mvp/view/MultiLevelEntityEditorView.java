package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.view;

import org.flowframe.ui.vaadin.addons.common.FlowFrameAbstractSplitPanel.ISplitPositionChangeListener;
import org.flowframe.ui.vaadin.addons.common.FlowFrameVerticalSplitPanel;
import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class MultiLevelEntityEditorView extends VerticalLayout implements IMultiLevelEntityEditorView {
	private static final long serialVersionUID = 1L;

	@UiField
	VerticalLayout mainLayout;

	private Component breadCrumb; // Index 0
	private Component header; // Index 1
	private FlowFrameVerticalSplitPanel splitPanel; // Index 2
	private Component detail;
	private Component footer; // Index 3

	private VerticalLayout defaultPanel;

	public MultiLevelEntityEditorView() {
		setSizeFull();
	}

	public void init() {
		splitPanel = new FlowFrameVerticalSplitPanel();
		splitPanel.setSizeFull();
		splitPanel.setImmediate(true);
		splitPanel.setSplitPosition(50);
		splitPanel.setStyleName("conx-entity-editor");
		mainLayout.addComponent(splitPanel);
		mainLayout.setExpandRatio(splitPanel, 1.0f);
	}

	@Override
	public void setBreadCrumb(Component component) {
		if (breadCrumb != null) {
			mainLayout.removeComponent(breadCrumb);
		}
		breadCrumb = component;
		mainLayout.addComponent(breadCrumb, 0);
	}

	@Override
	public void setHeader(Component component) {
		if (header != null) {
			mainLayout.removeComponent(header);
		}
		header = component;
		if (breadCrumb != null) {
			mainLayout.addComponent(header, 1);
		} else {
			mainLayout.addComponent(header, 0);
		}
	}

	@Override
	public void setMaster(Component component) {
		splitPanel.setFirstComponent(component);
	}
	
	public Component getMaster() {
		return splitPanel.getFirstComponent();
	}

	@Override
	public void setDetail(Component component, boolean showDefaultPanel) {
		this.detail = component;
		if (showDefaultPanel) {
			if (defaultPanel == null) {
				Label defaultMessage = new Label("Select an Item to see its detail.");
				defaultMessage.setContentMode(Label.CONTENT_XHTML);
				defaultMessage.setStyleName("conx-default-panel-message");
	
				this.defaultPanel = new VerticalLayout();
				this.defaultPanel.setStyleName("conx-default-panel");
				this.defaultPanel.setSizeFull();
				this.defaultPanel.addComponent(defaultMessage);
				this.defaultPanel.setComponentAlignment(defaultMessage, Alignment.MIDDLE_CENTER);
			}
			splitPanel.setSecondComponent(this.defaultPanel);
		} else {
			splitPanel.setSecondComponent(this.detail);
		}
	}

	@Override
	public void setFooter(Component component) {
		if (footer != null) {
			mainLayout.removeComponent(footer);
		}
		footer = component;
		int index = 1;
		if (breadCrumb != null) {
			index++;
		}
		if (header != null) {
			index++;
		}
		mainLayout.addComponent(footer, index);
	}

	@Override
	public void addSplitPositionChangeListener(ISplitPositionChangeListener listener) {
		this.splitPanel.addSplitPositionChangeListener(listener);
	}

	@Override
	public void showDetail() {
		if (this.detail != null) {
			splitPanel.setSecondComponent(this.detail);
		}
	}
}
