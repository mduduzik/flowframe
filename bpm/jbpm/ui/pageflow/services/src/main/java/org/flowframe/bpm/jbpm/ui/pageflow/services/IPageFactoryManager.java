package org.flowframe.bpm.jbpm.ui.pageflow.services;

import java.util.Map;

import org.vaadin.mvp.presenter.PresenterFactory;

public interface IPageFactoryManager {
	public IPageFactory create(Map<String, Object> config, PresenterFactory presenterFactory);
}
