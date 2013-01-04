package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.view;

import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.IEntityEditorComponentView;
import com.conx.logistics.kernel.ui.vaadin.common.ConXAbstractSplitPanel.ISplitPositionChangeListener;
import com.vaadin.ui.Component;

public interface IMultiLevelEntityEditorView extends IEntityEditorComponentView {
	public void setBreadCrumb(Component component);
	public void setHeader(Component component);
	public void setMaster(Component component);
	public void showDetail();
	public void setDetail(Component component, boolean showDefaultPanel);
	public void setFooter(Component component);
	public void addSplitPositionChangeListener(ISplitPositionChangeListener listener);
}