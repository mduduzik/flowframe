package org.flowframe.ui.vaadin.common.editors.mvp.editor.view;

import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class MasterSectionView extends VerticalLayout implements
		IMasterSectionView {
	private static final long serialVersionUID = 1L;

	@UiField
	private VerticalLayout mainLayout;

	private Component header;
	private Component content;

	public MasterSectionView() {
		setSizeFull();
	}

	/**
	 * Makes sure that content is visible and is the bottom component vertically
	 */
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

	/**
	 * Sets the first component vertically, the header doesn't have to be set
	 */
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

	@Override
	public Component getContent() {
		return this.content;
	}

}
