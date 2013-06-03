package org.flowframe.ui.pageflow.vaadin.mvp.editor.grid.view;

import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.table.EntityEditorGrid.IEditListener;
import org.flowframe.ui.vaadin.editors.entity.vaadin.ext.table.EntityEditorGrid.ISelectListener;
import org.vaadin.mvp.uibinder.IUiBindable;

import com.vaadin.data.Container;
import com.vaadin.data.Item;

public interface IEditorGridView extends IUiBindable {
	public void setContainerDataSource(Container container);

	public void setVisibleColumns(Object[] columnIds);

	public void addEditListener(IEditListener listener);

	public void addSelectListener(ISelectListener listener);

	public void deleteItem(Item item) throws Exception;
	
	public void init();
}