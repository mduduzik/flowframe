package org.flowframe.ui.vaadin.common.mvp;

import org.flowframe.kernel.common.mdm.domain.application.Feature;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

/**
 * The EventBus that is extended by all Application level EventBusses. It is NOT
 * unique to MainPresenter. It has events essential for application presenters
 * to implement.
 * 
 * @author Sandile
 */
public interface StartableApplicationEventBus extends EventBus {
	@Event(handlers = { MainPresenter.class })
	public void start(MainMVPApplication app);

	@Event(handlers = { MainPresenter.class })
	public void openDocument(FileEntry fileEntry);

	/**
	 * Show a document provided by the url. This is intended for use with
	 * reporting.
	 * 
	 * @param url
	 *            the url of the document
	 * @param url
	 *            the feature caption
	 * @throws Exception
	 */
	@Event(handlers = { MainPresenter.class })
	public void openDocument(String url, String caption);

	@Event(handlers = { MainPresenter.class })
	public void openFeatureView(Feature feature);

	@Event(handlers = { MainPresenter.class })
	public void closeFeatureView(Feature feature);
}
