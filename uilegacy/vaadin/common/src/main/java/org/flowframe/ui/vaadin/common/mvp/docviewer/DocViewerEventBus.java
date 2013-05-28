package org.flowframe.ui.vaadin.common.mvp.docviewer;

import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

public interface DocViewerEventBus extends EventBus {
	@Event(handlers = { DocViewerPresenter.class })	
	public void viewDocument(FileEntry fileEntry);
}
