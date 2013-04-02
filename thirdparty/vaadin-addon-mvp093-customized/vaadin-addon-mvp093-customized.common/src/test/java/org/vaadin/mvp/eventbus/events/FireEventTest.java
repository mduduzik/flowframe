package org.vaadin.mvp.eventbus.events;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.CustomEventBusManager;


public class FireEventTest {

  /** Logger */
  private static final Logger logger = LoggerFactory.getLogger(FireEventTest.class);
  private FireEventBus instance;
  private FirePresenter presenter;
  private FirePresenter1 presenter1;
  private FirePresenter2 presenter2;

  @Before
  public void setUp() {
    CustomEventBusManager eventBusManager = new CustomEventBusManager();
    presenter = new FirePresenter();
    eventBusManager.addSubscriber(presenter);
    
    presenter1 = new FirePresenter1();
    eventBusManager.addSubscriber(presenter1);
    
    presenter2 = new FirePresenter2();
    eventBusManager.addSubscriber(presenter2);    
    
    instance = eventBusManager.getEventBus(FireEventBus.class);
  }

  @Test
  public void testBasicEvent() {
	  instance.fireEvent(new FiredEvent());
	  assertTrue("event should have been received", presenter.eventReceived);
  }
}
