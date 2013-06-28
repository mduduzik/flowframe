package org.phakama.vaadin.mvp.presenter;

import java.util.Collection;

import junit.framework.Assert;

import org.junit.Test;
import org.phakama.vaadin.mvp.presenter.IPresenter;
import org.phakama.vaadin.mvp.presenter.IPresenterRegistry;
import org.phakama.vaadin.mvp.presenter.impl.registry.PresenterRegistry;
import org.phakama.vaadin.mvp.presenter.one.AnotherOnePresenter;
import org.phakama.vaadin.mvp.presenter.one.OnePresenter;
import org.phakama.vaadin.mvp.presenter.one.YetAnotherOnePresenter;
import org.phakama.vaadin.mvp.presenter.two.TwoPresenter;
import org.phakama.vaadin.mvp.view.IView;
import org.phakama.vaadin.mvp.view.one.AnotherOneView;
import org.phakama.vaadin.mvp.view.one.OneView;
import org.phakama.vaadin.mvp.view.one.YetAnotherOneView;
import org.phakama.vaadin.mvp.view.two.TwoViewImpl;

public class PresenterRegistryTest {

	@Test
	public void testRegister() {
		IPresenterRegistry registry = new PresenterRegistry();
		OnePresenter presenter = new OnePresenter();
		OneView view = new OneView();
		registry.register(presenter, null, view);
		Assert.assertTrue(registry.size() == 1);
		AnotherOnePresenter anotherPresenter = new AnotherOnePresenter();
		AnotherOneView anotherView = new AnotherOneView();
		registry.register(anotherPresenter, null, anotherView);
		Assert.assertTrue(registry.size() == 2);
		YetAnotherOnePresenter yetAnotherPresenter = new YetAnotherOnePresenter();
		YetAnotherOneView yetAnotherView = new YetAnotherOneView();
		registry.register(yetAnotherPresenter, null, yetAnotherView);
		Assert.assertTrue(registry.size() == 3);
	}

	@Test
	public void testUnregister() {
		IPresenterRegistry registry = new PresenterRegistry();
		OnePresenter presenter = new OnePresenter();
		OneView view = new OneView();
		registry.register(presenter, null, view);
		Assert.assertTrue(registry.size() == 1);
		AnotherOnePresenter anotherPresenter = new AnotherOnePresenter();
		AnotherOneView anotherView = new AnotherOneView();
		registry.register(anotherPresenter, null, anotherView);
		Assert.assertTrue(registry.size() == 2);
		registry.unregister(anotherPresenter);
		Assert.assertTrue(registry.size() == 1);
		registry.unregister(presenter);
		Assert.assertTrue(registry.size() == 0);
		Assert.assertTrue(registry.find(anotherView) == null);
		Assert.assertTrue(registry.find(view) == null);
	}
	
	@Test
	public void testFind() {
		IPresenterRegistry registry = new PresenterRegistry();
		OnePresenter presenter = new OnePresenter();
		OneView view = new OneView();
		registry.register(presenter, null, view);
		AnotherOnePresenter anotherPresenter = new AnotherOnePresenter();
		AnotherOneView anotherView = new AnotherOneView();
		registry.register(anotherPresenter, null, anotherView);
		Assert.assertTrue(registry.find(view) == presenter);
		Assert.assertTrue(registry.find(anotherView) == anotherPresenter);
	}
	
	@Test
	public void testParentOf() {
		IPresenterRegistry registry = new PresenterRegistry();
		OnePresenter presenter = new OnePresenter();
		OneView view = new OneView();
		registry.register(presenter, null, view);
		AnotherOnePresenter anotherPresenter = new AnotherOnePresenter();
		AnotherOneView anotherView = new AnotherOneView();
		registry.register(anotherPresenter, presenter, anotherView);
		YetAnotherOnePresenter yetAnotherPresenter = new YetAnotherOnePresenter();
		YetAnotherOneView yetAnotherView = new YetAnotherOneView();
		registry.register(yetAnotherPresenter, anotherPresenter, yetAnotherView);
		Assert.assertEquals(anotherPresenter, registry.parentOf(yetAnotherPresenter));
		Assert.assertEquals(presenter, registry.parentOf(anotherPresenter));
		Assert.assertNull(registry.parentOf(presenter));
	}
	
	@Test
	public void testSiblingsOf() {
		IPresenterRegistry registry = new PresenterRegistry();
		OnePresenter presenter = new OnePresenter();
		OneView view = new OneView();
		registry.register(presenter, null, view);
		AnotherOnePresenter anotherPresenter = new AnotherOnePresenter();
		AnotherOneView anotherView = new AnotherOneView();
		registry.register(anotherPresenter, presenter, anotherView);
		YetAnotherOnePresenter yetAnotherPresenter = new YetAnotherOnePresenter();
		YetAnotherOneView yetAnotherView = new YetAnotherOneView();
		registry.register(yetAnotherPresenter, presenter, yetAnotherView);
		TwoPresenter twoPresenter = new TwoPresenter();
		TwoViewImpl twoView = new TwoViewImpl();
		registry.register(twoPresenter, presenter, twoView);
		Collection<IPresenter<? extends IView>> siblingsOfTwo = registry.siblingsOf(twoPresenter);
		Assert.assertTrue(siblingsOfTwo.size() == 2);
		Assert.assertTrue(siblingsOfTwo.contains(anotherPresenter));
		Assert.assertTrue(siblingsOfTwo.contains(yetAnotherPresenter));
		Assert.assertTrue(!siblingsOfTwo.contains(twoPresenter));
	}
	
	@Test
	public void testChildrenOf() {
		IPresenterRegistry registry = new PresenterRegistry();
		OnePresenter presenter = new OnePresenter();
		OneView view = new OneView();
		registry.register(presenter, null, view);
		AnotherOnePresenter anotherPresenter = new AnotherOnePresenter();
		AnotherOneView anotherView = new AnotherOneView();
		registry.register(anotherPresenter, presenter, anotherView);
		YetAnotherOnePresenter yetAnotherPresenter = new YetAnotherOnePresenter();
		YetAnotherOneView yetAnotherView = new YetAnotherOneView();
		registry.register(yetAnotherPresenter, presenter, yetAnotherView);
		TwoPresenter twoPresenter = new TwoPresenter();
		TwoViewImpl twoView = new TwoViewImpl();
		registry.register(twoPresenter, presenter, twoView);
		Collection<IPresenter<? extends IView>> childrenOfOne = registry.childrenOf(presenter);
		Assert.assertTrue(childrenOfOne.size() == 3);
		Assert.assertTrue(childrenOfOne.contains(anotherPresenter));
		Assert.assertTrue(childrenOfOne.contains(yetAnotherPresenter));
		Assert.assertTrue(childrenOfOne.contains(twoPresenter));
	}
}
