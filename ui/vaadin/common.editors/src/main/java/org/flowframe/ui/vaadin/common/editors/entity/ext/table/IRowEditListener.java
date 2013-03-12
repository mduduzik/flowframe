package org.flowframe.ui.vaadin.common.editors.entity.ext.table;

import com.vaadin.addon.jpacontainer.EntityItem;

public interface IRowEditListener {
	public void onRowEdit(EntityItem<?> item);
}
