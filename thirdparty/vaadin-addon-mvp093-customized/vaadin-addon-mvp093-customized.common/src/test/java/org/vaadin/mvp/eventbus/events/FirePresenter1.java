package org.vaadin.mvp.eventbus.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;


/**
 */
@Presenter(view = FireEventView.class)
public class FirePresenter1 extends BasePresenter<FireEventView, FireEventBus1> {

  /** Logger */
  private static final Logger logger = LoggerFactory.getLogger(FirePresenter1.class);

  public boolean eventReceived = false;
  
  public void onEvent(FiredEvent e) {
	  logger.info("FirePresenter1 received message");
	  eventReceived = true;
  }
}
