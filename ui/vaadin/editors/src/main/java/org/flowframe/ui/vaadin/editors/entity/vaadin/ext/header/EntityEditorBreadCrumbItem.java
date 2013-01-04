package org.flowframe.ui.vaadin.editors.entity.vaadin.ext.header;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;

public class EntityEditorBreadCrumbItem extends Button {
	private static final long serialVersionUID = 3407301281707633241L;
	
	private static final String BREADCRUMB_ITEM_GRID_PNG = "breadcrumb/img/conx-bread-crumb-grid.png";
	private static final String BREADCRUMB_ITEM_RECORD_PNG = "breadcrumb/img/conx-bread-crumb-record.png";
	private static final String BREADCRUMB_ITEM_GRID_SELECTED_PNG = "breadcrumb/img/conx-bread-crumb-grid-selected.png";
	private static final String BREADCRUMB_ITEM_RECORD_SELECTED_PNG = "breadcrumb/img/conx-bread-crumb-record-selected.png";
	private static final String BREADCRUMB_ITEM_SELECTED_STYLE = "conx-entity-editor-bread-crumb-item-current";

	private boolean isGrid;
	
	public EntityEditorBreadCrumbItem(boolean isGrid, String title) {
		this.isGrid = isGrid;
		setStyleName("conx-entity-editor-bread-crumb-item");
		setCaption(title);
	}
	
	public void setSelected(boolean isSelected) {
		if (isSelected) {
			addStyleName(BREADCRUMB_ITEM_SELECTED_STYLE);
			if (isGrid) {
				setIcon(new ThemeResource(BREADCRUMB_ITEM_GRID_SELECTED_PNG));
			} else {
				setIcon(new ThemeResource(BREADCRUMB_ITEM_RECORD_SELECTED_PNG));
			}
		} else {
			removeStyleName(BREADCRUMB_ITEM_SELECTED_STYLE);
			if (isGrid) {
				setIcon(new ThemeResource(BREADCRUMB_ITEM_GRID_PNG));
			} else {
				setIcon(new ThemeResource(BREADCRUMB_ITEM_RECORD_PNG));
			}
		}
	}
}
