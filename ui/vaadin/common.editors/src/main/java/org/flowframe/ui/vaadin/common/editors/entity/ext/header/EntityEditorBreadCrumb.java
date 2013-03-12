package org.flowframe.ui.vaadin.common.editors.entity.ext.header;

import java.util.ArrayList;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

public class EntityEditorBreadCrumb extends HorizontalLayout {
	private static final long serialVersionUID = 4323855996149506775L;
	
	private ArrayList<EntityEditorBreadCrumbItem> items;
	private HorizontalLayout leftLayout;
	private HorizontalLayout rightLayout;

	public EntityEditorBreadCrumb() {
		this.leftLayout = new HorizontalLayout();
		this.rightLayout = new HorizontalLayout();
		this.items = new ArrayList<EntityEditorBreadCrumbItem>();
		
		initialize();
	}
	
	private void initialize() {
		leftLayout.setSpacing(true);
		rightLayout.setSpacing(true);
		rightLayout.setMargin(false, true, false, false);
		
		setStyleName("conx-entity-editor-bread-crumb");
		setHeight("38px");
		setWidth("100%");
		addComponent(leftLayout);
		addComponent(rightLayout);
		setComponentAlignment(leftLayout, Alignment.MIDDLE_LEFT);
		setComponentAlignment(rightLayout, Alignment.MIDDLE_RIGHT);
	}
	
	public void addItem(EntityEditorBreadCrumbItem item) {
		item.setSelected(true);
		if (items.size() != 0) {
			leftLayout.addComponent(new EntityEditorBreadCrumbSeparator());
			items.get(items.size() - 1).setSelected(false);
		}
		leftLayout.addComponent(item);
		items.add(item);
	}
	
	public void addItem(boolean isGrid, String title) {
		EntityEditorBreadCrumbItem newItem = new EntityEditorBreadCrumbItem(isGrid, title);
		newItem.setSelected(true);
		if (items.size() != 0) {
			leftLayout.addComponent(new EntityEditorBreadCrumbSeparator());
			items.get(items.size() - 1).setSelected(false);
		}
		leftLayout.addComponent(newItem);
		items.add(newItem);
	}
	
	public void clearBreadCrumb() {
		items.clear();
		this.leftLayout.removeAllComponents();
	}
	
	public void setNavigationComponent(Component component) {
		this.rightLayout.removeAllComponents();
		this.rightLayout.addComponent(component);
		this.rightLayout.setComponentAlignment(component, Alignment.MIDDLE_RIGHT);
	}
}
