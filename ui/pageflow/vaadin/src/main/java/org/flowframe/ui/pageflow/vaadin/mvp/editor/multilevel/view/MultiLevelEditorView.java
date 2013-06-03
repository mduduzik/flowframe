package org.flowframe.ui.pageflow.vaadin.mvp.editor.multilevel.view;

import org.flowframe.ui.component.domain.masterdetail.MasterDetailComponent;
import org.flowframe.ui.pageflow.vaadin.mvp.editor.multilevel.MultiLevelEditorPresenter;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.header.EntityEditorBreadCrumb;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.header.EntityEditorBreadCrumbItem;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class MultiLevelEditorView extends VerticalLayout implements IMultiLevelEditorView {
	private static final long serialVersionUID = 1L;

	@UiField
	private VerticalLayout mainLayout;

	private Component content;
	private MultiLevelEditorPresenter owner;
	private EntityEditorBreadCrumb breadCrumb;

	@Override
	public void setContent(Component component) {
		if (this.content != null) {
			this.mainLayout.removeComponent(this.content);
		}
		this.content = component;
		this.mainLayout.addComponent(this.content);
		this.mainLayout.setExpandRatio(this.content, 1.0f);
	}

	@Override
	public Component getContent() {
		return this.content;
	}

	@Override
	public IPresenter<?, ? extends EventBus> getOwner() {
		return this.owner;
	}

	@Override
	public void setOwner(MultiLevelEditorPresenter presenter) {
		this.owner = presenter;
	}

	@Override
	public void updateBreadCrumb(MasterDetailComponent[] masterDetailComponentArray) {
		if (masterDetailComponentArray.length <= 1) {
			this.breadCrumb.setVisible(false);
		} else {
			this.breadCrumb.clearBreadCrumb();
			EntityEditorBreadCrumbItem item = null;
			for (final MasterDetailComponent masterDetailComponent : masterDetailComponentArray) {
				item = new EntityEditorBreadCrumbItem(false, masterDetailComponent.getName());
				item.addListener(new ClickListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
						MultiLevelEditorView.this.owner.onRenderEditor(masterDetailComponent);
					}
				});
				this.breadCrumb.addItem(item);
			}
			this.breadCrumb.setVisible(true);
		}
	}

	@Override
	public void init() {
		this.breadCrumb = new EntityEditorBreadCrumb();
		this.breadCrumb.setWidth("100%");
		this.breadCrumb.setVisible(false);
		this.mainLayout.addComponentAsFirst(this.breadCrumb);
	}

}
