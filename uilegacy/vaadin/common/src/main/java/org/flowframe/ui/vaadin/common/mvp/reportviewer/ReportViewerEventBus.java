package org.flowframe.ui.vaadin.common.mvp.reportviewer;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

public interface ReportViewerEventBus extends EventBus {
	@Event(handlers = { ReportViewerPresenter.class })	
	public void viewReport(String reportUrl);
}
