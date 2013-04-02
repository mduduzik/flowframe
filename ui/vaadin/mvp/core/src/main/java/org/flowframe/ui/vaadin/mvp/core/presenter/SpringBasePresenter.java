package org.flowframe.ui.vaadin.mvp.core.presenter;

import org.flowframe.ui.vaadin.mvp.core.eventbus.IBaseEventBus;
import org.flowframe.ui.vaadin.mvp.core.view.IBaseView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.vaadin.mvp.presenter.BasePresenter;

/**
 */
public class SpringBasePresenter<V extends IBaseView, E extends IBaseEventBus> extends BasePresenter<V, E>
		implements ApplicationContextAware {

	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(SpringBasePresenter.class);

	/** Spring application context. */
	protected ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}
