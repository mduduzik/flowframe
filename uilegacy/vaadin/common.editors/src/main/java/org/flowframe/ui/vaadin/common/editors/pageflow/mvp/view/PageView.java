package org.flowframe.ui.vaadin.common.editors.pageflow.mvp.view;

import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class PageView extends VerticalLayout implements IPageView {
	private static final long serialVersionUID = 9218034594527412339L;

	@UiField
	private VerticalLayout mainLayout;

	private Component content;

	public PageView() {
		setSizeFull();
	}

	@Override
	public void setContent(Component content) {
		if (this.content != null) {
			this.mainLayout.removeComponent(this.content);
		}
		this.content = content;
		this.content.setSizeFull();
		this.mainLayout.addComponent(this.content);
	}

	@Override
	public Component getContent() {
		return content;
	}

	public VerticalLayout getMainLayout() {
		return mainLayout;
	}

	public void setMainLayout(VerticalLayout mainLayout) {
		this.mainLayout = mainLayout;
	}
}
