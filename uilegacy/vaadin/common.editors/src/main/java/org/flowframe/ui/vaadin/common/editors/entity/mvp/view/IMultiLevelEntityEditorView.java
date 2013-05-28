package org.flowframe.ui.vaadin.common.editors.entity.mvp.view;

import org.flowframe.ui.vaadin.addons.common.FlowFrameAbstractSplitPanel.ISplitPositionChangeListener;
import org.flowframe.ui.vaadin.common.editors.entity.mvp.IEntityEditorComponentView;

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