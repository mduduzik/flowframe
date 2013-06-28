package org.phakama.vaadin.mvp.view;

import java.util.Collection;

import junit.framework.Assert;

import org.junit.Test;
import org.phakama.vaadin.mvp.view.IView;
import org.phakama.vaadin.mvp.view.IViewPrioritizer;
import org.phakama.vaadin.mvp.view.IViewRegistry;
import org.phakama.vaadin.mvp.view.impl.ViewRegistry;
import org.phakama.vaadin.mvp.view.one.AnotherOneView;
import org.phakama.vaadin.mvp.view.one.IOneView;
import org.phakama.vaadin.mvp.view.one.OneView;
import org.phakama.vaadin.mvp.view.one.YetAnotherOneView;
import org.phakama.vaadin.mvp.view.two.TwoViewImpl;

public class ViewRegistryTest {

	@Test
	public void testRegister() {
		IViewRegistry registry = new ViewRegistry();
		registry.register(OneView.class);
		Assert.assertTrue(registry.size() == 1);
		Class<? extends IView> result = registry.find(IOneView.class);
		Assert.assertNotNull(result);
		registry.register(TwoViewImpl.class);
		Assert.assertTrue(registry.size() == 2);
		registry.register(AnotherOneView.class);
		Assert.assertTrue(registry.size() == 2);
		Assert.assertTrue(registry.findAll(IOneView.class).size() == 2);
	}

	@Test
	public void testUnregister() {
		IViewRegistry registry = new ViewRegistry();
		registry.register(OneView.class);
		registry.register(AnotherOneView.class);
		Assert.assertTrue(registry.size() == 1);
		registry.unregister(OneView.class);
		Assert.assertTrue(registry.size() == 1);
		registry.unregister(AnotherOneView.class);
		Assert.assertTrue(registry.size() == 0);
	}

	@Test
	public void testFind() {
		IViewRegistry registry = new ViewRegistry();
		registry.register(OneView.class);
		registry.register(AnotherOneView.class);
		Class<? extends IView> result = registry.find(IOneView.class);
		Assert.assertEquals(AnotherOneView.class, result);
		registry.unregister(AnotherOneView.class);
		result = registry.find(IOneView.class);
		Assert.assertEquals(OneView.class, result);
		registry.unregister(OneView.class);
		Assert.assertNull(registry.find(IOneView.class));
	}
	
	@Test
	public void testFindAll() {
		IViewRegistry registry = new ViewRegistry();
		registry.register(OneView.class);
		registry.register(OneView.class);
		registry.register(TwoViewImpl.class);
		registry.register(AnotherOneView.class);
		Collection<Class<? extends IView>> result = registry.findAll(IOneView.class);
		Assert.assertTrue(result.size() == 2);
	}

	@Test
	public void testFindPrioritizer() {
		IViewRegistry registry = new ViewRegistry();
		registry.register(OneView.class);
		registry.register(AnotherOneView.class);
		registry.register(YetAnotherOneView.class);
		IViewPrioritizer alphabeticalPrioritizer = new IViewPrioritizer() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean prioritize(Class<? extends IView> first, Class<? extends IView> second) {
				return first.getName().compareTo(second.getName()) < 0;
			}
		};
		IViewPrioritizer oppositeAlphabeticalPrioritizer = new IViewPrioritizer() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean prioritize(Class<? extends IView> first, Class<? extends IView> second) {
				return first.getName().compareTo(second.getName()) > 0;
			}
		};
		Class<? extends IView> result = registry.find(IOneView.class, alphabeticalPrioritizer);
		Assert.assertEquals(AnotherOneView.class, result);
		result = registry.find(IOneView.class, oppositeAlphabeticalPrioritizer);
		Assert.assertEquals(YetAnotherOneView.class, result);
	}
}
