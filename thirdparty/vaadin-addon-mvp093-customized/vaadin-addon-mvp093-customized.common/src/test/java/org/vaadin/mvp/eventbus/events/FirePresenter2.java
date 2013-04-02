package org.vaadin.mvp.eventbus.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;


/**
 */
@Presenter(view = FireEventView.class)
public class FirePresenter2 extends BasePresenter<FireEventView, FireEventBus> {

  /** Logger */
  private static final Logger logger = LoggerFactory.getLogger(FirePresenter2.class);

  public boolean eventReceived = false;
  
  public void onEvent(FiredEvent e) {
	  logger.info("FirePresenter2 received message");
	  eventReceived = true;
  }
}
