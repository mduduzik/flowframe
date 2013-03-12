package org.flowframe.ui.vaadin.common.editors.entity.mvp.breadcrumb.view;

import org.flowframe.ui.vaadin.common.editors.entity.ext.header.EntityEditorBreadCrumbItem;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.IEntityEditorComponentView;
import com.vaadin.ui.Button.ClickListener;

public interface IEntityBreadCrumbView  extends IEntityEditorComponentView {
	public void clearBreadCrumb();
	public void addBreadCrumbItem(EntityEditorBreadCrumbItem entityEditorBreadCrumbItem);
	public void setPagerCaption(String caption);
	public void addFirstItemListener(ClickListener listener);
	public void addPreviousItemListener(ClickListener listener);
	public void addNextItemListener(ClickListener listener);
	public void addLastItemListener(ClickListener listener);
	public void setFirstItemEnabled(boolean enabled);
	public void setPreviousItemEnabled(boolean enabled);
	public void setNextItemEnabled(boolean enabled);
	public void setLastItemEnabled(boolean enabled);
}