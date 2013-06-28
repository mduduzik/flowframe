package org.phakama.vaadin.mvp.view.impl;

import org.phakama.vaadin.mvp.exception.InaccessibleViewException;
import org.phakama.vaadin.mvp.exception.ViewConstructionException;
import org.phakama.vaadin.mvp.view.IView;
import org.phakama.vaadin.mvp.view.IViewFactory;
import org.phakama.vaadin.mvp.view.IViewRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewFactory implements IViewFactory {
	private static final long serialVersionUID = 7902009139894230033L;

	private static final Logger logger = LoggerFactory.getLogger(ViewFactory.class);

	private boolean logging = false;

	private IViewRegistry registry;

	public ViewFactory(IViewRegistry registry) {
		if (registry == null) {
			throw new IllegalArgumentException("The registry parameter was null.");
		}

		this.registry = registry;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IView> T create(Class<T> viewClass) {
		if (viewClass == null) {
			throw new IllegalArgumentException("The viewClass parameter was null.");
		}
		if (!IView.class.isAssignableFrom(viewClass) || IView.class.equals(viewClass)) {
			throw new IllegalArgumentException(viewClass + " does not implement an extension of the IView interface.");
		}

		Class<? extends IView> viewImplClass = this.registry.find(viewClass);
		if (viewImplClass == null) {
			// The view implementation has not been registered yet
			viewImplClass = findViewImpl(viewClass);
			if (viewImplClass == null) {
				// The view implementation doesn't have an obvious name
				throw new ViewConstructionException(
						"Could not identify an implementation for view class "
								+ viewClass.getName()
								+ ". Either register the view implementation in the IViewRegistry, or name you view implementation predictably and put it in the same package as the view class. For instance, IDummyView would be implemented by either DummyView or DummyViewImpl; DummyView would be implemented by DummyViewImpl.");
			} else {
				this.registry.register(viewImplClass);
			}
		}

		IView viewInstance = null;
		try {
			viewInstance = viewImplClass.newInstance();
		} catch (InstantiationException e) {
			throw new ViewConstructionException(viewImplClass + " could not be instantiated. View implementations must have publicly accessible default constructors.");
		} catch (IllegalAccessException e) {
			throw new InaccessibleViewException(e);
		}

		if (this.logging)
			logger.debug("New instance of View Implementation Type [{}] implementing View Type [{}] successfully instantiated.", viewImplClass, viewClass);

		return (T) viewInstance;
	}

	@SuppressWarnings("unchecked")
	private <T> Class<? extends T> findViewImpl(Class<T> viewClass) {
		String name = viewClass.getName().trim();
		String[] nameSections = name.split("\\.");
		String lastSection = nameSections[nameSections.length - 1];

		if (this.logging)
			logger.info("Looking for a View Implementation of View Type [{}].", viewClass);

		String namePermutation1 = null, namePermutation2 = null;
		if (lastSection.length() >= 1) {
			if (lastSection.charAt(0) == 'I') {
				// IDummyView -> DummyView
				namePermutation1 = lastSection.substring(1);
				namePermutation1 = name.replace(lastSection, namePermutation1);
				// IDummyView -> DummyViewImpl
				namePermutation2 = lastSection.substring(1).concat("Impl");
				namePermutation2 = name.replace(lastSection, namePermutation2);
			} else {
				// DummyView -> DummyViewImpl
				namePermutation1 = lastSection.concat("Impl");
				namePermutation1 = name.replace(lastSection, namePermutation1);
			}
		}

		Class<?> viewImplClass = null;
		try {
			if (this.logging)
				logger.debug("Checking if View Implementation Type with name [{}] exists...", namePermutation1);
			viewImplClass = Class.forName(namePermutation1);
			if (this.logging)
				logger.info("View Implementation Type with name [{}] exists!", namePermutation1);
		} catch (ClassNotFoundException e) {
			if (this.logging)
				logger.info("Couldn't find View Implementation Type with name [{}].", namePermutation1);
			if (namePermutation2 != null) {
				try {
					if (this.logging)
						logger.debug("Checking if View Implementation Type with name [{}] exists...", namePermutation2);
					viewImplClass = Class.forName(namePermutation2);
					if (this.logging)
						logger.info("View Implementation Type with name [{}] exists!", namePermutation2);
				} catch (ClassNotFoundException e1) {
					if (this.logging)
						logger.info("Couldn't find View Implementation Type with name [{}].", namePermutation2);
				}
			}
		}

		if (viewImplClass == null) {
			return null;
		}

		if (viewClass.isAssignableFrom(viewImplClass)) {
			return (Class<? extends T>) viewImplClass;
		} else {
			return null;
		}
	}

	@Override
	public IViewRegistry getRegistry() {
		return this.registry;
	}

	@Override
	public boolean isLogging() {
		return this.logging;
	}

	@Override
	public void enableLogging() {
		this.logging = true;
	}

	@Override
	public void disableLogging() {
		this.logging = false;
	}
}
