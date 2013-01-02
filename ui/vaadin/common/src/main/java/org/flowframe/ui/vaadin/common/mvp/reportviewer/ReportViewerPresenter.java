package org.flowframe.ui.vaadin.common.mvp.reportviewer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import org.flowframe.ui.vaadin.common.mvp.reportviewer.view.IReportViewerView;
import org.flowframe.ui.vaadin.common.mvp.reportviewer.view.ReportViewerView;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Embedded;

@Presenter(view = ReportViewerView.class)
public class ReportViewerPresenter extends BasePresenter<IReportViewerView, ReportViewerEventBus> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String reportUrl;
	
	public void onViewReport(String reportUrl) {
		this.reportUrl = reportUrl;
		
		ExternalResource externalResource = new ExternalResource(this.reportUrl, "application/pdf"); 
		Embedded pdf = new Embedded(null, externalResource);
		pdf.setType(Embedded.TYPE_BROWSER);
		pdf.setMimeType("application/pdf"); 
		pdf.setSizeFull();	
		pdf.setHeight("800px");
		
		getView().setReport(pdf);
	}
	
}
