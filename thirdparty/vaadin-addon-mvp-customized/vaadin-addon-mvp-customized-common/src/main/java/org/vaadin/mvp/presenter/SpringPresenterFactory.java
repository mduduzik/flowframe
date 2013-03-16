package org.vaadin.mvp.presenter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.EventBusManager;

import com.vaadin.terminal.gwt.server.WebApplicationContext;

public class SpringPresenterFactory extends PresenterFactory implements ApplicationContextAware {
	@Autowired
	private EventBusManager eventBusManager;
	
	private ApplicationContext context;

	public SpringPresenterFactory() {
		setEventManager(eventBusManager);
	}
	
	@Override
	protected IPresenter<?, ? extends EventBus> create(Object arg) {
		IPresenter<?, ? extends EventBus> presenter = super.create(arg);
		this.context.getAutowireCapableBeanFactory().autowireBean(presenter);
		
		return presenter;
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.context = arg0;
	}

}
