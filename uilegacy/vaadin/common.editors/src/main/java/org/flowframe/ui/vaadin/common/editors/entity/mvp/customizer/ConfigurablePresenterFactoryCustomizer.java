package org.flowframe.ui.vaadin.common.editors.entity.mvp.customizer;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.IPresenterFactoryCustomizer;

import org.flowframe.ui.vaadin.common.editors.entity.mvp.ConfigurableBasePresenter;
import org.flowframe.ui.vaadin.common.editors.ext.mvp.IConfigurablePresenter;

public class ConfigurablePresenterFactoryCustomizer implements IPresenterFactoryCustomizer {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Map<String, Object> config;

	public ConfigurablePresenterFactoryCustomizer(Map<String, Object> config) {
		super();
		this.config = config;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void customize(IPresenter presenter) {
		if (presenter != null) {
			IConfigurablePresenter configPresenter = (IConfigurablePresenter) presenter;
			try {
				configPresenter.onConfigure(this.config);
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String stacktrace = sw.toString();
				logger.error("Error customizing presenter "+presenter+"["+stacktrace+"]");
				throw new IllegalArgumentException("Error customizing presenter "+presenter, e);
			}
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
