package org.phakama.vaadin.mvp.view;

import junit.framework.Assert;

import org.junit.Test;
import org.phakama.vaadin.mvp.view.IViewFactory;
import org.phakama.vaadin.mvp.view.four.IFourView;
import org.phakama.vaadin.mvp.view.four.view.FourView;
import org.phakama.vaadin.mvp.view.impl.ViewFactory;
import org.phakama.vaadin.mvp.view.impl.ViewRegistry;
import org.phakama.vaadin.mvp.view.one.IOneView;
import org.phakama.vaadin.mvp.view.one.OneView;
import org.phakama.vaadin.mvp.view.three.ThreeView;
import org.phakama.vaadin.mvp.view.three.ThreeViewImpl;
import org.phakama.vaadin.mvp.view.two.TwoView;
import org.phakama.vaadin.mvp.view.two.TwoViewImpl;

public class ViewFactoryTest {

	@Test
	public void testViewFinder() {
		IViewFactory factory = new ViewFactory(new ViewRegistry());
		IOneView oneView = factory.create(IOneView.class);
		Assert.assertNotNull(oneView);
		Assert.assertEquals(OneView.class, oneView.getClass());
		TwoView twoView = factory.create(TwoView.class);
		Assert.assertNotNull(twoView);
		Assert.assertEquals(TwoViewImpl.class, twoView.getClass());
		ThreeView threeView = factory.create(ThreeView.class);
		Assert.assertNotNull(threeView);
		Assert.assertEquals(ThreeViewImpl.class, threeView.getClass());
		factory.getRegistry().register(FourView.class);
		IFourView fourView = factory.create(IFourView.class);
		Assert.assertNotNull(fourView);
		Assert.assertEquals(FourView.class, fourView.getClass());
	}
	
}
