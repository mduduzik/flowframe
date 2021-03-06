package org.flowframe.ui.vaadin.common.editors.entity.mvp.search.grid.view;

import org.flowframe.ui.vaadin.common.editors.entity.ext.table.EntityEditorGrid.IDepletedListener;
import org.flowframe.ui.vaadin.common.editors.entity.ext.table.EntityEditorGrid.IEditListener;
import org.flowframe.ui.vaadin.common.editors.entity.ext.table.EntityEditorGrid.ISelectListener;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.IEntityEditorComponentView;
import com.vaadin.data.Container;
import com.vaadin.data.Item;

public interface IEntityGridView  extends IEntityEditorComponentView {
	public void setContainerDataSource(Container container);
	public void setVisibleColumns(Object[] columnIds);
	public void setVisibleColumnNames(String[] columnNames);
	public void addEditListener(IEditListener listener);
	public void addSelectListener(ISelectListener listener);
	public void addDepletedListener(IDepletedListener listener);
	public void deleteItem(Item item) throws Exception;
	public void printGrid();
	public Item getSelectedItem();
}