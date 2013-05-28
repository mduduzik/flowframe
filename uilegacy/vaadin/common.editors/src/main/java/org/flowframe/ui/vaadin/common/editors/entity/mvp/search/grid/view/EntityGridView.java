package org.flowframe.ui.vaadin.common.editors.entity.mvp.search.grid.view;

import org.vaadin.mvp.uibinder.annotation.UiField;

import org.flowframe.ui.vaadin.common.editors.entity.ext.table.EntityEditorGrid;
import org.flowframe.ui.vaadin.common.editors.entity.ext.table.EntityEditorGrid.IDepletedListener;
import org.flowframe.ui.vaadin.common.editors.entity.ext.table.EntityEditorGrid.IEditListener;
import org.flowframe.ui.vaadin.common.editors.entity.ext.table.EntityEditorGrid.ISelectListener;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.ui.VerticalLayout;

public class EntityGridView extends VerticalLayout implements IEntityGridView {
	private static final long serialVersionUID = 1L;

	@UiField
	VerticalLayout mainLayout;
	
	private EntityEditorGrid grid;
	
	public EntityGridView() {
		setSizeFull();
	}
	
	@Override
	public void setContainerDataSource(Container container) {
		grid.setContainerDataSource(container);
	}

	@Override
	public void init() {
		if (mainLayout != null) {
			this.grid = new EntityEditorGrid();
			mainLayout.removeAllComponents();
			mainLayout.addComponent(grid);
			mainLayout.setExpandRatio(grid, 1.0f);
		}
	}

	@Override
	public void setVisibleColumns(Object[] columnIds) {
		this.grid.setVisibleColumns(columnIds);
	}

	@Override
	public void addEditListener(IEditListener listener) {
		this.grid.addEditListener(listener);
	}

	@Override
	public void addSelectListener(ISelectListener listener) {
		this.grid.addSelectListener(listener);
	}

	@Override
	public void deleteItem(Item item) throws Exception {
		this.grid.deleteItem(item);
	}

	@Override
	public Item getSelectedItem() {
		return grid.getSelectedItem();
	}

	@Override
	public void printGrid() {
		this.grid.printGrid();
	}

	@Override
	public void addDepletedListener(IDepletedListener listener) {
		this.grid.addDepletedListener(listener);
	}

	@Override
	public void setVisibleColumnNames(String[] columnNames) {
		this.grid.setColumnTitles(columnNames);
	}
}
