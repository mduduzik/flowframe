package org.flowframe.ui.vaadin.common.mvp.docviewer;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import com.conx.logistics.mdm.domain.documentlibrary.FileEntry;

public interface DocViewerEventBus extends EventBus {
	@Event(handlers = { DocViewerPresenter.class })	
	public void viewDocument(FileEntry fileEntry);
}
