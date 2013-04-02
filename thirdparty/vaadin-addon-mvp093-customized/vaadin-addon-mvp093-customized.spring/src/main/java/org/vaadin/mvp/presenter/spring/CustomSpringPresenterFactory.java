package org.vaadin.mvp.presenter.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.presenter.AbstractPresenterFactory;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.IViewFactory;
import org.vaadin.mvp.presenter.PresenterFactory;
import org.vaadin.mvp.presenter.ViewFactoryException;
import org.vaadin.mvp.presenter.annotation.Presenter;
import org.vaadin.mvp.view.IView;

/**
 * Spring based {@link PresenterFactory}.
 * 
 * @author tam
 * 
 */
public class CustomSpringPresenterFactory extends SpringPresenterFactory
		implements ApplicationContextAware {

	/** Logger */
	private static final Logger logger = LoggerFactory
			.getLogger(CustomSpringPresenterFactory.class);

	@SuppressWarnings("unchecked")
	public IPresenter<?, ? extends EventBus> create(Object name,
			EventBus parentEventBus) {
		boolean isBean = false;
		IPresenter p = null;
		if (name instanceof String) {
			String beanName = (String) name;
			if (applicationContext.containsBean(beanName)) {
				p = applicationContext.getBean(beanName, IPresenter.class);
				isBean = true;
			} else
				throw new IllegalArgumentException("Bean(" + beanName
						+ ") not found in context");
		} else {
			try {
				Class<IPresenter<?, ? extends EventBus>> presenterClass = (Class<IPresenter<?, ? extends EventBus>>) name;
				p = (IPresenter<?, ? extends EventBus>) presenterClass.newInstance();
			} catch (ClassCastException e) {
				throw new IllegalArgumentException("Name(" + name
						+ ") is neither string nor IPresenter type");
			} catch (IllegalAccessException e) {
				throw new IllegalArgumentException("Name(" + name
						+ ") is neither string nor IPresenter type : IllegalAccessException");
			}
			catch (InstantiationException e) {
				throw new IllegalArgumentException("Name(" + name
						+ ") is neither string nor IPresenter type : InstantiationException");
			}			
		}

		p.setApplication(application);
		p.setMessageSource(messageSource);
		Presenter def = p.getClass().getAnnotation(Presenter.class);
		if (def == null) {
			throw new IllegalArgumentException(
					"Missing @Presenter annotation on bean '" + name + "'");
		}

		EventBus eventBus = createEventBus((Class<IPresenter>) p.getClass(), p,
				parentEventBus);
		p.setEventBus(eventBus);

		try {
			Class type = null;
			IView v = null;
			if (isBean) {
				try {
					v = applicationContext.getBean(name + "View", IView.class);
				} catch (NoSuchBeanDefinitionException e) {
				}
			}
			if (v != null)
				type = v.getClass();
			else
				type = def.view();
			Object view = viewFactory.createView(eventBusManager, p, type,
					locale);
			p.setView(view);
		} catch (ViewFactoryException e) {
			logger.error("Failed to create view for presenter", e);
		}

		// -- Configure via wiring
		this.applicationContext.getAutowireCapableBeanFactory().autowireBean(p);

		// -- Bind
		p.bind();

		return p;
	}

	@Override
	public IViewFactory getViewFactory() {
		return this.viewFactory;
	}

}
