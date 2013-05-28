package org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.view;

import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class EntityLineEditorView extends VerticalLayout implements IEntityLineEditorView {
	private static final long serialVersionUID = 1L;
	
	@UiField
	VerticalLayout mainLayout;
	
	private TabSheet content;

	private VerticalLayout defaultLayout;
	
	@Override
	public void init() {
		Label defaultLabel = new Label("Select an item to use its editors.");
		defaultLabel.setWidth("200px");
		defaultLabel.setStyleName("conx-line-editor-default-layout-label");
		
		this.defaultLayout = new VerticalLayout();
		this.defaultLayout.setStyleName("conx-line-editor-default-layout");
		this.defaultLayout.setSizeFull();
		this.defaultLayout.addComponent(defaultLabel);
		this.defaultLayout.setComponentAlignment(defaultLabel, Alignment.MIDDLE_CENTER);
		
		this.mainLayout.removeAllComponents();
		this.mainLayout.addComponent(defaultLayout);
		this.mainLayout.setExpandRatio(defaultLayout, 1.0f);
	}
	
	@Override
	public void addTab(Component content, String title) {
		if (this.content == null) {
			this.content = new TabSheet();
			this.content.setStyleName("conx-entity-editor-detail-tabsheet");
			this.content.setSizeFull();
			
			this.mainLayout.removeAllComponents();
			this.mainLayout.addComponent(this.content);
			this.mainLayout.setExpandRatio(this.content, 1.0f);
		}
		
		this.content.addTab(content, title);
	}
	
	@Override
	public void removeTab(Component content) {
		if (this.content != null) {
			this.content.removeComponent(content);
		}
	}
	
	public EntityLineEditorView() {
		setSizeFull();
	}

	@Override
	public void removeAllTabs() {
		if (this.content != null)
			this.content.removeAllComponents();
	}
}
