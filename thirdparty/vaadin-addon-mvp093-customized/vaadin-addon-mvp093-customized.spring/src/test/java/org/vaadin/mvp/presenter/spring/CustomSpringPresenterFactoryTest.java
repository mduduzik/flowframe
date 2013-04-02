package org.vaadin.mvp.presenter.spring;

import static org.junit.Assert.*;

import java.util.Locale;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.vaadin.mvp.eventbus.CustomEventBusManager;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.spring.SpringPresenterFactory;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/testCustomApplicationContext.xml")
public class CustomSpringPresenterFactoryTest {

  @Autowired
  @Resource(name="customSpringPresenterFactory")
  CustomSpringPresenterFactory instance;

  @Before
  public void setUp() throws Exception {
    instance.setEventManager(new CustomEventBusManager());
    instance.setLocale(Locale.getDefault());
  }

  @Test
  public void testFactoryPresent() {
    assertNotNull(instance);
  }

  @Test
  public void testCreate() {
    IPresenter<?, ? extends EventBus> presenter = instance.createPresenter("customSpring");
    assertNotNull("created presenter is null", presenter);
    assertNotNull("presenters view is null", presenter.getView());
    assertTrue("presenters bind() method has not been called", ((SwappableSpringPresenter)presenter).bound);
    assertNotNull("presenter eventbus is null", presenter.getEventBus());
  }

}
