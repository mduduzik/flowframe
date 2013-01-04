package org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.customizer;

import java.util.Map;

import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.IPresenterFactoryCustomizer;

import org.flowframe.ui.vaadin.editors.entity.vaadin.mvp.ConfigurableBasePresenter;

public class ConfigurablePresenterFactoryCustomizer implements IPresenterFactoryCustomizer {

	private Map<String, Object> config;

	public ConfigurablePresenterFactoryCustomizer(Map<String, Object> config) {
		super();
		this.config = config;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void customize(IPresenter presenter) {
		if (presenter != null) {
			ConfigurableBasePresenter configPresenter = (ConfigurableBasePresenter) presenter;
			configPresenter.setConfig(this.config);
		} else {
			String err = "placeholder line";
		}
	}

	public Map<String, Object> getConfig() {
		return config;
	}

	public void setConfig(Map<String, Object> config) {
		this.config = config;
	}
}
