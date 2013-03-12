package org.flowframe.ui.vaadin.common.editors.entity.mvp.breadcrumb.view;

import org.vaadin.mvp.uibinder.annotation.UiField;

import org.flowframe.ui.vaadin.common.editors.entity.ext.header.EntityEditorBreadCrumb;
import org.flowframe.ui.vaadin.common.editors.entity.ext.header.EntityEditorBreadCrumbItem;
import org.flowframe.ui.vaadin.common.editors.entity.ext.pager.EntityEditorPager;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

public class EntityBreadCrumbView extends VerticalLayout implements IEntityBreadCrumbView {
	private static final long serialVersionUID = -8556644797413509062L;
	
	private EntityEditorBreadCrumb breadcrumb;
	private EntityEditorPager pager;
	
	@UiField
	VerticalLayout mainLayout;

	public EntityBreadCrumbView() {
		setWidth("100%");
		setHeight("38px");
	}
	
	@Override
	public void init() {
		this.breadcrumb = new EntityEditorBreadCrumb();
		this.pager = new EntityEditorPager();
		
		breadcrumb.setNavigationComponent(pager);
		
		mainLayout.addComponent(breadcrumb);
	}

	@Override
	public void addBreadCrumbItem(EntityEditorBreadCrumbItem entityEditorBreadCrumbItem) {
		this.breadcrumb.addItem(entityEditorBreadCrumbItem);
	}

	@Override
	public void setPagerCaption(String caption) {
		this.pager.setCaption(caption);
	}

	@Override
	public void clearBreadCrumb() {
		this.breadcrumb.clearBreadCrumb();
	}

	@Override
	public void addFirstItemListener(ClickListener listener) {
		this.pager.addFirstListener(listener);
	}

	@Override
	public void addPreviousItemListener(ClickListener listener) {
		this.pager.addPreviousListener(listener);
	}

	@Override
	public void addNextItemListener(ClickListener listener) {
		this.pager.addNextListener(listener);
	}

	@Override
	public void addLastItemListener(ClickListener listener) {
		this.pager.addLastListener(listener);
	}

	@Override
	public void setFirstItemEnabled(boolean enabled) {
		this.pager.setFirstEnabled(enabled);
	}

	@Override
	public void setPreviousItemEnabled(boolean enabled) {
		this.pager.setPreviousEnabled(enabled);
	}

	@Override
	public void setNextItemEnabled(boolean enabled) {
		this.pager.setNextEnabled(enabled);
	}

	@Override
	public void setLastItemEnabled(boolean enabled) {
		this.pager.setLastEnabled(enabled);
	}
}
