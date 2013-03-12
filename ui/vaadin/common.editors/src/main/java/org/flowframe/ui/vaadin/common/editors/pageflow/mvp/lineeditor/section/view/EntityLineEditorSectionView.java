package org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.section.view;

import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class EntityLineEditorSectionView extends VerticalLayout implements IEntityLineEditorSectionView {
	private static final long serialVersionUID = 1L;

	@UiField
	private VerticalLayout mainLayout;

	private Component header;
	private Component content;

	public EntityLineEditorSectionView() {
		setSizeFull();
	}

	@Override
	public void setContent(Component component) {
		if (component != null) {
			if (this.content != null) {
				this.mainLayout.removeComponent(this.content);
			}
			this.content = component;
			this.content.setSizeFull();
			if (this.header == null) {
				this.mainLayout.addComponent(this.content);
			} else {
				this.mainLayout.addComponent(this.content, 1);
			}
			this.mainLayout.setExpandRatio(this.content, 1.0f);
		}
	}

	@Override
	public void setHeader(Component component) {
		if (component != null) {
			if (this.header != null) {
				this.mainLayout.removeComponent(this.header);
			}
			this.header = component;
			this.header.setWidth("100%");
			this.mainLayout.addComponentAsFirst(this.header);
		}
	}

}
