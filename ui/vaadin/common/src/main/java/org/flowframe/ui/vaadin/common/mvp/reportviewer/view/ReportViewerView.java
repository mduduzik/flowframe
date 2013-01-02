package org.flowframe.ui.vaadin.common.mvp.reportviewer.view;

import org.vaadin.mvp.uibinder.IUiBindable;
import org.vaadin.mvp.uibinder.annotation.UiField;

import com.vaadin.ui.Embedded;
import com.vaadin.ui.VerticalLayout;

public class ReportViewerView extends VerticalLayout implements IReportViewerView,IUiBindable {
	private static final long serialVersionUID = 1L;

	@UiField
	private VerticalLayout mainLayout;

	@Override
	public void setReport(Embedded report) {
		this.mainLayout.removeAllComponents();
		this.mainLayout.addComponent(report);
	}
}
