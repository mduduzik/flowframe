package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp;

import java.util.Map;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;

public abstract class ConfigurableBasePresenter<V, E extends EventBus>  extends BasePresenter<V, E> {
	private Map<String,Object> config;

	public Map<String, Object> getConfig() {
		return config;
	}

	public void setConfig(Map<String, Object> config) {
		this.config = config;
	}
	
	public abstract void configure();
}
