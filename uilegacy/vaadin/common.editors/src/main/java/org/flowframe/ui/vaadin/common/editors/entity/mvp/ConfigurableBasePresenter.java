package org.flowframe.ui.vaadin.common.editors.entity.mvp;

import java.util.Map;

import org.flowframe.ui.vaadin.common.editors.ext.mvp.IConfigurablePresenter;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.BasePresenter;

public abstract class ConfigurableBasePresenter<V, E extends EventBus>  extends BasePresenter<V, E> implements IConfigurablePresenter{
	private Map<String,Object> config;

	public Map<String, Object> getConfig() {
		return config;
	}

	public void setConfig(Map<String, Object> config) {
		this.config = config;
	}
}
